/* LC-2K Instruction-level simulator */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define NUMMEMORY 65536 /* maximum number of words in memory */
#define NUMREGS 8 /* number of machine registers */
#define MAXLINELENGTH 1000 
typedef struct stateStruct {
    int pc;
    int mem[NUMMEMORY];
    int reg[NUMREGS];
    int numMemory;
} stateType;

void printState(stateType *);
int convertNum(int num);

int main(int argc, char *argv[])
{
    char line[MAXLINELENGTH];
    stateType state;
    FILE *filePtr;

    if (argc != 2) {
        printf("error: usage: %s <machine-code file>\n", argv[0]);
        exit(1);
    }

    filePtr = fopen(argv[1], "r");
    if (filePtr == NULL) {
        printf("error: can't open file %s", argv[1]);
        perror("fopen");
        exit(1);
    }

    /* read in the entire machine-code file into memory */
    for (state.numMemory = 0; fgets(line, MAXLINELENGTH, filePtr) != NULL;
            state.numMemory++) {

        if (sscanf(line, "%d", state.mem+state.numMemory) != 1) {
            printf("error in reading address %d\n", state.numMemory);
            exit(1);
        }
        printf("memory[%d]=%d\n", state.numMemory, state.mem[state.numMemory]);
    }
    state.pc = 0;
    for ( int i = 0; i < NUMREGS; i++ ) { state.reg[i] = 0; }
    
    while ( state.pc < state.numMemory )
    {
        int target_line = state.mem[state.pc];
        int opcode = (target_line >> 22);
        int fld0 = (target_line - (opcode << 22)) >> 19;
        int fld1 = (target_line - ((opcode << 22) + (fld0 << 19))) >> 16;
        int fld2 = (target_line - ((opcode << 22) + (fld0 << 19) + (fld1 << 16)));
        if ( opcode == 0 )   //add .fill
        {
            if ( (target_line >> 16) > 0 )// add
            {
                state.reg[fld2] = state.reg[fld0] + state.reg[fld1];
            }
            state.pc++;
        }
        else if ( opcode == 1 ) //nor
        {
            state.reg[fld2] = ~(state.reg[fld0] | state.reg[fld2]);
            state.pc++;
        } 
        else if ( opcode == 2 )     //lw 
        {
            state.reg[fld1] = state.mem[state.reg[fld0] + convertNum(fld2)];
            state.pc++;
        }
        else if ( opcode == 3 )     //sw
        {
            state.mem[state.reg[fld0] + convertNum(fld2)] = state.reg[fld1];
            state.pc++;
        }
        else if ( opcode == 4 )     //beq
        {
            if ( state.reg[fld0] == state.reg[fld1] )
            {
                state.pc = (state.pc + 1) + convertNum(fld2);
            }
            else
            {
                state.pc = state.pc + 1;
            }
        }
        else if ( opcode == 5 )     //jalr
        {
            state.reg[fld1] = state.pc + 1;
            state.pc = state.reg[fld0];
        }
        else if ( opcode == 6 )     //halt
        {
            break;
        }
        else if ( opcode == 7 )     //noop
        {
            state.pc++;
        }
        else
        {
            exit(1);
        }
    }
    printState(&state);
		/* TODO: */
    return(0);
}

void printState(stateType *statePtr)
{
    int i;
    printf("\n@@@\nstate:\n");
    printf("\tpc %d\n", statePtr->pc);
    printf("\tmemory:\n");
    for (i = 0; i < statePtr->numMemory; i++) {
        printf("\t\tmem[ %d ] %d\n", i, statePtr->mem[i]);
    }
    printf("\tregisters:\n");
    for (i = 0; i < NUMREGS; i++) {
        printf("\t\treg[ %d ] %d\n", i, statePtr->reg[i]);
    }
    printf("end state\n");
}

int convertNum(int num)
{
	/* convert a 16-bit number into a 32-bit Linux integer */
	if (num & (1 << 15)) {
		num -= (1 << 16);
	}
	return (num);
}
