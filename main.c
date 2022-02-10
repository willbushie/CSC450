// CSC 450 - HW #3 - forking code in order to count from 0 to 9 by reading and writing to a file
// 2.5 hours so far

#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<signal.h>

int main(void)
{
    int count;
    int child1;
    int child2;
    // create the child process (child1, child2) from parent
    int parent = getpid();
    fork();
    // if we aren't the parent, assign child1
    if(getpid() != parent)
    {
        child1 = getpid();
    }
    // if we are the parent, fork, and if not parent, assign child2
    if(getpid() == parent)
    {
        fork();
        // if we are not the parent
        if(getpid() != parent)
        {
            child2 = getpid();
        }
    }

    // counting process
    // if we are child processes (child1, child2)
    for(int num = 0; num < 10; num++)
    {
        if(getpid() != parent)
        {
            // open file for reading
            FILE* fptr = fopen("info.dat","r");
            // read in the value and update the count
            fscanf(fptr,"%d",&count);
            // print the value from each child, pid, ppid 
            printf("child PID: %d PPID: %d count: %d\n",getpid(),getppid(),count);
            // allow child1 to do the operation
            if(count+1%2 == 0 && getpid() == child1)
            {
                // close reading, open writing, advance, write, close writing
                fclose(fptr);
                fptr = fopen("info.dat","w");
                count++;                
                fprintf(fptr,"%d",count);
                fclose(fptr);
            }
            else
            {
                // close reading
                fclose(fptr);
            }
            // allow child2 to do the operation
            if (count+1%2 != 0 && getpid() == child2)
            {
                // close reading, open writing, advance, write, close writing
                fclose(fptr);
                fptr = fopen("info.dat","w");
                count++;
                fprintf(fptr,"%d",count);
                fclose(fptr);
            }
            else
            {
                // close reading
                fclose(fptr);
            }
            sleep(1);
        }
    }
    // kill the each child process
    kill(child1,SIGKILL);
    kill(child2,SIGKILL);
    // make parent process wait for child processes
    wait(NULL);
    return 0;
}
