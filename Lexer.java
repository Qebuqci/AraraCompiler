import java.util.Scanner;

public class Lexer 
{
  
  /* @Atributos: */
  
  // manter o rastro da entrada
  private String conteudo_fonte;
  private int tam_conteudo_fonte;
  private int pos_absoluta;
        
  // linha e coluna para mensagens de erro
  private int linha;
  private int coluna;

  /* acumula os símbolos da entrada até encontrar
   o maior léxico de uma categoria (token). 
   */
  private String mlexico_reconhecido;
  
  // caracteres sentinelas da entrada
  private char current_symbol;
  private char next_symbol;
  
  // expressões regulares 
  private final String STRING = "[a-zA-Z_]*";
    
  private final String HEX = "[0-9a-fA-F]";
  private final String DEC = "[0-9]";
  private final String NUM = HEX + '|' + DEC;

  private final String OP = "[ + | \\- | − | * | / | ^ | % | < | > | =]";
 
  private final String SPACE = "[\\n | \\t | \\s]";
  
  private final char NULL = '\0';
    
   /* @Métodos: */
  
  /* Construtor
   * Inicializa o estado interno do Lexer:
   * 		pos_absoluta, linha e coluna, 
   * 	  conteudo_fonte e seu tamanho,
   *    sentinelas: current_symbol, next_symbol
   * 	  e acumulador do maior lexico reconhecido
  */
  public Lexer()
  {
    // Configuração inicial
    pos_absoluta = 0; 
    linha = coluna = 1;
    
    /* Carrega o conteúdo da entrada padrão 
     * para conteudo_fonte */
    Scanner scan = new Scanner(System.in);
    conteudo_fonte = "";
    while ( scan.hasNextLine() ) 
      conteudo_fonte += scan.nextLine() + '\n';
    tam_conteudo_fonte = conteudo_fonte.length();
    
    // Lê os primeiros símbolos da entrada
    current_symbol = conteudo_fonte.charAt(pos_absoluta);
    next_symbol = conteudo_fonte.charAt(pos_absoluta + 1);
    
    // Inicializa o acumulador de símbolos da entrada
    mlexico_reconhecido = "";
  }
  
