package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;

public class CompilerComponentFactory extends CreateToken implements IPLPLexer {

	static ArrayList<IPLPToken> tokens;
	IPLPLexer lexer;
	public static enum State{
		START("start"), 
		KEYWORD("keyword"),
		Identifier("identifier"),
		IntLiteral("intliteral"),
		StringLiteral("stringliteral"),
		EscapeSeq("escapeseq"),
		Comment("comment"),
		HaveEqual("haveequal"),
		WhiteSpace("whitespace");
		
		State(String stateName){
			this.stateName = stateName;
		}
		String stateName;
		
		public String getStateName() {
			return stateName;
		}
	}

	
	static IPLPLexer getLexer(String input) {
		//TODO  create and return a Lexer instance to parse the given input.
		char [] chars = new char[input.length()];
		 int inputLength = input.length();
		 int indexPosition = 0;
		 int charIndex = 0;
		 int linePostion = 0;
		 int line = 0;
		 int startPosition = 0;
		  char EOFchar = 0;
		  State state = State.START;
		  for (int i= 0; i< input.length();i++) {
			  chars[i] = input.charAt(i);
		  }
		  while(indexPosition <= inputLength) {
			  charIndex = indexPosition  < inputLength?chars[indexPosition]:-1;
			  switch(state) {
			  	case START -> {
				  if(indexPosition < inputLength) {
					  indexPosition = skipWhiteSpace(indexPosition, length);
					  charIndex = indexPosition  < inputLength?chars[indexPosition]:-1;
					  startPosition = indexPosition;
					  switch(charIndex) {
					  case -1 ->{
						  tokens.add(new CreateToken(Kind.EOF,indexPosition,0,line,linePostion));
						  indexPosition = indexPosition + 1;
					  }
					  case ' ','\t' ->{
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case '\n','\r' ->{
						  indexPosition = indexPosition + 1;
						  line = line + 1;
						  linePostion =  1;
						  
					  }
					  case '+'-> {
						  tokens.add(new CreateToken(Kind.PLUS,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case '-' -> {
						  tokens.add(new CreateToken(Kind.MINUS,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case '/' -> {
						  tokens.add(new CreateToken(Kind.DIV,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case '*'->{
						  tokens.add(new CreateToken(Kind.TIMES,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;	  
					  }
					  case '(' -> {
						  tokens.add(new CreateToken(Kind.LPAREN,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case ')' -> {
						  tokens.add(new CreateToken(Kind.RPAREN,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case';' -> {
						  tokens.add(new CreateToken(Kind.SEMI,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case ','->{
						  tokens.add(new CreateToken(Kind.COMMA,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case ':' ->{
						  tokens.add(new CreateToken(Kind.COLON,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case '&' -> {
						  tokens.add(new CreateToken(Kind.AND,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case '|' -> {
						  tokens.add(new CreateToken(Kind.OR,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case '[' -> {
						  tokens.add(new CreateToken(Kind.LSQUARE,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case ']' ->{
						  tokens.add(new CreateToken(Kind.RSQUARE,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case '<' ->{
						  tokens.add(new CreateToken(Kind.LT,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case '>' -> {
						  tokens.add(new CreateToken(Kind.GT,startPosition,1,line,linePostion));
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case '=' -> {
						  state = State.HaveEqual;
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  case '0','1','2','3','4','5','6','7','8','9' -> {
						  state = State.IntLiteral;
						  indexPosition = indexPosition + 1;
						  linePostion = linePostion + 1;
					  }
					  }
				  }
			  	}
			  }
		  }
		return null;
	}

	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
		if(tokenCount >= token.size()) {
			return null;
		}
		return token.get(tokenCount++);
	}

	

}