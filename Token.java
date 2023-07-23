enum Tok 
{
    // Lista dos tipos de Tokens
    NUM, 
    OP,
  
    PARENTESES,
    CHAVES,
    PONTUACAO,
    
    VAR,
    NOME,
    PRINT,
    
    IF,
    ELIF,
    ELSE,
  
    WHILE,    
  
    FUNDEF,
  
  	EOF; 
  	
  	// Representação em String de um tokenTag
  	public String toString()
    {
      switch(this) {
        case NUM:
          return "TokNum";
        case OP:
          return "TokOp";
        case PARENTESES:
          return "TokParenteses";
        case CHAVES:
          return "TokChaves";
        case PONTUACAO:
          return "TokPontuacao";
        case VAR:
          return "TokVar";
        case NOME:
          return "TokNome";
        case PRINT:
          return "TokPrint";
        case IF:
          return "TokIf";
        case ELIF:
          return "TokElif";
        case ELSE:
          return "TokElse";
        case WHILE:
          return "TokWhile";
        case FUNDEF:
          return "TokFundef";
        case EOF:
          return "TokEOF";
        default:
          return null;
      }
    }  
}

public class Token
{
  private final Tok rotulo;
  private final String valor;
  private final int linha, coluna;
  
  /* Construtor
   * Cada token é uma 4-upla com os componentes:
   * token = (rotulo, valor, linha, coluna)
   * */
  Token (Tok rotulo, String val, int lin, int col) 
  {
    this.rotulo = rotulo;
    this.valor = val;
    this.linha = lin;
    this.coluna = col;   
  }  
  
  // Get the Token Tipo
  public Tok getTipo()
  {
  	return rotulo;    
  }
  
  // Get the Token Valor
  public String getValor()
  {
    return valor;
  }
    
  // Get the Token Linha
  public int getLinha() 
  {
    return linha;
  }
  
  // Get the Token Coluna
  public int getColuna() 
  {
    return coluna;
  }
  
  // Representação em String de um TokenObject
  public String toString()
  {
    return '(' + rotulo.toString() + ", " + valor + ", " + linha + ", " + coluna + ')';    
  }
  
  // Test function
  public static void main(String[] args)
  {
    Token soma = new Token(Tok.OP, "+", 0, 0);
    System.out.println(soma);    
  }
  
}
