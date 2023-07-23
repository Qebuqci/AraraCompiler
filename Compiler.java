import AST.*;
import java.util.HashMap;

public class Compiler
{
    private String output;
    private Parser p;
    
    private int countVariables;
    // HashMap para as variáveis
    private HashMap<String, int> variaveis;
    
    private int countFunctions; 
    // HashMap para funções
    private HashMap<String, int> funcoes;
    
    public Compiler ()
    {
        countFunctions = countVariables = 0;
        funcoes = new HashMap<String, int>();
        variaveis = new HashMap<String, int>();
    
    }
    
    // Gera Código para Programa
    public void geraPrograma()
    {
        
    }
    
    
    // Gera Código para Func - fundef
    public void geraFunc(Func f) 
    {
        // Salva no hashtable de funções, seu ID
        funcoes.put(f.getFnome(), countFunctions);
        
        int qargs = f.getQntArgs();
        int qlocalVars = f.qntLocalVars();
        
        output += "FUNCTION " + countFunctions + ' ' + 
            qargs + ' ' + qlocalVars + '\n';

        countFunctions += 1;
        
        // Code generation para cada comando da função
        for (Cmd cmd : f.getBloco().getComandos() )
            geraCmd(cmd);
    }
    
    
    // Gera Código para Cmd
    public void geraCmd(Cmd cmd) 
    {
        // Traduz loop
        if (cmd instanceof Cmd_Loop) {
        
        }
    
        // Traduz condicional
        
        // Traduz print
        
        // Traduz assignment
        
        // Traduz function call
    
    
    
    
    }
    
    
    // Gera Código para Exp
    public void geraExp(Exp e) 
    {
        // Traduz Literal
        if (e instanceof Literal) {
            // Traduz Número
            if ( (Literal) e.isNumero() )
                output += "NUMBER " + e.numero + '\n';
            // Nome
            else
                // 
        }
        // Traduz Expressão unária
        else if (e instanceof ExpUn) {
            geraExp( (ExpUn) e.getExp() );                   
            switch( (ExpUn) e.getOp() ) {
                case "-":
                    output += "NEG\n";
                    break;
                case "not":
                    output += "NOT\n";
                    break;
            }
        }
        // Traduz Expressão Binária
        else if (e instanceof ExpBin ) {
             geraExp( (ExpBin) e.getExp1() );  
             geraExp( (ExpUn) e.getExp2() );  
             switch( (ExpBin) e.getOp() ) {
             // Operadores Aritméticos
                case "+":
                    output += "ADD\n";
                    break;
                case "-":
                    output += "SUB\n";
                    break;
                case "*":
                    output += "MUL\n";
                    break;
                case "/":
                    output += "DIV\n";
                    break;
                case "^":
                    output += "POW\n";
                    break;
                // Operadores de Comparação
                case "<":
                    output += "LE\n";
                    break;
                case ">":
                    output += "GE\n";
                    break;
                case "<=":
                    output += "LEQ\n";
                    break;
                case ">=":
                    output += "GEQ\n";
                    break;
                case "==":
                    output += "EQ\n";
                    break;
                case "!=":
                    output += "NEQ\n";
                    break;
                // Operadores Booleanos
                case "or":
                    output += "OR\n";
                    break;
                 case "and":
                    output += "AND\n";
                    break;
            }
        }
        /* Traduz a chamada de funções que ocorre como
        expressão dentro de outra chamada */
        else if (e instanceof FuncCall) {
            for (Exp exp : (FuncCall) e.getExps() )
                geraExp(exp);
                
            int functionID = funcoes.get((FuncCall) e.getFnome() );
            output += "CALL " + functionID + '\n';            
        }    
    }

 
    
    public static void main(String[] args)  
    {
    
    
    }

}
