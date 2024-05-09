/* Assembler code fragment for LC-2K */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define MAXLINELENGTH 1000
typedef struct label_cal
{
	char _label[7];
	int address;
}label_i;
label_i *label_array;
int readAndParse(FILE *, char *, char *, char *, char *, char *);
int isNumber(char *);
int label_search(label_i* arr, char * str,int num);
int check(label_i *arr, char *str, int num);
void check_reg(char *str);
int main(int argc, char *argv[]) 
{
	char *inFileString, *outFileString;
	FILE *inFilePtr, *outFilePtr;
	char label[MAXLINELENGTH], opcode[MAXLINELENGTH], arg0[MAXLINELENGTH], 
			 arg1[MAXLINELENGTH], arg2[MAXLINELENGTH];

	if (argc != 3) {
		printf("error: usage: %s <assembly-code-file> <machine-code-file>\n",
				argv[0]);
		exit(1);
	}

	inFileString = argv[1];
	outFileString = argv[2];

	inFilePtr = fopen(inFileString, "r");
	if (inFilePtr == NULL) {
		printf("error in opening %s\n", inFileString);
		exit(1);
	}
	outFilePtr = fopen(outFileString, "w");
	if (outFilePtr == NULL) {
		printf("error in opening %s\n", outFileString);
		exit(1);
	}

	/* here is an example for how to use readAndParse to read a line from
		 inFilePtr */
		/* reached end of file */
	
	int i = 0;
	int label_num;
	while ( readAndParse(inFilePtr, label, opcode, arg0, arg1, arg2) )
	{
		if ( label[0] != '\0' ) i++;

	}
	rewind(inFilePtr);
	label_num = i;
	label_array = (label_i *)malloc(sizeof(label_i) * label_num);
	i = 0;
	int j = -1;
	while ( readAndParse(inFilePtr, label, opcode, arg0, arg1, arg2) )
	{
		j++;
		if ( label[0] != '\0' )
		{
			if ( strlen(label) > 6 )exit(1);
			if ( check(label_array, label, i) )exit(1);
			
			strcpy(label_array[i]._label, label);
			label_array[i].address = j;
			i++;
			
		}
	}

	/* this is how to rewind the file ptr so that you start reading from the
		 beginning of the file */
	rewind(inFilePtr);


	/* after doing a readAndParse, you may want to do the following to test the
		 opcode */
	i = 0;
	while ( readAndParse(inFilePtr, label, opcode, arg0, arg1, arg2) )
	{
		
		int sum, inst, fld0,fld1,fld2;
		if ( !strcmp(opcode, "add") )
		{
			check_reg(arg0);
			check_reg(arg1);
			check_reg(arg2);
			inst = 0;
			fld0 = atoi(arg0);
			fld1 = atoi(arg1);
			fld2 = atoi(arg2);
			sum = (inst << 22) + (fld0 << 19) + (fld1 << 16) + fld2;
			fprintf(outFilePtr, "%d", sum);
			/* do whatever you need to do for opcode "add" */
		}
		else if ( !strcmp(opcode, "nor") )
		{
			check_reg(arg0);
			check_reg(arg1);
			check_reg(arg2);
			inst = 1;
			fld0 = atoi(arg0);
			fld1 = atoi(arg1);
			fld2 = atoi(arg2);
			sum = (inst << 22) + (fld0 << 19) + (fld1 << 16) + fld2;
			fprintf(outFilePtr, "%d", sum);

		}
		else if ( !strcmp(opcode, "lw") )
		{	
			inst = 2;
			check_reg(arg0);
			check_reg(arg1);
			fld0 = atoi(arg0);
			fld1 = atoi(arg1);
			if ( isNumber(arg2) )
			{
				if ( atoi(arg2) < -32768 || atoi(arg2) > 32767 )exit(1);
				if ( atoi(arg2) < 0 )
				{
					fld2 = (2 << 15) + atoi(arg2);
				}
				else fld2 = atoi(arg2);
			}
			else
			{
				fld2 = label_search(label_array, arg2, label_num);
			}

			sum = (inst << 22) + (fld0 << 19) + (fld1 << 16) + fld2;
			fprintf(outFilePtr, "%d", sum);
		}
		else if ( !strcmp(opcode, "sw") )
		{
			inst = 3;
			check_reg(arg0);
			check_reg(arg1);
			fld0 = atoi(arg0);
			fld1 = atoi(arg1);
			if ( isNumber(arg2) )
			{
				if ( atoi(arg2) < -32768 || atoi(arg2) > 32767 )exit(1);
				if ( atoi(arg2) < 0 )
				{
					fld2 = (2 << 15) + atoi(arg2);
				}
				else fld2 = atoi(arg2);
			}
			else
			{
				fld2 = label_search(label_array, arg2, label_num);
			}

			sum = (inst << 22) + (fld0 << 19) + (fld1 << 16) + fld2;
			fprintf(outFilePtr, "%d", sum);

		}
		else if ( !strcmp(opcode, "beq") )
		{
			inst = 4;
			check_reg(arg0);
			check_reg(arg1);
			fld0 = atoi(arg0);
			fld1 = atoi(arg1);
			if ( isNumber(arg2) )
			{
				if ( atoi(arg2) < -32768 || atoi(arg2) > 32767 )exit(1);
				if ( atoi(arg2) < 0 )
				{
					fld2 = (2 << 15) + atoi(arg2);
				}
				else fld2 = atoi(arg2);
			}
			else
			{
				fld2 = label_search(label_array, arg2, label_num) - i;
				if ( fld2 < 0 )
				{
					fld2 = ((2 << 15) - 1) + fld2;
				}
				else
				{
					fld2 = fld2 - 1;
				}
			}

			sum = (inst << 22) + (fld0 << 19) + (fld1 << 16) + fld2;
			fprintf(outFilePtr, "%d", sum);
		}
		else if ( !strcmp(opcode, "jalr") )
		{
			inst = 5;
			check_reg(arg0);
			check_reg(arg1);
			fld0 = atoi(arg0);
			fld1 = atoi(arg1);
			sum = (inst << 22) + (fld0 << 19) + (fld1 << 16);
			fprintf(outFilePtr, "%d", sum);
		}
		else if ( !strcmp(opcode, "halt") )
		{
			inst = 6;
			sum = (inst << 22);
			fprintf(outFilePtr, "%d", sum);
		}
		else if ( !strcmp(opcode, "noop") )
		{
			inst = 7;
			sum = (inst << 22);
			fprintf(outFilePtr, "%d", sum);
		}
		else if ( !strcmp(opcode, ".fill") )
		{
			if ( isNumber(arg0) )
			{
				fprintf(outFilePtr, "%d", atoi(arg0));
			}
			else
			{
				fprintf(outFilePtr, "%d", label_search(label_array,arg0,label_num));
			}
		}

		else                                       //unknown instruction
		{
			exit(1);				//wrong instruction
		}
		i++;
		fputs("\n", outFilePtr);
	}
	if (inFilePtr) {
		fclose(inFilePtr);
	}
	if (outFilePtr) {
		fclose(outFilePtr);
	}
	exit(0);
	return(0);
}

