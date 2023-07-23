package AST;
import java.util.ArrayList;

public class Bloco 
{
  private ArrayList<Cmd> comandos;
  public Bloco (ArrayList<Cmd> cmds) { comandos = cmds;} 
  
  // Representação em String
  public String toString() 
  {
    String str = "Bloco : { ";
    for (Cmd c : comandos)
        str += c.toString() + '\n';
    str += " }\n";
    return str;   
  }
  
  // Getters
  public ArrayList<Cmd> getComandos() { return comandos; }
  
}
