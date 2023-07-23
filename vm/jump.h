#ifndef _JUMP_H
#define _JUMP_H

#define MAX_LABEL_SIZE 5

/* Estrutura para representar um Jump 
 Par ordenado Jump = (label, linha)
 */
typedef struct {
    char label[MAX_LABEL_SIZE]; // primary key
    int linha;
} Jump;

// Array dinâmico de Jump
typedef struct {
    Jump *array;
    int count;
    int capacity;
} Jumps;

/* Funções que manipulam um array de Jump
  + Inicializa um array dinâmico de Jump com dado size
  + Insere no array de Jump
  + Busca Jump no array por label
  + Seta a linha de um Jump do array 
  + Get the linha de um Jump
  + Libera um array de Jump */
Jumps *init_Jumps(int size);
void insere_Jump(Jumps *jumps, Jump jmp);
Jump* busca_Jump(Jumps *jumps, char *label);
int setLine_Jump(Jumps *jumps, char *label, int linha);
int getLine_Jump(Jumps *jumps, char *label);
void free_Jumps(Jumps *jumps);

#endif