/*
 * Read and parse a line of the assembly-language file.  Fields are returned
 * in label, opcode, arg0, arg1, arg2 (these strings must have memory already
 * allocated to them).
 *
 * Return values:
 *     0 if reached end of file
 *     1 if all went well
 *
 * exit(1) if line is too long.
 */
int readAndParse(FILE *inFilePtr, char *label, char *opcode, char *arg0,
		char *arg1, char *arg2)
{
	char line[MAXLINELENGTH];
	char *ptr = line;
	/* delete prior values */
	label[0] = opcode[0] = arg0[0] = arg1[0] = arg2[0] = '\0';

	/* read the line from the assembly-language file */
	if (fgets(line, MAXLINELENGTH, inFilePtr) == NULL) {
		/* reached end of file */
		return(0);
	}

	/* check for line too long (by looking for a \n) */
	if (strchr(line, '\n') == NULL) {
		/* line too long */
		printf("error: line too long\n");
		exit(1);
	}

	/* is there a label? */
	ptr = line;
	if (sscanf(ptr, "%[^\t\n\r ]", label)) {
		/* successfully read label; advance pointer over the label */
		ptr += strlen(label);
	}

	/*
	 * Parse the rest of the line.  Would be nice to have real regular
	 * expressions, but scanf will suffice.
	 */
	sscanf(ptr, "%*[\t\n\r ]%[^\t\n\r ]%*[\t\n\r ]%[^\t\n\r ]%*[\t\n\r ]%"
			"[^\t\n\r ]%*[\t\n\r ]%[^\t\n\r ]", opcode, arg0, arg1, arg2);
	return(1);
}

int isNumber(char *string)
{
	/* return 1 if string is a number */
	int i;
	return( (sscanf(string, "%d", &i)) == 1);
}

int label_search(label_i *arr, char* str, int num) //label_search(label_array, arg2,label_num)
{
	for ( int i = 0; i < num; i++ )
	{
		if ( !strcmp(arr[i]._label, str) )
		{
			return arr[i].address;
		}

	}

}
int check(label_i *arr, char *str, int num)
{
	int i = 0;
	while(i<num){
		if ( !strcmp(arr[i]._label, str) )
		{
			return 1;
		}
		i++;
	}
	return 0;
}
void check_reg(char *str)
{
	if ( !isNumber(str) ) exit(1);
	if ( atoi(str) < 0 || atoi(str) > 7 ) exit(1);
}
