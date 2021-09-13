package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import java.util.HashMap;

public class CompilerComponentFactory extends CreateToken implements IPLPLexer {
	
	static ArrayList<CreateToken> tokens  = new ArrayList<CreateToken>();
	IPLPLexer lexer;
	static HashMap<String,Kind> keyWords =new HashMap<String, Kind>();
	int tokenCount;
	
	
	CompilerComponentFactory(String input){
		super(input);
		keyWords.put("integer", kind.KW_INT);
		keyWords.put("boolean", kind.KW_BOOLEAN);
		keyWords.put("case", kind.KW_CASE);
		keyWords.put("default", kind.KW_DEFAULT);
		keyWords.put("do", kind.KW_DO);
		keyWords.put("else", kind.KW_ELSE);
		keyWords.put("end", kind.KW_END);
		keyWords.put("false", kind.KW_FALSE);
		keyWords.put("float", kind.KW_FLOAT);
		keyWords.put("fun", kind.KW_FUN);
		keyWords.put("if", kind.KW_IF);
		keyWords.put("let", kind.KW_LET);
		keyWords.put("list", kind.KW_LIST);
		keyWords.put("nil", kind.KW_NIL);
		keyWords.put("return", kind.KW_RETURN);
		keyWords.put("string", kind.KW_STRING);
		keyWords.put("switch", kind.KW_SWITCH);
		keyWords.put("true", kind.KW_TRUE);
		keyWords.put("val", kind.KW_VAL);
		keyWords.put("var", kind.KW_VAR);
		keyWords.put("while", kind.KW_WHILE);
		
	}
	public static enum State{
		START("start"), 
		KEYWORD("keyword"),
		Identifier("identifier"),
		IntLiteral("intliteral"),
		StringLiteral("stringliteral"),
		EscapeSeq("escapeseq"),
		Comment("comment"),
		HaveEqual("haveequal"),
		WhiteSpace("whitespace"),
		Start_Comment("startcomment");
		State(String stateName){
			this.stateName = stateName;
		}
		String stateName;
		
		public String getStateName() {
			return stateName;
		}
	}

