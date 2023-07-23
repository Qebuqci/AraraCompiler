package AST;
public class Cmd_Loop implements Cmd
{
  private String tipo_condicional;
  private Exp expressao;
  private Bloco bloco_comandos;
  
  public Cmd_Loop(String tipo, Exp e, Bloco b)
  {
  	tipo_condicional = tipo;
    expressao = e;
    bloco_comandos = b;
  }
  
  // Representação em String
  
  public String toString() 
  {
    return "Loop : " + tipo_condicional + expressao.toString() +
            bloco_comandos.toString(); 
  }
  
  // Getters
  public String getTipoCond() { return tipo_condicional; }
  public Exp getExp() { return expressao; }
  public Bloco getBloco() { return bloco_comandos; }
  

}
