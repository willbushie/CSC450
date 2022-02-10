#include<stdio.h>
#include<stdlib.h>

// code given for this assingment
int main(void)
{
    // open file for reading only
    FILE* fptr = fopen("info.dat","r");
    // int that will hold the read value
    int num;

    // reading the file and updating the value
    fscanf(fptr, "%d", &num);
    // print the result to the screen
    printf("the value is: %d\n", num);
    // update the value
    num++;
    // close the file
    fclose(fptr);
    // open the file for writing only
    fptr = fopen("info.dat","w");
    // write the new value to the file
    fprintf(fptr, "%d", num);
    // close the file
    fclose(fptr);
}

// previous HW solution
int main()
{
    FILE* fptr = fopen("info.dat","w+");
    int count;
    // loop to create the child processes and print their specific number
    while(count < 10)
    {
        fscanf(fptr, "%d", &count);
        if(fork() == 0)
        {
            fprintf(fptr ,"%d", count);
            kill(getpid(),SIGKILL);
        }
        else if(getpid() != 0)
        {
            count++;
        }
    }
    sleep(1);
    fclose(fptr);
    return 0;
}
