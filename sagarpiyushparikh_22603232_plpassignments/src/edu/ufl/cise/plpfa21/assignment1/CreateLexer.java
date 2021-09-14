package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

public class CreateLexer extends CreateToken implements IPLPLexer, IPLPToken {



	static ArrayList<CreateToken> tokens;
	IPLPLexer lexer;
	CreateToken singletoken;
	static HashMap<String,Kind> keyWords =new HashMap<String, Kind>();
	int tokenCount;
	
	CreateLexer(String input) {
		super(input);
		 tokens  = new ArrayList<CreateToken>();
		// TODO Auto-generated constructor stub
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
	public ArrayList<CreateToken> CreateLexerTokens (String input) throws LexicalException {
		int inputLength = input.length();
		 int indexPosition = 0;
		 char charIndex = 0;
		 int linePostion = 0;
		 int line = 0;
		 int startPosition = 0;
		 int startLinePostion = 0;
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
							  tokens.add( new CreateToken(Kind.EOF,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
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
							  startLinePostion = startLinePostion + 1;
							  line = line + 1;
							  
						  }
						  case '+'-> {
							  tokens.add(new CreateToken(Kind.PLUS,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case '-' -> {
							  tokens.add(new CreateToken(Kind.MINUS, indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case '/' -> {
							  state = state.Comment;
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case '*'->{
							  tokens.add(new CreateToken(Kind.TIMES,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;	  
						  }
						  case '(' -> {
							  tokens.add(new CreateToken(Kind.LPAREN,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case ')' -> {
							  tokens.add(new CreateToken(Kind.RPAREN,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case';' -> {
							  tokens.add(new CreateToken(Kind.SEMI, indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case ','->{
							  tokens.add(new CreateToken(Kind.COMMA,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case ':' ->{
							  tokens.add(new CreateToken(Kind.COLON,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case '&' -> {
							  tokens.add(new CreateToken(Kind.AND,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case '|' -> {
							  tokens.add(new CreateToken(Kind.OR,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case '[' -> {
							  tokens.add(new CreateToken(Kind.LSQUARE,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case ']' ->{
							  tokens.add(new CreateToken(Kind.RSQUARE,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case '<' ->{
							  tokens.add(new CreateToken(Kind.LT,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
						  }
						  case '>' -> {
							  tokens.add(new CreateToken(Kind.GT,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
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
//								 indexPosition = indexPosition + 1;
//								  linePostion = linePostion + 1;
							 }
							 else {
//								  LexicalException lexicalexception = new LexicalException("Error in character insert", line,linePostion );
								 state = state.START;
								 indexPosition = indexPosition + 1;
								  linePostion = linePostion + 1;
								  break;
								 
							 }
						 }
						  
						  }
					  }
					  
				  	}
				  	case Identifier ->{
				  		System.out.println("line 211");
				  		
				  		if(Character.isJavaIdentifierStart(input.charAt(indexPosition))) {
				  			chdict.append(input.charAt(indexPosition));
				  			System.out.println("line 247");
				  			System.out.println(indexPosition);
				  			System.out.println(linePostion);
				  			indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
					
				  		}
				  		else {
				  			if(keyWords.containsKey(chdict.toString())) {
				  				tokens.add(new CreateToken(keyWords.get(chdict.toString()), indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
				  				
				  			}
				  			else {
				  				tokens.add(new CreateToken(Kind.IDENTIFIER,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
				  			
				  			}
				  			state = State.START;
				  			chdict.setLength(0);
				  		}
				  	}
				  	case IntLiteral -> {
				  		try {
				  			if(Integer.parseInt(input) < Integer.MAX_VALUE) {
				  				
					  			if(Character.isDigit(input.charAt(indexPosition))) {
						  			chdict.append(Character.getNumericValue(input.charAt(indexPosition)));
						  			 indexPosition = indexPosition + 1;
									  linePostion = linePostion + 1;  
						  		}
						  		else {
						  			try
						  			{
						  				Integer.parseInt(chdict.toString());
						  				tokens.add(new CreateToken(Kind.INT_LITERAL,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
						  			}
						  			catch(Exception error) {
										  LexicalException lexicalexception = new LexicalException("Error in character insert", line,linePostion );
										 throw lexicalexception;
						  			}
						  			state = State.START;
						  			chdict.setLength(0);
						  		}
				  			}
				  			else {
				  				LexicalException lexicalexception = new LexicalException("Error in character insert", line,linePostion );
								 throw lexicalexception;
				  			}
				  			
				  		}catch(Exception error) {
				  			LexicalException lexicalexception = new LexicalException("Error in character insert", line,linePostion );
							 throw lexicalexception;
				  		}
				  	}
				  	case HaveEqual ->{
				  		if(((input.charAt(indexPosition)) == '=')){
							  tokens.add(new CreateToken(Kind.EQUALS,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
							  indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1; 
							  state = State.START;
				  		}
				  		else {
				  			System.out.println("line 297");
				  			tokens.add(new CreateToken(Kind.ASSIGN,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
				  			indexPosition = indexPosition + 1;
							  linePostion = linePostion + 1;
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
				  			tokens.add(new CreateToken(Kind.DIV, indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
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
		  catch (StringIndexOutOfBoundsException e) {
			  tokens.add(new CreateToken(Kind.EOF,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex));
			 
		      }
		  catch(LexicalException error) {
			 
			throw error;
			
		  }
		  return tokens;
	}
	
	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
		System.out.println("line 297");
		int end = 0;
		for(int i = 0;i < tokens.size();i++) {
			if(tokens.get(i).kind == kind.EOF) {
				end  = i;
			}
		}
		for(;end<tokens.size();end++) {
			tokens.remove(end);
		}
		for(CreateToken i : tokens) {
			System.out.println(i.kind);
		}
		int tokenCount = 0;
		if(tokenCount >= tokens.size()) {
			return null;
		}
		return tokens.get(tokenCount++);
	}

}
