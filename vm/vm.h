#ifndef __VIRTUAL_MACHINE_H
#define __VIRTUAL_MACHINE_H

#include "assembler.h"
#include "stack.h"

// Estrutura para modelar uma máquina virtual
typedef struct {
  // instruções de um programa na memória
  Memoria *programa;                       
  int pc; // registrador PC
  
  // Regiões temporárias de memória
  Stack *valores; // pilha de valores
  Stack *chamada; // pilha de chamadas
  Stack *funcs;	// stack trace de funções
} VM;

/* Funções para manipular a VM
 * Inicializar a vm com um programa na mem.
 * Executar o programa */
VM* init_VM(Memoria *prog);
void executar(VM *vm);

#endif
