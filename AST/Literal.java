package AST;

/* Classe que armazena um literal, que
pode ser tanto um número quanto um nome */

public class Literal implements Exp
{
 private final int numero;
 private final String nome;
 private final Boolean isNumero;
  
  // Sobrecarga de construtor
  
  // Literal Numérico
  public Literal(int v)
  {
  	numero = v;
  	isNumero = true;
  }
  
  // Literal String
  public Literal(String n) 
  {
    nome = n;
    isNumero = false;
  }

  public Boolean isNumero() { return isNumero;} 
  
  
 // Representação em String
 public String toString() 
 {
    if (isNumero) 
        return "Literal : " + numero;
    return "Literal : " + nome; 
 }
  
  
}
