%%

/* Não altere as configurações a seguir */

%line
%column
%unicode
//%debug
%public
%standalone
%class Minijava
%eofclose

/* Insira as regras léxicas abaixo */


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace =  {LineTerminator} | [ \t\f]


 Comment = {TraditionalComment} | {EndOfLineComment} 
 TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
 EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?

letra           = [A-Za-z] | "_"
digito          = [0-9]
inteiro         = {digito}+
alfanumerico    = {letra}|{digito}
identificador      = {letra}({alfanumerico})*
inteiro = [1-9] ([0-9])*  | 0

%%

  boolean   {System.out.println("Palavra reservada boolean");}
  class     {System.out.println("Palavra reservada class");}
  public	{System.out.println("Palavra reservada public");}
  extends	{System.out.println("Palavra reservada extends");}
  static	{System.out.println("Palavra reservada static");}
  void		{System.out.println("Palavra reservada void");}
  main		{System.out.println("Palavra reservada main");}
  String	{System.out.println("Palavra reservada String");}
  int		{System.out.println("Palavra reservada int");}
  while		{System.out.println("Palavra reservada while");}
  if		{System.out.println("Palavra reservada if");}
  else		{System.out.println("Palavra reservada else");}
  return	{System.out.println("Palavra reservada return");}
  length	{System.out.println("Palavra reservada length");}
  true		{System.out.println("Palavra reservada true");}
  false		{System.out.println("Palavra reservada false");}
  this		{System.out.println("Palavra reservada this");}
  new		{System.out.println("Palavra reservada new");}
  "System.out.println" {System.out.println("Palavra reservada System.out.println");}
  {identificador}  {System.out.println("identificador");}
  {inteiro}   	   {System.out.println("inteiro");}
  {WhiteSpace} {}
  {Comment} {}
  
  /* operators */

  "&&"		{System.out.println("Operador  &&");}
  "<"		{System.out.println("Operador  <");}
  "=="		{System.out.println("Operador  ==");}
  "!="		{System.out.println("Operador  !=");}
  "+"		{System.out.println("Operador  +");}
  "-"		{System.out.println("Operador  -");}
  "*"		{System.out.println("Operador  *");}
  "!"		{System.out.println("Operador  !");}

  /* delimitadores e pontuação */
	
  ";" 		{System.out.println("delimitador  ;");}
  "." 		{System.out.println("delimitador  .");}
  "," 		{System.out.println("delimitador  ,");}
  "=" 		{System.out.println("delimitador  =");}
  "("		{System.out.println("delimitador  (");}
  ")" 		{System.out.println("delimitador  )");}
  "{"		{System.out.println("delimitador  {");}
  "}" 		{System.out.println("delimitador  }");}
  "["		{System.out.println("delimitador  [");}
  "]"		{System.out.println("delimitador  ]");}




    
/* Insira as regras léxicas no espaço acima */     
     
. { throw new RuntimeException("Caractere ilegal! '" + yytext() + "' na linha: " + yyline + ", coluna: " + yycolumn); }
