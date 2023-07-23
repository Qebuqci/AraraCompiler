#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "memoria.h"
#include "bytecode.h"
#include "jump.h"

// Assembler as an individual file.

#define MAX_FILENAME_SIZE 20
#define MAX_INSTRUCTION_LEN 10
#define DEFAULT_INIT_MEMORY_SIZE 7
#define MAX_LABEL_LEN 10

// Protótipo das funções helper
char* readline(FILE *filename);
void find_LineToLabel(FILE *file, Jumps *jumps);
char* checkJump(char *instrucao);
int checkLabel(char *linha, char *label);

Memoria* assembler(char *filename);

int main(int argc, char *argv[])
{
  if (argc != 2 ) {
      puts("Uso: ./arassembler <programa.ara>");
      return 1;
  }
    
  char filename[MAX_FILENAME_SIZE];
  strcpy(filename, argv[1]);
  // Gera o array de ByteCode
  Memoria* arrayBytes = assembler(filename); 
  // Imprime o array de ByteCode
  for (int i = 0; i < arrayBytes->count; i++) 
      printf(" %d %d ", arrayBytes->instrucoes[i].instrucao, 
             arrayBytes->instrucoes[i].operando );
  printf("\n");
  
  // Libera o array de ByteCode
  free_chunk(arrayBytes);
  
  return 0;
}

// Monta um array dinâmico de bytes 
Memoria* assembler(char *filename) 
{
  // Tratamento de arquivo source de entrada
  FILE *file = fopen(filename, "r");
  if ( file == NULL ) {
    perror("Unable to open file!");
    return NULL;
  }
  
  // Inicialização constante de memória
  Memoria *memoria = init_chunk(DEFAULT_INIT_MEMORY_SIZE);
  
  // Inicialização de um array de jumps
  Jumps *jumps = init_Jumps(DEFAULT_INIT_MEMORY_SIZE);
  
  // Primeira varredura para encontrar as linhas das labels
  find_LineToLabel(file, jumps);
  
  /* Segunda varredura para substituir as labels dos jumps
  pelas linhas encontradas na primeira varredura  */
  fseek(file, 0, SEEK_SET );
  while ( !feof(file) ) {  
    
    // Lê a linha da entrada
    char *linha = readline(file); //printf("%s", linha); 
        
    // Inicializa um ByteCode para essa instrução
    ByteCode *bytecode = init_ByteCode();
  	
    // Se a instrução da linha começa com label
    char label[MAX_LABEL_LEN];
    int res = checkLabel(label, linha);
    // pegue somente a instrução dessa linha
    if ( res > 0 ) strcpy(linha, linha + strlen(label) + 2);
    
    // Converte a (instrução de uma) linha para ByteCode
  	c2bc(bytecode, linha); 
    
    /* Se a instrução da linha é um Jump, busque a label
    desse jump */
    char *labelJump = checkJump(linha);
    if ( labelJump != "NULL" ) {
        /* busque o Jump do array através da label 
        a linha encontrada na primeira passada */
        Jump *jmp = busca_Jump(jumps, labelJump);
        // Sete o bytecode operando desse Jump c/ essa linha
        bytecode->operando = jmp->linha;
    }
    
    printf("\t \t --> %d %d\n", bytecode->instrucao, bytecode->operando);
    
    // Escreve na memoria o bytecode
    write_chunk(memoria, *bytecode);
    
    // Libera o bytecode
    free_ByteCode(bytecode);
  }
  // Libera o array de Jumps
  //free_Jumps(jumps);
  
  return memoria;
}

// Funções Helper

/* Função helper para manipular arquivo.
    Retorna 1 linha do arquivo de entrada.
 */
char* readline(FILE *filename) 
{
   char *string = (char *) malloc(MAX_INSTRUCTION_LEN * sizeof (char));
   fscanf(filename, " %[^\n]", string);   
   return string;   
}

/* Função helper que varre o programa e salva num array de Jump 
  todas as linhas encontradas para uma label. */
void find_LineToLabel(FILE *file, Jumps *jumps) {
    // Linha atual do assembler
    int linha_atual = 1;
    
    while ( !feof(file) ) {
        // Lê a linha da entrada
        char *linha = readline(file); //printf("%s \n", linha); 
        
        // Se a instrução da linha é um Jump, save the Jump Label
        char *labelJump = checkJump(linha);
        if ( labelJump != "NULL" ) {
            Jump jmp; strcpy(jmp.label, labelJump);
            insere_Jump(jumps, jmp);
        }
        
        // Se a instrução da linha começa com label
        char label[MAX_LABEL_LEN]; 
        int res = checkLabel(linha,label);
        if ( res > 0 ) {
            // puts(label);
            Jump *jmp = busca_Jump(jumps, label);
            jmp->linha = linha_atual;
        }
        // Incrementa a linha atual do assembler
        linha_atual++;
    }
}

/* Função helper que verifica se a instrução é um jump
    Se sim retorna a Label referente ao jump. Se não, retorna "NULL"
 */
char* checkJump(char *instrucao) 
{
    if ( strncmp(instrucao, "JUMP_TRUE", 9) == 0 ) 
        return strtok(instrucao + 9, " ");
    else if ( strncmp(instrucao, "JUMP_FALSE", 10) == 0) 
        return strtok(instrucao + 10, " ");
    else if ( strncmp(instrucao, "JUMP", 4) == 0 ) 
        return strtok(instrucao + 4, " ");
    return "NULL";
}

/* Função helper que verifica se uma linha começa com label.
 Se sim, escreve a label do começo na linha, no endereço de 
 entrada e retorna 1. Se não, retorna -1
 */
int checkLabel(char *linha, char *label) 
{
    /* Uma label é encontrada, se o primeiro caracter é 'L'
    e o segundo caracter não é um 'O', 'T' ou 'E',
    eliminando as possibilidades de conflito com as 
    instruções LOAD, LT, LE, LEQ. 
        Then save the label line */
    if (linha[0] == 'L' && !( linha[1] == 'O' || 
        linha[1] == 'T'|| linha[1] == 'E') ) {
            int i = 0;
            while ( linha[i] != ':' ) {
                label[i] = linha[i]; i++;
            }
            label[i] = '\0'; 
            return 1;
    }
        return -1;
 }




