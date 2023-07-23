#ifndef _MEMORIA_H
#define _MEMORIA_H

#include "bytecode.h"

// Estrutura abstrata para Memória
typedef struct {
  // Array dinâmico de byteCodes
  ByteCode *instrucoes;
  int count; // informa quantos bytescodes estão na memoria
  int capacity; // capacidade máxima da memória
} Memoria;

/* Funções que manipulam a estrutura de Memória
 * Inicializa uma área de memória
 * Escreve numa área de memória alterando-a dinamicamente
 * Libera uma área de memória
 * Monta um array dinâmico de bytes */
Memoria *init_chunk(int size);
void write_chunk(Memoria *memoria, ByteCode bytecode);
void free_chunk(Memoria *memoria);



#endif
