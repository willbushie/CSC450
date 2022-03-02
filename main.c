// CSC450 - HW#6 - Merge Sort implementation using child processes and shared memory

// includes section
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/wait.h>
#include <sys/shm.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <signal.h>
#include <sys/ipc.h>
#include <sys/types.h>

#define ARRAY_SIZE 10

// global variables
int segment_id;
int* sh_mem;

// function declarations
void mergeSort(int* array);
void mergeSortHelper(int* array, int leftmost, int rightmost);
void displayArray(int* array, int length);
void testSharedMem();

int main() 
{
    // create the shared mememory
    segment_id = shmget (IPC_PRIVATE, ARRAY_SIZE * sizeof(int*), IPC_CREAT | IPC_EXCL | S_IRUSR | S_IWUSR);
    sh_mem = (int*) shmat(segment_id, NULL, 0);
    
    time_t t;
    srand(time(&t)); // randomly seed a list

    for(int index = 0; index < ARRAY_SIZE; index++) 
    {
        sh_mem[index] = rand() % 20;
    }

    displayArray(sh_mem,ARRAY_SIZE);
    mergeSort(sh_mem);
    //testSharedMem();
    displayArray(sh_mem, ARRAY_SIZE);
    shmdt(sh_mem);

    return 0;
}

// have an array display itself
void displayArray(int* array, int length)
{
    printf("PID: %d - ",getpid());
    for(int index = 0; index < length; index++)
    {
        printf("%d ", array[index]);
    }
    printf("\n");
}

// merge sort the array
void mergeSort(int* array)
{
    mergeSortHelper(array, 0, ARRAY_SIZE - 1);
}

// merge sort "helper" (main execution code)
void mergeSortHelper(int* array, int leftmost, int rightmost)
{
    // is this portion of the array trivially sorted? (single array item?)
    if(leftmost != rightmost)
    {
        int leftmostA = leftmost;
        int rightmostA = (rightmost + leftmost)/2;
        int leftmostB = rightmostA + 1;
        int rightmostB = rightmost;
        
        // create two child process - parent waits for successful execution
        int parent = getpid();
        int childA, childB;
        int decide = 1;
        childA = fork();
        if(getpid() == parent)
        {
            childB = fork();
            if(getpid() != parent)
            {
                decide = 2;
            }
        }
        if(getpid() != parent)
        {   
            // divide the operations for each child & call merge sort
            if(decide % 2 != 0)
            {
                mergeSortHelper(array, leftmostA, rightmostA);
                wait(NULL);
                kill(getpid(),SIGKILL);
            }
            else if(decide % 2 == 0)
            {
                mergeSortHelper(array, leftmostB, rightmostB);
                wait(NULL);
                kill(getpid(),SIGKILL);
            }
        }

        // only uncomment if using a single process to run this function
        //mergeSortHelper(array, leftmostA, rightmostA);
        //mergeSortHelper(array, leftmostB, rightmostB);
        // parent wait for children execution
        wait(NULL);
        sleep(1);

        //now merge
        int tempLength = rightmost - leftmost + 1;
        int temp[tempLength];
        int pos1 = leftmostA;
        int pos2 = leftmostB;
        
        for(int currIndex = 0; currIndex < tempLength; currIndex++)
        {
            if(pos1 <= rightmostA && pos2 <= rightmostB)
            {
                if(array[pos1] < array[pos2])
                {
                    temp[currIndex] = array[pos1];
                    pos1++;
                }
                else
                {
                    temp[currIndex] = array[pos2];
                    pos2++;
                }
            }
            else if(pos1 <= rightmostA)
            {
                temp[currIndex] = array[pos1];
                pos1++;
            }
            else
            {
                temp[currIndex] = array[pos2];
                pos2++;
            }
        }
        
        //copy temp back over array
        int tempPos = 0;
        for(int index = leftmost; index <= rightmost; index++)
        {
            array[index] = temp[tempPos];
            tempPos++;
        }
    }
}

// testing shared memeory
void testSharedMem()
{
    // create child process - parent waits for successful execution
    int parent = getpid();
    int child;
    printf("parent PID: %d\n",getpid());
    child = fork();
    if(getpid() != parent)
    {   
        printf("made it in PID: %d\n",getpid());
        sh_mem = (int*) shmat(segment_id, NULL, 0); 
        sh_mem[0] = 100;
        sh_mem[9] = 200;
        displayArray(sh_mem,ARRAY_SIZE);
        shmdt(sh_mem);
        kill(getpid(),SIGKILL);
    }
    wait(NULL);
}