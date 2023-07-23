#ifndef __STACK__H
#define __STACK_H

typedef unsigned int Byte;

// Estrutura para modelar uma stack dinâmica
typedef struct {
  Byte *bytes; // array (genérico) de bytes  
  int topo;
  int capacity;
} Stack;

/* Funções para manipular uma Stack
 Inicializa uma Stack
 Adiciona no topo
 Retira do topo 
 Libera uma Stack */
Stack *init_Stack(int size);
void push(Stack *stack, Byte byte);
Byte pop(Stack *stack);
void free_Stack(Stack *stack);

#endif
