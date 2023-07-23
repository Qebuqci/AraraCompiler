import java.util.ArrayList; 
import AST.*;

public class Parser
{
  /* @Atributos */
  
  private final Lexer lexer;
  private Token current_token;
  
  // Tipos Constantes de Token
  private final String TOKNUM = "TokNum";
  private final String TOKOP = "TokOp";
  
  private final String TOKPAR = "TokParenteses";
  private final String TOKCHAVES = "TokChaves";
  private final String TOKPONTUACAO = "TokPontuacao";
  
  private final String TOKVAR = "TokVar";
  private final String TOKNOME = "TokNome";
  
  private final String TOKPRINT = "TokPrint";
	
  private final String TOKIF = "TokIf";
  private final String TOKELIF = "TokElif";
  private final String TOKELSE = "TokElse";
  
  private final String TOKWHILE = "TokWhile";
  
  private final String TOKFUNDEF = "TokFundef";
  
  private final String TOKEOF = "TokEOF";
    
  /* @Métodos */
  
  public Parser()
  {
    // Estado inicial
    lexer = new Lexer();
    current_token = lexer.next_token();    
  }
  
  /* Atualiza o estado interno do Parser, 
   * capturando um novo token */
  private void atualiza()
  {
    if ( !ver(TOKEOF) )
    	current_token = lexer.next_token();   	
  }
  
  /* Método auxiliar para verificar se
   * o current_token têm o tipo de Token com 
   * o mesmo formato em String do tipo de 
   * token dado como argumento.
   
   * @Input: String Tipo de token
   * @Output: true or false 
   * */
  private Boolean ver(String tagToken) 
  {
    return current_token.getTipo().toString() == tagToken;
  }
  
  // Variável inicial da Gramática
  public Programa Programa()
  {
    ArrayList<Func> funcoes = Funcs();
   	return new Programa(funcoes);  
  }
  
  // Parser para funcs: Uma lista de funções
  public ArrayList<Func> Funcs() 
  {
    ArrayList<Func> funcoes = new ArrayList<>();
    Func f = FunDef();
    funcoes.add(f);
    atualiza();
    
    while ( ver(TOKFUNDEF) ) {
      Func oneMoref = FunDef();
      funcoes.add(oneMoref);
      atualiza();
    }
    
    return funcoes;
  }
  
  // Parser para Args: uma lista de argumentos 
 public ArrayList<String> Args() {
    
    // um ou mais argumentos
    if ( ver(TOKNOME) ) {
      ArrayList<String> args= new ArrayList<String>();
      String nome = current_token.getValor();
      args.add(nome);
      atualiza();
      while( current_token.getValor() == "," && ver( TOKPONTUACAO) ) {
        atualiza();
        nome = current_token.getValor();
        args.add(nome);
        atualiza(); 
      }
    	return args;
    } 
    // caso sem argumento - caso follow(Args)
    else if( ver(TOKPAR) && current_token.getValor() == ")" ) 
      return null;
  }   
  
  // Parser para Exps: uma lista de expressões 
  public ArrayList<Exp> Exps() {
    // caso com um ou mais expressões
     if ( ver(TOKNUM) || ver(TOKPAR) && current_token.getValor() == "(" || ver(TOKNOME)) {
       
       ArrayList<Exp> exps = new ArrayList<Exp>();
       Exp e1 = Exp();
       exps.add(e1);
       atualiza();
              
       while (current_token.getValor() == "," && ver(TOKPONTUACAO)) {
         atualiza();
         Exp e2 = Exp();
         exps.add(e2);
         atualiza();      
       } 
       return exps;        
     }
  
    // Sem expressão na chamada de uma função - caso follow de Exps
    else if( ver(TOKPAR) && current_token.getValor() == ")") 
      return null;
  }
       
  // Parser para cmds: Uma lista de comandos
  public ArrayList<Cmd> Cmds()
  {
  	ArrayList<Cmd> comandos = new ArrayList<>();
    Cmd comando = Cmd();
    comandos.add(comando);
    atualiza();
    while ( ver(TOKPRINT) || ver (TOKVAR) || ver(TOKNOME) 
           || ver(TOKWHILE) || ver(TOKIF) ) {
      Cmd oneMoreComando = Cmd();
    	comandos.add(oneMoreComando);
    }
    return comandos; 
  }
  
  /* Descendente recursivo para fundef */
  public Func FunDef(){
   
   if ( ver(TOKFUNDEF) ) {
     atualiza();
     Func func;
     String nome_func = current_token.getValor();
     atualiza();
     if ( ver(TOKPAR) && current_token.getValor() == "(" ) {
       atualiza();
       ArrayList<String> args = Args();
       atualiza();
       Bloco b = Bloco();
       func = new Func(nome_func, args, b);
     }
     return func;  
   }
   
   // Retorna erro sintatico
   //else return null;
 }
    
