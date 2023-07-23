package AST;
public class Cmd_Cond implements Cmd
{
    private final String tipo_cond;
    private Exp exp;
    private Bloco blcmds;
    private Cmd_Cond Elses;
    
    // Sobrecarga de construtor quando o tipo_cond == ELSE.
    // não tem expressão como parâmetro
    public Cmd_Cond(String tipo, Bloco b, Cmd_Cond elses)
    {
        tipo_cond = tipo;
        blcmds = b;
        Elses = elses;
    }
    
    // Caso o condicional seja IF ou ELIF
    public Cmd_Cond(String tipo_cnd, Exp e, Bloco b, Cmd_Cond elses) 
    {
        tipo_cond = tipo_cnd;
        exp = e;
        blcmds = b;
        Elses = elses;    
    }
    

    // Representação em String
   /*public String toString() 
    {
        if (Elses)
            return "Condicional : " + tipo_cnd + '(' + 
            e.toString() + ')' + b.toString() + '\n' +
            Elses.toString();
        
        return "Condicional : " + tipo_cnd + '(' + 
            e.toString() + ')' + b.toString();
    }
*/
}
