#include <stdio.h>
#include <stdlib.h>
#include "memoria.h"

// Inicializa um chunk da estrutura de Memória com size passado
Memoria* init_chunk(int size) 
{
  Memoria *chunk = (Memoria *) malloc( sizeof( Memoria ) );
  chunk->count  = 0;
  chunk->capacity = size;
  chunk->instrucoes = (ByteCode *) malloc( chunk->capacity * sizeof( ByteCode ) );
  return chunk;  
}

// Escreve dinâmicamente na estrutura de Memória - Inserção O(1)
void write_chunk(Memoria *memoria, ByteCode bytecode) 
{
   	if (memoria->capacity < memoria->count + 1) 
        memoria->instrucoes = (ByteCode *) realloc(memoria->instrucoes, 
                                                   memoria->capacity * 2);        
    
    if (memoria->instrucoes != NULL) {   
        memoria->instrucoes[memoria->count].instrucao = bytecode.instrucao;
        memoria->instrucoes[memoria->count].operando = bytecode.operando;
        memoria->count++;
        memoria->capacity *= 2;
    }
    else puts("Memoria: Falha no realloc");
}

// Libera a estrutura alocada para Memória
void free_chunk(Memoria *memoria) 
{
  free(memoria->instrucoes);
  free(memoria);
}