  /* Descendente Recursivo para Bloco */
  public Bloco Bloco() 
  {
    if ( ver (TOKCHAVES) ) {
     	atualiza();
      ArrayList<Cmd> comandos = Cmds();
      atualiza();
      if ( ver(TOKCHAVES) && current_token.getValor() == "}" )
    		return new Bloco(comandos);
    }
  }
  
  /* Descendente Recursivo */
  public Cmd Cmd()
  {    
    // TokPRINT
    if ( ver(TOKPRINT) ) {
      atualiza();
      Exp e = Exp();
      if ( ver(TOKPONTUACAO) && current_token.getValor() == ";") 
      	return new Cmd_Print(e);
    }
    
    // TokIf  
    else if ( ver(TOKIF) ) {
     	atualiza();
      Exp e = Exp();
      Bloco b = Bloco();
      Cmd_Cond elses = Elses();
      return new Cmd_Cond("If", e, b, elses);
    }
    
    // TokWHILE
    else if ( ver(TOKWHILE) ) {
      atualiza();
      Exp e = Exp();
      Bloco b = Bloco();
      return new Cmd_Loop("While", e, b);
    }
  
     // TokVAR = Token inicio de variável - Declaração
    else if ( ver(TOKVAR) ) {
      atualiza();
      String nome = current_token.getValor();
      atualiza();
      if ( ver(TOKOP) && current_token.getValor() == "=" ) {
        atualiza();
        Exp e =  Exp();
        if ( ver(TOKPONTUACAO) && current_token.getValor() == ";") 
        	return new Cmd_Assign(nome, e, true);
      }
    }    
    
    // TokNOME
    else if ( ver(TOKNOME) ) {
      String nome = current_token.getValor();
      atualiza();
      
      // Caso: Atribuição
      if ( ver(TOKOP) && current_token.getValor() == "=" ) {
        atualiza();
        Exp e =  Exp();
        if ( ver(TOKPONTUACAO) && current_token.getValor() == ";") 
        	return new Cmd_Assign(nome, e, false);
      }
      
      // Caso: Chamada de função
      else if ( ver(TOKPAR) && current_token.getValor() == "(" ) {
        FuncCall fcall = FunCall();
        if ( ver(TOKPONTUACAO) && current_token.getValor() == ";") 
           return fcall;
      }      
    }
  	
    // Lançar erro sintático
    else {
      return null; 
    }
    
  }
  
  /* Descendente Recursivo para Elses */
  public Cmd_Cond Elses()
  {
    // Regra cadeia vazia - follow
    // (...)
    
		// TokElif
    if ( ver(TOKELIF) ) {
     	atualiza();
      Exp e =  Exp();
      Bloco b =  Bloco();
      Cmd_Cond elses = Elses();
      return new Cmd_Cond("Elif", e, b, elses);
    }
      
    // TokElse    
    if ( ver(TOKELSE) ) {
      atualiza();
      Bloco b =  Bloco();
      Cmd_Cond elses = Elses();
      return new Cmd_Cond("Else", b, elses);
    }
  }
     
  /* Descendente recursivo para funcall */
  public FuncCall FunCall() 
  {
    if ( ver(TOKNOME) ) {
      String nome_func = current_token.getValor();
      atualiza();
    	if ( ver(TOKPAR) && current_token.getValor() == "(") {
        atualiza();
        ArrayList<Exp> exps = Exps();
        atualiza();
            if ( ver(TOKPONTUACAO) && current_token.getValor() == ";") 
                return new FuncCall(nome_func, exps); 
        }
    }
  }
  
  /* Expressões */
  public Exp Exp()
  {   
    // Expressão entre parênteses
    if ( ver( TOKPAR ) && current_token.getValor() == "(" ) {
      atualiza();
      Exp e = Exp();
    	if (ver (TOKPAR) && current_token.getValor() == ")")
        return e;
    } 
    
    // TokNOME
  	else if ( ver (TOKNOME) ) {
			String nome = current_token.getValor();
      atualiza();
      
      // Chamada de função
      if ( ver(TOKPAR) && current_token.getValor() == "(") {
      	FuncCall f = FunCall();
        return f;
      }
      /* Caso não tenha retornado para a chamada de função,
      então trate o nome como um valor literal.
      */
      return new Literal ( current_token.getValor() );
    }
    
     // Números, Expressões binárias e unárias 
    if ( ver(TOKNUM) )
      return new Literal( current_token.getValor() );
    
      
    else if ( ver(TOKOP) ) {
    
    }     
    
    // else launch an error
    return null;
  }  
  
