#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "vm.h"

#define MAX_FILENAME_SIZE 20
#define DEFAULT_INIT_MEMORY_SIZE 7

int main(int argc, char *argv[]) 
{
  if (argc != 2 ) {
      puts("Uso: ./arassembler <programa.ara>");
      return 1;
  }
    
  char filename[MAX_FILENAME_SIZE];
  strcpy(filename, argv[1]);
  
  /* Gera um programa como array de ByteCode a 
   * partir das instruções de máquina */
  Memoria *programa = assembler(filename);
  // Imprime o array de ByteCode no formato (opcode instrução, operando)
  for (int i = 0; i < programa->count; i++) 
      printf(" %d %d ", programa->instrucoes[i].instrucao, 
             programa->instrucoes[i].operando );
  printf("\n");
  
  // Inicializa a máquina Virtual
  VM *vm = init_VM(programa);
  
  // Executa o programa que está carregado nela
  executar(vm);
  
  // Libera o array de ByteCode
  free_chunk(programa);
  return 0;
}

// Inicializa a VM
VM* init_VM(Memoria *prog)
{
  VM *vm = (VM *) malloc (sizeof(VM));
  vm->pc = 0;
  vm->programa = prog;
  
  // Inicialização das Stacks
  // variáveis locais dentro de uma função em execução
  vm->valores = init_Stack(DEFAULT_INIT_MEMORY_SIZE);
  /* Para manipular os argumentos passados quando uma função
  em execução chama outra */
  vm->chamada = init_Stack(DEFAULT_INIT_MEMORY_SIZE);
  // stack trace para saber que função está em execução - topo
  vm->funcs = init_Stack(DEFAULT_INIT_MEMORY_SIZE);      
}

// Simula a execução de um programa na VM
void executar(VM *vm)
{
  // aliases
  Memoria *programa = vm->programa; 
  int *pc = &(vm->pc);
  
  while(1) {
    ByteCode instrucao = programa->instrucoes[*pc++];
    printf("Decodificando: %d %d\n", instrucao.instrucao, instrucao.operando);
    switch(instrucao.instrucao) {
        case OP_EXIT:
            break;        
        case OP_NUMBER:
            push(vm->valores, instrucao.operando);
            break;
        case OP_ADD:
            Byte operando1 = pop(vm->valores);
            Byte operando2 = pop(vm->valores);
            Byte res = operando1 + operando2;
            push(vm->valores, res);
            break;
        case OP_SUB:
            Byte operando1 = pop(vm->valores);
            Byte operando2 = pop(vm->valores);
            Byte res = operando1 - operando2;
            push(vm->valores, res);
            break;
        case OP_MUL:
            Byte operando1 = pop(vm->valores);
            Byte operando2 = pop(vm->valores);
            Byte res = operando1 * operando2;
            push(vm->valores, res);
            break;
        case OP_DIV:
            Byte operando1 = pop(vm->valores);
            Byte operando2 = pop(vm->valores);
            Byte res = operando1 / operando2;
            push(vm->valores, res);
            break;
        case OP_POW:
            Byte operando1 = pop(vm->valores);
            Byte operando2 = pop(vm->valores);
            Byte res = pow(operando1, operando2);
            push(vm->valores, res);
            break;
        case OP_NOT:
            break;
        case OP_NEG;
            break;
        case OP_
        
        
    }
    
    */
    
  }
  
  
