package AST;
public class Cmd_Assign implements Cmd{
  
  private String nome;
  private Exp expressao;
  /* tells if this cmd is a declaration of a new variable 
  or an assignment of an existing one */
  private Boolean isDeclaration; 
  
  public Cmd_Assign(String nome, Exp expressao, Boolean isDecl)
  {
    this.nome = nome;
    this.expressao = expressao;
    this.isDeclaration = isDecl;
  }
  
  // Representação em String
  
  public String toString() 
  {
    return "Assign : " + nome + " = " + expressao.toString();    
  }
  
  // Getters 
  public Boolean isDeclaration() { return isDeclaration; }

}
