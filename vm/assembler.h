#ifndef _ASSEMBLER_H
#define _ASSEMBLER_H

#include "memoria.h"
#include "bytecode.h"
#include "jump.h"

// Funções Helper

// Lê uma linha do FILE de entrada
char* readline(FILE *filename);

/* Varre o programa e salva num array de Jump 
  pares da forma (Label, Linha) onde Label é encontrada
  após uma instrução JUMP e Linha é encontrada após a 
  a checagem se uma linha inicia com label. */
void find_LineToLabel(FILE *file, Jumps *jumps);

/* Verifica se a instrução é um jump
Caso sim retorna a Label referente ao jump. Se não, retorna "NULL" */
char* checkJump(char *instrucao);

/* Verifica se a instrução da linha inicia com label 
 Caso sim, escreve no ponteiro de char *label, essa
 label (string). Caso não, retorna NULL. */
int checkLabel(char *linha, char *label);

// Monta um array dinâmico de bytes 
Memoria* assembler(char *filename);

#endif
