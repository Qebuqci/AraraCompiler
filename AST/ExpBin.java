package AST;
public class ExpBin implements Exp
{
	private final String operador;
	private final Exp e1, e2;
	
    public ExpBin(String op, Exp _e1, Exp _e2) 
    {
      operador = op;
       e1 = _e1;
       e2 = _e2;    
    } 
    
    // Define a representação em String de uma ExpBin
    public String toString()
    {
    	return e1.toString() + operador + e2.toString();
    }
    
    // Getters
    public String getOp() { return operador;}
	public Exp getExp1() { return e1;}
    public Exp getExp2() { return e2;}
    
  }