  /* Atualiza a configuração do Lexer (automato) */
  private void atualiza()
  {       
    // Para quebra de linha -> set linha e reset coluna
    if (current_symbol == '\n') {
    	linha += 1; coluna = 0;
    }  
    
    // Para as duas últimas posições do arquivo fonte
    if (pos_absoluta == tam_conteudo_fonte - 2) {
      pos_absoluta += 1;
      current_symbol = conteudo_fonte.charAt(pos_absoluta);
      next_symbol = NULL;
      
      // reset pos_absoluta e coluna
      pos_absoluta = coluna = 0;
    }
    // Para as outras posições com exceção das duas últimas,
    else {
      pos_absoluta += 1;
    	coluna += 1;
      current_symbol = conteudo_fonte.charAt(pos_absoluta);
      next_symbol = conteudo_fonte.charAt(pos_absoluta + 1);
    }
  }
  /* Lê símbolo a símbolo na entrada, acumulando o 
   * maior léxico reconhecido e designa um token
   * (categoria) para ele, se possível. Se não
   * for possível, retorna erro.
   */
  public Token next_token()
  {    
    while ( next_symbol != NULL ) {  
      mlexico_reconhecido = "";
      
      /* Tratamento de espaço em branco, tab e quebra de linha */
      if ( ehReconhecido(current_symbol, SPACE) ) 
      	atualiza();
      
      /* Tratamento de comentários */
      else if (current_symbol == '/') {
        
        // Tratamento do comentário de bloco
        if (next_symbol == '*') {
          
          /* loop interno que itera até encontrar o simbolo 
           * que fecha o comentário de bloco */
          while ( !(current_symbol == '*' && next_symbol == '/') )
            atualiza(); 
          
          // após encontrado, atualiza 2x o estado interno do Lexer
          atualiza(); atualiza();
        }
        
        // Tratamento do comentário de linha
        else if (next_symbol == '/') {
          while ( current_symbol != '\n') 
            atualiza();
        }
        
        /* Se não for comentário de bloco, nem de linha, 
        então (TokOP, divisão) */
        else {
          atualiza();
          return new Token(Tok.OP, "/", linha, coluna - 1);     
        }
      }
      
      /* Reconhecimento de números hexadecimal */
      else if ( current_symbol == '0' && ehReconhecido(next_symbol, "[xX]" ) ) {
        
        // Acumula os símbolos 0x ou 0X da entrada e dupla atualização
        mlexico_reconhecido += current_symbol; mlexico_reconhecido += next_symbol;
        atualiza(); atualiza();
      	
        // continua acumulando e atualizando
        while ( ehReconhecido(current_symbol, HEX) ) {
          mlexico_reconhecido += current_symbol; atualiza();
        }
      
        // loop chegou ao fim, retorne o token com o número hexa acumulado
        return new Token(Tok.NUM, mlexico_reconhecido, linha, coluna - 1);
      }
      
      /* Reconhecimento de números em decimal */
      else if ( ehReconhecido(current_symbol, DEC) ) {
       
        // acumula os decimais e atualiza
       while ( ehReconhecido(current_symbol, DEC ) ) {
         mlexico_reconhecido += current_symbol;  atualiza();
       }
    	 // loop no fim, retorne o token com o número decimal acumulado
       return new Token(Tok.NUM, mlexico_reconhecido, linha, coluna - 1);
      }
      
      /* Reconhecimento de operadores com 1 símbolo */
      else if ( ehReconhecido(current_symbol, OP) ) {
        Token token = new Token(Tok.OP, String.valueOf(current_symbol), linha, coluna);
        atualiza();
        return token;
      }
      
      /* @Reconhecimento de operadores com 2 símbolos ou mais símbolos */
      /* Operadores de comparação */
      
      // Comparação de igualdade: ==
      else if ( current_symbol == '=' && next_symbol == '=' ) {
      	mlexico_reconhecido += "==";
        atualiza(); atualiza();
        Token token = new Token(Tok.OP, mlexico_reconhecido, linha, coluna);
        atualiza();
        return token;
      }
      
      // Operador diferente: !=
    	else if ( current_symbol == '!' && next_symbol == '=' ) {
        mlexico_reconhecido += "!=";
        atualiza(); atualiza();
        Token token = new Token(Tok.OP, mlexico_reconhecido, linha, coluna);
        atualiza();
        return token;
      }
      
      // Operador menor ou igual: <=
      else if ( current_symbol == '<' && next_symbol == '=' ) {
        mlexico_reconhecido += "<=";
        atualiza(); atualiza();
        Token token = new Token(Tok.OP, mlexico_reconhecido, linha, coluna);
        atualiza();
        return token;
      }
      
      // Operador maior ou igual: >=
      else if ( current_symbol == '>' && next_symbol == '=' ) {
        mlexico_reconhecido += ">=";
        atualiza(); atualiza();
        Token token = new Token(Tok.OP, mlexico_reconhecido, linha, coluna);
        atualiza();
        return token;
      }
      
      /* Operadores booleanos */
      // or
      else if ( current_symbol == 'o' && next_symbol == 'r' ) {
        mlexico_reconhecido += "or";
        atualiza(); atualiza();
        Token token = new Token(Tok.OP, mlexico_reconhecido, linha, coluna);
        atualiza();
        return token;
      }
      
      // and
      else if ( current_symbol == 'a' && next_symbol == 'n' ) {
        mlexico_reconhecido += "an";
        atualiza(); atualiza();
        if (current_symbol == 'd') {
          mlexico_reconhecido += 'd';
       		Token token = new Token(Tok.OP, mlexico_reconhecido, linha, coluna);
        	atualiza();
        	return token;
        }
      }
      
      // not
      else if ( current_symbol == 'n' && next_symbol == 'o' ) {
        mlexico_reconhecido += "no";
        atualiza(); atualiza();
        if (current_symbol == 't') {
          mlexico_reconhecido += 't';
       		Token token = new Token(Tok.OP, mlexico_reconhecido, linha, coluna);
        	atualiza();
        	return token;
        }
      }
            
      /* Reconhecimento de parenteses */ 
      else if (current_symbol == '(' || current_symbol == ')') {
        Token token = new Token(Tok.PARENTESES, String.valueOf(current_symbol), linha, coluna);
        atualiza();
        return token;
      }
      
      /* Reconhecimento de chaves */
      else if (current_symbol == '{' || current_symbol == '}') {
        Token token = new Token(Tok.CHAVES, String.valueOf(current_symbol), linha, coluna);
        atualiza();
        return token;
      }
      
      /* Reconhecimento de ponto e virgula */
      else if (current_symbol == ';') {
        Token token = new Token(Tok.PONTUACAO, String.valueOf(current_symbol), linha, coluna);
        atualiza();
        return token;    
      }
      
      /* Reconhecimento de virgula */
      else if (current_symbol == ',') {
        Token token = new Token(Tok.PONTUACAO, String.valueOf(current_symbol), linha, coluna);
        atualiza();
        return token;    
      }
                    
      /* Reconhecimento de início de variável */
      else if (current_symbol == 'v' && next_symbol == 'a') {
        mlexico_reconhecido += "va";
        atualiza(); atualiza();
        
        if (current_symbol == 'r') {
          mlexico_reconhecido += 'r';
          Token tok = new Token(Tok.VAR, mlexico_reconhecido, linha, coluna);
          atualiza();
        	return tok;
        }
      } 
            
      /* Reconhecimento de print */
      else if (current_symbol == 'p' && next_symbol == 'r') {
          mlexico_reconhecido += "pr";
        	atualiza(); atualiza();
         
        	if (current_symbol == 'i' && next_symbol == 'n') {
            mlexico_reconhecido += "in";
            atualiza(); atualiza();
            if (current_symbol == 't') {
            	mlexico_reconhecido += "t";
              Token tok = new Token(Tok.PRINT, mlexico_reconhecido, linha, coluna);
              atualiza();
              return tok;
            }
          }		
      }
      
      /* Reconhecimento de if */
      else if (current_symbol == 'i' && next_symbol == 'f') {
        mlexico_reconhecido += "if";
        atualiza(); atualiza();
        Token tok = new Token(Tok.IF, mlexico_reconhecido, linha, coluna);
        atualiza();
        return tok;  
      }
          
      
      /* Reconhecimento de elif e else */
      else if (current_symbol == 'e' && next_symbol == 'l') {
        atualiza(); atualiza();
        mlexico_reconhecido = "el";
        if (current_symbol == 'i' && next_symbol == 'f' ) {
            mlexico_reconhecido += "if";
            Token tok = new Token(Tok.ELIF, mlexico_reconhecido, linha, coluna);
            atualiza(); atualiza();
            return tok;
          }
          
        else if (current_symbol == 's' && next_symbol == 'e' ) {
            mlexico_reconhecido += "se";
            Token tok = new Token(Tok.ELSE, mlexico_reconhecido, linha, coluna);
            atualiza();  atualiza();
            return tok;
          }
        }
      
      /* Reconhecimento de while */
      else if (current_symbol == 'w' && next_symbol == 'h') {
          mlexico_reconhecido += "wh";
        	atualiza(); atualiza();
         
        	if (current_symbol == 'i' && next_symbol == 'l') {
            mlexico_reconhecido += "il";
            atualiza(); atualiza();
            if (current_symbol == 'e') {
            	mlexico_reconhecido += "e";
              Token tok = new Token(Tok.WHILE, mlexico_reconhecido, linha, coluna);
              atualiza();
              return tok;
            }
          }		
      }
      
      /* Reconhecimento de definição de função */
      else if (current_symbol == 'f' && next_symbol == 'u') {
          mlexico_reconhecido += "fu";
        	atualiza(); atualiza();
         
        	if (current_symbol == 'n') {
            mlexico_reconhecido += "n";
            atualiza(); 
            Token tok = new Token(Tok.FUNDEF, mlexico_reconhecido, linha, coluna);
            atualiza();
            return tok;
          }
      	}		    
        
      /* Reconhecimento de nome de variável */
      else if ( ehReconhecido(current_symbol, STRING) ) {
       // acumula os caracteres e atualiza
       while ( ehReconhecido(current_symbol, STRING ) ) {
         mlexico_reconhecido += current_symbol;  atualiza();
       }
    	 // loop no fim, retorne o token com o número decimal acumulado
       return new Token(Tok.NOME, mlexico_reconhecido, linha, coluna - 1);
      }
                  
      /* Se o símbolo na entrada não foi tratado -> não reconhecido. Logo,
       * lance um erro 
       * 
       * ToDo usando a função mensagemErro
       * 
       * */
      
    }  
    
    // Quando next_symbol == NULL, então
    return new Token(Tok.EOF, "TokEOF", linha, tam_conteudo_fonte);
  }
  
  /* Método auxiliar para verificar se um símbolo é 
   * reconhecido por uma expressão regular.
   
   * @Input: Um símbolo e uma regex
   * @Output: Caso sim, então retorna true. 
   * 		      Caso não, retorna false
   * */
  private Boolean ehReconhecido(char symbol, String regex) 
  {
    String ssymbol = String.valueOf(symbol);
    return ssymbol.matches(regex);
  }
  
  /* Método auxiliar para formatar mensagens de erro 
   
   * @Input: 
   * */
  private void mensagemErro(int lin, int col)
  {
    // ToDo  
  } 
  
  public static void main(String[] args) 
  {
    Lexer lexer = new Lexer();
    Token token = lexer.next_token();
    while (token.getTipo() != Tok.EOF ) {
      System.out.println(token);
      token = lexer.next_token();
    }
    System.out.println(token);
          
  }
    
}
