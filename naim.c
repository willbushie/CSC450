#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <string.h>
#include <signal.h>
#include <semaphore.h>

// global variables
int* sh_mem;
const char* semName = "count, child";


void operation()
{
    if(*sh_mem > 10)
    {
        shmdt(sh_mem);
        //printf("operation complete, killing process %d\n",getpid());
        kill(getpid(),SIGKILL);
    }
    else
    {
        printf("Count: %d (%d)\n", *sh_mem, getpid());
        *sh_mem += 1;
        //sem_post(sem_id);
    }
}

int main()
{    
    printf("Parent (%d)\n",getpid());
    // for storing the counting value
    int segment_id = shmget (IPC_PRIVATE, sizeof(int), IPC_CREAT | IPC_EXCL | S_IRUSR | S_IWUSR);
    
    // set the shared memory value
    sh_mem = (int*) shmat(segment_id, NULL, 0);
    *sh_mem = 0;
    shmdt(sh_mem);

    int pid = fork();
    if(pid == 0) // first child
    {
        sem_t* sem_id = sem_open(semName, O_CREAT, 0600, 0);
        printf("First Child (%d)\n", getpid());
        sh_mem = (int*) shmat(segment_id, NULL, 0);
        *sh_mem = 0;
        sem_post(sem_id);
        while(1)
        {
            //printf("first child check (%d)\n",getpid());
            sleep(1);
            sem_wait(sem_id);
            operation();
            sem_post(sem_id);
        }
        sem_close(sem_id);
        sem_unlink(semName);
    }
    else
    {
        pid = fork();
        if(pid == 0) // second child
        {
            sem_t* sem_id = sem_open(semName, O_CREAT, 0600, 0);
            printf("Second Child (%d)\n", getpid());
            sh_mem = (int*) shmat(segment_id, NULL, 0);
            while(1)
            {
                sleep(1);
                sem_wait(sem_id);
                operation();
                sem_post(sem_id);
            }
            sem_close(sem_id);
            sem_unlink(semName);
        }
    }
    wait(NULL);
    //sleep(1);
}
