// CSC 450 - HW #4 - Count to 10 using signals and shared memory

// inclusions
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <string.h>
#include <signal.h>

// global variables
int segment_id;
int segment_id_pid;


// handle user created signals (SIGCONT is for telling the other process to continue count)
void handle()
{	
	printf("Being handled - PID: %d\n",getpid());
	// obtain & store the shared value
	int* sh_mem = (int*) shmat(segment_id, NULL, 0);
	int value = *sh_mem;
	// update the shared value
	*sh_mem = value + 1;
	// detatch from shared memory
	shmdt(sh_mem);
	// print the shared value
	printf("Current count: %d PID: %d\n",value,getpid());
	// obtain & store the shared value
	int* sh_mem2 = (int*) shmat(segment_id_pid, NULL, 0);
	int brotherPID = *sh_mem2;
	// update the shared value
	*sh_mem2 = getpid();
	// detatch from shared memory
	shmdt(sh_mem2);
	if(value < 10)
	{
		// print the shared value
		printf("Calling brother - my PID: %d Brother PID: %d\n",getpid(),brotherPID);
		// signal brother process
		kill(brotherPID,SIGCONT);
		kill(getpid(),SIGSTOP);
	}
	else
	{
		kill(brotherPID,SIGKILL);
		kill(getpid(),SIGKILL);
	}
}

void testhandle()
{
	printf("this is stupid");
}

// main method
int main()
{
    // variable creation
	int child1; 
	int child2;
	int count = 1;
	int parent = getpid();

	// create shared memory segment & place the current value in it as the parent
	segment_id = shmget (IPC_PRIVATE, sizeof(int), IPC_CREAT | IPC_EXCL | S_IRUSR | S_IWUSR);
	int* sh_mem = (int*) shmat(segment_id, NULL, 0);
	*sh_mem = count;
	shmdt(sh_mem);

	// create the children & keep track of the PIDs
	child1 = fork();
	if(getpid() == parent) // if we are the parent, fork
	{
		child2 = fork();
	}

	if(getpid() == parent)
	{	
		// set shared pid to child1 pid
		segment_id_pid = shmget (IPC_PRIVATE, sizeof(int), IPC_CREAT | IPC_EXCL | S_IRUSR | S_IWUSR);
		int* sh_mem2 = (int*) shmat(segment_id_pid, NULL, 0);
		*sh_mem2 = child2;
		shmdt(sh_mem2);
		// begin counting process - call SIGUSR for child1
		printf("parent PID: %d child1 PID: %d child2 PID: %d\n",parent,child1,child2);
	}
	else
	{
		signal(SIGCONT,handle);
		kill(child1,SIGCONT);
	}


	// parent wait until child process killed
	sleep(5);
	//wait(NULL);
	printf("PID: %d end\n",getpid());
	return 0;
}
