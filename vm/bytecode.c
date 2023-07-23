#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "bytecode.h"

ByteCode* init_ByteCode() 
{
    ByteCode *bytecode = (ByteCode *) malloc (sizeof ( ByteCode) );
    return bytecode;  
}

// Converte cada linha de instrução no seu respectivo ByteCode 
void c2bc(ByteCode *bytecode, char linhaInstrucao[])
{
  // Mapeamento das instruções em seus OpCodes com seus resp. operandos
  if (strncmp(linhaInstrucao, "EXIT", 4) == 0) {
      bytecode->instrucao = OP_EXIT; bytecode->operando = -1;     
    }
  else if ( strncmp(linhaInstrucao, "NUMBER", 6) == 0 ) {
      bytecode->instrucao = OP_NUMBER; 
      bytecode->operando = atoi(strtok(linhaInstrucao + 6, " "));
    }    
  else if ( strncmp(linhaInstrucao, "ADD", 3) == 0 ) {
      bytecode->instrucao = OP_ADD; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "SUB", 3) == 0 ) {
      bytecode->instrucao = OP_SUB; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "MUL", 3) == 0 ) {
      bytecode->instrucao = OP_MUL; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "DIV", 3) == 0 ) {
      bytecode->instrucao = OP_DIV; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "POW", 3) == 0 ) {
      bytecode->instrucao = OP_DIV; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "NOT", 3) == 0 ) {
      bytecode->instrucao = OP_NOT; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "NEG", 3) == 0 ) {
      bytecode->instrucao = OP_NEG; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "LT", 2) == 0 ) {
      bytecode->instrucao = OP_LT;  bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "GT", 2) == 0 ) {
      bytecode->instrucao = OP_GT; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "EQ", 2) == 0 ) {
      bytecode->instrucao = OP_EQ; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "NEQ", 3) == 0 ) {
      bytecode->instrucao = OP_NEQ; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "LE", 2) == 0 ) {
      bytecode->instrucao = OP_LE; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "GE", 2) == 0 ) {
      bytecode->instrucao = OP_GE; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "GEQ", 3) == 0 ) {
      bytecode->instrucao = OP_GEQ; bytecode->operando = -1;
  }
  else if ( strncmp(linhaInstrucao, "LOAD", 4) == 0 ) {
      bytecode->instrucao = OP_LOAD;      
      bytecode->operando = atoi(strtok(linhaInstrucao + 4, " "));
    }
  else if ( strncmp(linhaInstrucao, "STORE", 5) == 0 ) {
      bytecode->instrucao = OP_STORE;      
      bytecode->operando = atoi(strtok(linhaInstrucao + 5, " "));
    }
  else if ( strncmp(linhaInstrucao, "POP", 3) == 0 ) {
      bytecode->instrucao = OP_POP; bytecode->operando = -1;
  }
   else if ( strncmp(linhaInstrucao, "DUP", 3) == 0 ) {
      bytecode->instrucao = OP_DUP; bytecode->operando = -1;
  }
   else if ( strncmp(linhaInstrucao, "PRINT", 5) == 0 ) {
        bytecode->instrucao = OP_PRINT; bytecode->operando = -1;
  }
    else if ( strncmp(linhaInstrucao, "JUMP_TRUE", 9) == 0 ) 
      bytecode->instrucao = OP_JUMPTRUE;      
   else if ( strncmp(linhaInstrucao, "JUMP_FALSE", 10) == 0 ) 
      bytecode->instrucao = OP_JUMPFALSE;      
   else if ( strncmp(linhaInstrucao, "JUMP", 4) == 0 )
      bytecode->instrucao = OP_JUMP;      
   
   else if ( strncmp(linhaInstrucao, "CALL", 4) == 0 ) {
      bytecode->instrucao = OP_CALL;      
      bytecode->operando = atoi(strtok(linhaInstrucao + 4, " "));
    }
   else if ( strncmp(linhaInstrucao, "RETURN", 6) == 0 ) {
      bytecode->instrucao = OP_RETURN; bytecode->operando = -1;
    }  
} 

void free_ByteCode(ByteCode *bytecode) 
{
    free(bytecode);    
}