	public static int removeSpaces(int indexPostion,int inputLength,String input) {
		while(Character.isWhitespace(input.charAt(indexPostion))) {
			if(input.charAt(indexPostion) == '\r' || input.charAt(indexPostion) == '\n') {
				return indexPostion;
			}
			else {
				indexPostion = indexPostion +1;
				if(indexPostion == inputLength) {
					return indexPostion;
				}
			}
		}
		return indexPostion;
	}
	static IPLPLexer getLexer(String input) throws LexicalException {
		//TODO  create and return a  instance to parse the given input
		 int inputLength = input.length();
		 int indexPosition = 0;
		 char charIndex = 0;
		 int linePostion = 0;
		 int line = 0;
		 int startPosition = 0;
		  char EOFchar = 0;
		  StringBuilder chdict = new StringBuilder();
		  State state = State.START;
		  System.out.println("line 79");
		  try {
			  while(indexPosition <= inputLength) {
				  System.out.println(indexPosition);
				  System.out.println(inputLength);
				  System.out.println(input.charAt(indexPosition));
				  System.out.println("line 88");
				  if(indexPosition < inputLength) {
					  charIndex = input.charAt(indexPosition);
				  }
				  else {
					  charIndex = EOFchar;
				  }
				  System.out.println(state);
				  switch(state) {
				  
				  	case START -> {
				  		System.out.println("line 95");
					  if(indexPosition < inputLength) {
						  indexPosition = removeSpaces(indexPosition, inputLength, input);
						  if(indexPosition < inputLength) {
							  charIndex = input.charAt(indexPosition);
						  }
						  else {
							  charIndex = EOFchar;
						  }
						  startPosition = indexPosition;
						  System.out.println("line 101");
						  switch(charIndex) {
						  
						  case 0 ->{
							  System.out.print("-1");
								
							  tokens.add(new CreateToken(Kind.EOF,indexPosition,0,line,linePostion));
							  indexPosition = indexPosition + 1;
							  break;
						  }
						  case ' ','\t' ->{
							  System.out.print("linne 96");
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case '\n','\r' ->{
							  System.out.print("linne 128");
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
							  state = state.Comment;
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
						 default -> {
							 System.out.println("line 192");
							 if(Character.isJavaIdentifierStart(input.charAt(indexPosition))) {
								 System.out.println("line 192");
								 chdict.append(input.charAt(indexPosition));
								 state = state.Identifier;
								 indexPosition = indexPosition + 1;
								  linePostion = linePostion + 1;
							 }
							 else {
								  LexicalException lexicalexception = new LexicalException("Error in character insert", line,linePostion );
								 throw lexicalexception;
							 }
						 }
						  
						  }
					  }
					  
				  	}
				  	case Identifier ->{
				  		System.out.println("line 211");
				  		if(Character.isJavaIdentifierStart(input.charAt(indexPosition))) {
				  			chdict.append(input.charAt(indexPosition));
							 indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
				  		}
				  		else {
				  			if(keyWords.containsKey(chdict.toString())) {
				  				tokens.add(new CreateToken(keyWords.get(chdict.toString()),startPosition,1,line,linePostion));
				  			}
				  			else {
				  				tokens.add(new CreateToken(Kind.IDENTIFIER,startPosition,1,line,linePostion));
				  			}
				  			state = State.START;
				  			chdict.setLength(0);
				  		}
				  	}
				  	case IntLiteral -> {
				  		try {
				  			Integer.parseInt(input);
				  			if(Character.isDigit(input.charAt(indexPosition))) {
					  			chdict.append(Character.getNumericValue(input.charAt(indexPosition)));
					  			 indexPosition = indexPosition + 1;
								  linePostion = linePostion + 1;  
					  		}
					  		else {
					  			try
					  			{
					  				Integer.parseInt(chdict.toString());
					  				tokens.add(new CreateToken(Kind.INT_LITERAL,startPosition,1,line,linePostion));
					  			}
					  			catch(Exception error) {
									  LexicalException lexicalexception = new LexicalException("Error in character insert", line,linePostion );
									 throw lexicalexception;
					  			}
					  			state = State.START;
					  			chdict.setLength(0);
					  		}
				  			
				  		}catch(Exception error) {
				  			
				  		}
				  	}
				  	case HaveEqual ->{
				  		if(((input.charAt(indexPosition)) == '=')){
							  tokens.add(new CreateToken(Kind.EQUALS,startPosition,1,line,linePostion));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1; 
							  state = State.START;
				  		}
				  		else {
				  			tokens.add(new CreateToken(Kind.ASSIGN,startPosition,1,line,linePostion));
							  state = State.START;
				  		}
				  	}
				  	case Comment->
				  	{
				  		if(input.charAt(indexPosition) == '*') {
				  			 indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
							  state = state.Start_Comment;
				  		}
				  		else {
				  			tokens.add(new CreateToken(Kind.DIV,startPosition,1,line,linePostion));
				  			state = State.START;
				  		}
				  	}
				  	case Start_Comment ->
				  	{
				  		if(input.charAt(indexPosition) == '*') {
				  			indexPosition = indexPosition + 1;
				  			if(indexPosition < inputLength) {
				  				charIndex = input.charAt(indexPosition);
				  			}
				  			else {
				  				charIndex = EOFchar;
				  			}
				  			if(charIndex == '/') {
				  				indexPosition = indexPosition + 1;
								  linePostion = linePostion + 1;
								  state = State.START;
				  			}
				  			else {
				  				if(charIndex == -1) {
				  					state = State.START;
				  				}
				  				else {
				  					indexPosition = indexPosition + 1;
									  linePostion = linePostion + 1;
									  state = State.Start_Comment;	
				  				}
				  			}
				  		}
				  	}
				  }
			  }
		  }
		  catch(Exception error) {
			  tokens.add(new CreateToken(Kind.EOF,indexPosition,0,line,linePostion));
		  }
		  
		  
		return (new CompilerComponentFactory(input)); 
	}

	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
		if(tokenCount >= tokens.size()) {
			return null;
		}
		return tokens.get(tokenCount++);
	}

	

}