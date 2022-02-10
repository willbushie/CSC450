#include <stdio.h>
#include <stdlib.h>

int main(void) 
{
	// reading and writing to a file
	FILE* fptr = fopen("info.dat","w+");
	// hold the file contents
	int num;
	// reading from the file & updating the value
	fscanf(fptr, "%d", &num);
	// print the number to the screen/terminal
	printf("the value is: %d\n", num);
	// advance the count
	num++;
	// writing to the file
	fprintf(fptr, "%d", num);
	// closing the file
	fclose(fptr);
}
