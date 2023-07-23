package AST;
public class ExpUn extends Exp 
{
	private final String operador;
	private final Exp e;
	public ExpUn (String op, Exp exp) { 	operador_unario = op; e = exp; }
	
	// Representação em String
	
	public String toString() 
	{
        return "Expressão Unária : " + operador + e.toString();
    }
	
	// Getters
	public String getOp() { return operador;}
	public Exp getExp() { return e;}
	
}
