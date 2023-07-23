#include <stdio.h>
#include <stdlib.h>
#include "stack.h"

Stack *init_Stack(int size) 
{
    Stack *stack = (Stack *) malloc(sizeof(Stack));
    stack->capacity = size;
    stack->topo = 0;
    stack->bytes = (Byte *) malloc(stack->capacity * sizeof(Byte));
    return stack;
}

// Inserção O(1)
void push(Stack *stack, Byte byte )
{
    if (stack->capacity < stack->topo + 1) 
        stack->bytes = (Byte *) realloc(stack->bytes, 
                                        stack->capacity * 2);        
    
    if (stack->bytes != NULL) {   
        stack->bytes[stack->topo] = byte;
        stack->topo++;
        stack->capacity *= 2;
    }
    else puts("Stack: Falha no realloc");
    
}

// Remoção O(1) - LIFO
Byte pop(Stack *stack) 
{
    Byte b = stack->bytes[stack->topo];
    stack->topo -= 1;
    return b;
}

// Libera a Stack da memória
void free_Stack(Stack *stack)
{
    free(stack->bytes);
    free(stack);    
}
