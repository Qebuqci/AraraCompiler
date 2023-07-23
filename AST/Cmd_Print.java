package AST;
public class Cmd_Print implements Cmd{
  private Exp expressao;
  
  public Cmd_Print(Exp expressao){
    this.expressao = expressao;
  }
  
  // Representação em String
  
  public String toString() 
  {
    return "print " + expressao.toString();
  }

}