  /* Expressões binárias */
  // BINOP -> E6
  public Exp BinOP()
  {
   	return BinOp_Or();	// E6
  }
      
  /* Nível de precedência 6 - OR
   * E6 -> E5 OR E6      ==		E6 -> E5 (OR E5)* 
   */
  public Exp BinOp_Or() 
  {
    Exp expr = BinOp_And(); // E5
    atualiza();    
    while ( current_token.getValor() == "or" ) {
      atualiza();
      Exp operando = BinOp_And(); // E5
      expr = new ExpBin("or", expr, operando);
    }    
    return expr;
  }
  
  /* Nível de precedência 5 - AND
   * E5 -> E4 AND E5     ==		E5 -> E4 (AND E4)* */
  public Exp BinOp_And() 
  {
    Exp expr = BinOP_Comparacao(); // E4
    atualiza();
    while ( current_token.getValor() == "and" ) {
      atualiza();
      Exp operando = BinOP_Comparacao(); // E4
      expr = new ExpBin("and", expr, operando);
    }
    return expr;
  }
  
  /* Nível de precedência 4 - Operadores de Comaparação
   * E4 -> E3 == E4      ==		E4 -> E3 (== E3)*  
   * E4 -> E3 != E4      ==		E4 -> E3 (!= E3)* 
   * E4 -> E3 != E4      ==		E4 -> E3 (!= E3)* 
   * E4 -> E3 >= E4      ==		E4 -> E3 (>= E3)*   
   * 
   * E4 -> E3 > E4      ==			E4 -> E3 (> E3)*   
		 E4 -> E3 < E4      ==			E4 -> E3 (< E3)*   
   * */
  public Exp BinOP_Comparacao()
  {
    Exp expr = BinOp_SumSub(); // E3
    atualiza();
    while ( current_token.getValor() == "==" || current_token.getValor() == "!=" 
		|| current_token.getValor() == ">=" || current_token.getValor() == "<=" || 
          current_token.getValor() == "<" || current_token.getValor() == ">") {
      String operator = current_token.getValor();	// salva o operador
      atualiza();
      Exp operando = BinOp_SumSub(); // E3
      expr = new ExpBin(operator, expr, operando);
    }
    return expr;
  }
  
  /* Nível de precedência 3 - Aritméticos (Soma e Sub)
   * E3 -> E2 + E3		==		E3 -> E2 (+ E2)*
   * 	E3 -> E2 - E3		== 		E3 -> E2 (- E2)*   **/
  public Exp BinOp_SumSub()
  {
		Exp expr = OpBin_MulDiv(); // E2
    atualiza();
    while ( current_token.getValor() == "-" || current_token.getValor() == "+") {
      String operator = current_token.getValor();	// salva o operador
      atualiza();
      Exp operando = OpBin_MulDiv(); // E2
      expr = new ExpBin(operator, expr, operando);
    }
    return expr;
  }  
  
  /*  Nível de precedência 2 - Aritméticos (Mul e Div)
   * E2 -> U1 * E2			==		E2 -> U1 (* U1)*
   * E2 -> U1 / E2   	==		E2 -> U1 (/ U1)*   **/
  public Exp OpBin_MulDiv()
  {
    Exp expr = OpUn_Negacao(); // U1
    atualiza();
    while ( current_token.getValor() == "*" || current_token.getValor() == "/" ) {
      String operador = current_token.getValor();
      atualiza();
      Exp operando = OpUn_Negacao(); // U1
      expr = new ExpBin(operador, expr, operando);
    }
    return expr;
  } 
  
  public Exp OpUn_Negacao()
  {
    // ToDo
    return null;
  }
  
  /* Operador Exponencial associativo a direita
			E1 -> E1 ^ Exp => regra recursiva a esquerda
   * Eliminação da recursão a esquerda
   		E1  -> ^ Exp E1r
			E1r -> Exp
			E1r -> 
   * Aplicação do algoritmo descendente recursivo   * */
  // E1  -> ^ Exp E1r
  public Exp OpBin_Exponenciacao()
  {
    String op = current_token.getValor(); 
    atualiza();
    Exp Op1 = Exp();	
    atualiza();
    Exp Op2 = OpBin_Exponenciacao1(); // E1R
    return new ExpBin(op, Op1, Op2);
  }  
  
  /* Método redundante
   * E1R -> Exp
     E1R -> 
    **/ 
  public Exp OpBin_Exponenciacao1() 
  {
    return Exp();
  }
        
  public static void main(String[] args) 
  {
    Parser p = new Parser();
    while ( p.ver("TokEOF")) {
      System.out.println(p.Programa());
      p.atualiza(); 
    }    
  }
  
}
