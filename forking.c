// CSC 450 - HW #2 - forking code in order to count from 0 to 9

#include <stdio.h>
#include <unistd.h>
#include <signal.h>

int main()
{
    int count = 0;
    // loop to create the child processes and print their specific number
    while(count < 10)
    {
        if(fork() == 0)
        {
            printf("child PID: %d PPID: %d: %d\n",getpid(),getppid(),count);
            kill(getpid(),SIGKILL);
        }
        else if(getpid() != 0)
        {
            //printf("parent PID: %d %d: %d\n",getpid(),getppid(),count);
            count++;
        }
    }
    sleep(1);
    return 0;
}
