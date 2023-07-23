package AST;
import java.util.ArrayList; 

public class FuncCall implements Exp, Cmd
{
    private String nome_funcao;
    private ArrayList<Exp> exps;
    
    public FuncCall(String nomef, ArrayList<Exp> expressoes) 
    {
        nome_funcao = nomef;
        exps = expressoes;    
    }
    
    // Representação em String
    public String toString()
    {
        String str = "Função : " + nome_funcao + '('; 
        for (int i = 0; i < exps.size() - 1; i++) 
            str += exps.get(i).toString() + ", ";
        str += exps.get(-1).toString() + ")\n";
        return str;       
    }

    // Getters
    public String getFnome() { return nome_funcao; }
    public ArrayList<Exp> getExps() { return exps; }
    
}
