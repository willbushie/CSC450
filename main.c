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

#define ARRAY_SIZE 10

// global variables
int* sh_mem;

// function declarations
void mergeSort(int* ar);
void mergeSortHelper(int* ar, int begin, int end);
void displayArray(int* ar, int length);


int main() 
{
    time_t t;
    int* array = (int*)malloc(ARRAY_SIZE * sizeof(int)); //by default malloc returns a void*

    //srand(time(&t)); // randomly seed a list

    for(int index = 0; index < ARRAY_SIZE; index++) 
    {
        array[index] = rand() % 20;
    }

    //displayArray(ar, ARRAY_SIZE);
    displayArray(array, ARRAY_SIZE);
    mergeSort(array);
    displayArray(array, ARRAY_SIZE);

    free(array); // sys call
    return 0;
}

// have an array display itself
void displayArray(int* array, int length)
{
    for(int index = 0; index < length; index++)
    {
        printf("%d\n", array[index]);
    }
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
        fork();
        if(getpid() != parent)
        {
            childA = getpid();
        }
        if(getpid() == parent)
        {
            childB = fork();
        }
        // print statements to check process id's
        if(getpid() == parent)
        {
            //printf("1. children created. childA: %d, childB: %d, parent: %d, my PID: %d\n",childA,childB,parent,getpid());
        }
        if(getpid() != parent)
        {
            //printf("children created. childA: %d, childB: %d, parent: %d, my PID: %d\n",childA,childB,parent,getpid());
        }

        // divide the operations for each child & call merge sort
        if(getpid() != parent && getpid() == childA)
        {
            //printf("i'm a childA PID: %d\n",getpid());
            mergeSortHelper(array, leftmostA, rightmostA);
            kill(getpid(),SIGKILL);
        }
        else if(getpid() != parent && getpid() == childB)
        {
            //printf("i'm a childB PID: %d\n",getpid());
            mergeSortHelper(array, leftmostB, rightmostB);   
            kill(getpid(),SIGKILL);
        }
        else if(getpid() == parent)
        {
            sleep(1);
            wait(NULL);
        }
        
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
        for(int i = leftmost; i <= rightmost; i++)
        {
            array[i] = temp[tempPos];
            tempPos++;
        }
    }
}