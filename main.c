#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define ARRAY_SIZE 10

void mergeSort(int* ar);
void mergeSortHelper(int* ar, int begin, int end);
void displayArray(int* ar, int length);

int main() 
{
   time_t t;
   int* ar = (int*)malloc(ARRAY_SIZE * sizeof(int)); //by default malloc returns a void*
   
   //srand(time(&t));

   for(int i = 0; i < ARRAY_SIZE; i++) 
   {
      ar[i] = rand() % 20;
   }
   
   //displayArray(ar, ARRAY_SIZE);
   mergeSort(ar);
   displayArray(ar, ARRAY_SIZE);
   
   free(ar);
   return 0;
}

void displayArray(int* ar, int length)
{
    for(int i = 0; i < length; i++)
    {
        printf("%d\n", ar[i]);
    }
}

void mergeSort(int* ar)
{
    mergeSortHelper(ar, 0, ARRAY_SIZE - 1);
}

void mergeSortHelper(int* ar, int begin, int end)
{
    //is this portion of the array trivially sorted?
    if(begin != end)
    {
        int begin1 = begin;
        int end1 = (end + begin)/2;
        int begin2 = end1 + 1;
        int end2 = end;
        
        mergeSortHelper(ar, begin1, end1);
        mergeSortHelper(ar, begin2, end2);
        
        
        //now merge
        int tempLength = end - begin + 1;
        int temp[tempLength];
        int pos1 = begin1;
        int pos2 = begin2;
        
        for(int i = 0; i < tempLength; i++)
        {
            if(pos1 <= end1 && pos2 <= end2)
            {
                if(ar[pos1] < ar[pos2])
                {
                    temp[i] = ar[pos1];
                    pos1++;
                }
                else
                {
                    temp[i] = ar[pos2];
                    pos2++;
                }
            }
            else if(pos1 <= end1)
            {
                temp[i] = ar[pos1];
                pos1++;
            }
            else
            {
                temp[i] = ar[pos2];
                pos2++;
            }
        }
        
        //copy temp back over ar
        int tempPos = 0;
        for(int i = begin; i <= end; i++)
        {
            ar[i] = temp[tempPos];
            tempPos++;
        }
    }
}