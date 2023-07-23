package AST;
import java.util.ArrayList; 

public class Programa 
{
    private ArrayList<Func> funcoes;
    
    public Programa (ArrayList<Func> funcs) 
    {
        funcoes = funcs;    
    }
    
    // Representação em String
    
    public String toString()
    {
        String str = "Programa : ";
        for (Func f : funcoes)
            str += funcoes.toString();
        return str;
    }
    

}
