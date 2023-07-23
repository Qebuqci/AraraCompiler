package AST;
import java.util.ArrayList; 

public class Func 
{
    private final String nome_funcao;
    private ArrayList<String> args;
    private final Bloco bloco;
    private final int qargs;
    
    public Func( String nomef, ArrayList<String> params, Bloco b) 
    {
        nome_funcao = nomef;
        args = params;
        Bloco bloco = b;
        qargs = params.size();
    }

    // Representação em String [OLHAR]
    public String toString() 
    {
        String str = "Função : " + nome_funcao + '('; 
        for (int i = 0; i < args.size() - 1; i++) 
            str += args.get(i) + ", ";
        str += args.get(-1) + ")\n";
        return str;        
    }   
    
    // Getters
    public getFnome() { return nome_funcao; } 
    public ArrayList<String> getArgs() { return args ; }
    public getQntArgs() { return qargs; }
    public getBloco() { return bloco; }
   
    /* Procedimento helper que informa a quantidade de 
    local vars em um bloco de comandos de uma função
    */
    public int qntLocalVars() 
    {
        int qntlv = 0;
        for (Cmd cmd : bloco.getComandos() ) {
            if (cmd instanceof Cmd_Assign) 
                if ((Cmd_Assign) cmd.isDeclaration() ) 
                    qntlv += 1; 
        }
        return qntlv;
    }
    
}
