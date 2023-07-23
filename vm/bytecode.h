#ifndef _BYTECODE_H
#define _BYTECODE_H

typedef unsigned int Byte;

// Estrutura abstrata para OpCode
typedef enum {
  OP_EXIT,
  OP_NUMBER,
  OP_ADD, OP_SUB, OP_MUL, OP_DIV, OP_POW,
  OP_NOT, OP_NEG, 
  OP_LT, OP_GT, // adicionadas pela dupla
  OP_EQ, OP_NEQ, OP_LE, OP_LEQ, OP_GE, OP_GEQ,
  OP_LOAD, OP_STORE,
  OP_POP, OP_DUP,
  OP_PRINT,
  OP_JUMP, OP_JUMPTRUE, OP_JUMPFALSE,
  OP_CALL, OP_RETURN
} OpCode;

// Estrutura abstrata para ByteCode
typedef struct {
  OpCode instrucao;
  Byte operando;  
} ByteCode;

/* Funções que manipulam a estrutura de ByteCode 
 * + Inicializa um byteCode
 * + Converte uma linha de instrução em ByteCode 
 * + Libera um ByteCode */
ByteCode* init_ByteCode();
void c2bc(ByteCode *bytecode, char linhaInstrucao[]);
void free_ByteCode(ByteCode *bytecode);

#endif
