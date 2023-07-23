#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "jump.h"

// Constroi um array dinâmico de Jump
Jumps* init_Jumps(int size) 
{
    Jumps *jumps = (Jumps *) malloc(sizeof(Jumps));
    jumps->capacity = size;
    jumps->count = 0;
    jumps->array = (Jump *) malloc( jumps->capacity * sizeof(Jump));
    return jumps;    
}

// Insere no array dinâmico de Jump - Inserção O(1)
void insere_Jump(Jumps *jumps, Jump jmp)
{
    if ( jumps->capacity < jumps->count + 1 )
        jumps->array = (Jump *) realloc (jumps->array, jumps->capacity * 2);
    
    if (jumps->array != NULL ) {
        jumps->array[jumps->count] = jmp;
        jumps->count++;
        jumps->capacity *= 2;
    }
    else puts("Jump: Falha no realloc");
}
// Busca um Jump no array por label - Implementação O(n)
Jump* busca_Jump(Jumps *jumps, char *label)
{
    for (int i = 0; i < jumps->count; i++) 
        if ( strcmp( jumps->array[i].label, label) == 0 )
            return jumps->array + i;
    
    return NULL;
}

/* Seta a linha de um Jump do array
 Retorna 0 em caso de sucesso e -1 em caso de falha */
int setLine_Jump(Jumps *jumps, char *label, int linha)
{
    Jump *jmp = busca_Jump(jumps, label);
    if (jmp == NULL) return -1;
    
    jmp->linha = linha; 
    return 0;
}

// Pega a linha de um Jump do array 
int getLine_Jump(Jumps *jumps, char *label)
{
    Jump *jmp = busca_Jump(jumps, label);
    return jmp->linha;    
    
}
void free_Jumps(Jumps *jumps)
{
    free(jumps->array);
    free(jumps);
}
