package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

public class CreateLexer extends CreateToken implements  IPLPToken {



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
	public boolean checkDigit(String input) {
		try {
			if(Integer.parseInt(input) < Integer.MAX_VALUE) {
				return true;	
			}
			else {
				return false;
			}
		}catch(Exception e) {
			return false;
			
		}
	}
	public ArrayList<CreateToken> CreateLexerTokens (String input) throws LexicalException {
		try {
			int inputLength = input.length();
			String givenInput = input;
			 int indexPosition = 0;
			 char charIndex = 0;
			 int linePostion = 1;
			 int line = 1;
			 int startPosition = 1;
			 int startLinePostion = 0;
			 int charcount = 0;
			 int spacecount = 0;
			 int linecount = 0;
			  char EOFchar = 0;
			  String albha = "";
			  int charposition = 0;
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
								  tokens.add( new CreateToken(Kind.EOF,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex,albha));
								  indexPosition = indexPosition + 1;
								  break;
							  }
							  case ' ','\t' ->{
								  System.out.println("linne 96");
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case '\n','\r' ->{
								  System.out.println("linne 128");
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
								  linecount = linecount +1;
								  linePostion = linePostion + 1;
								  System.out.println("linne 131");
								  System.out.println(linePostion);
							  }
							  case '+'-> {
								  tokens.add(new CreateToken(Kind.PLUS,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case '-' -> {
								  tokens.add(new CreateToken(Kind.MINUS, indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case '/' -> {
								  state = state.Comment;
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case '*'->{
								  tokens.add(new CreateToken(Kind.TIMES,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;	  
							  }
							  case '(' -> {
								  tokens.add(new CreateToken(Kind.LPAREN,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case ')' -> {
								  tokens.add(new CreateToken(Kind.RPAREN,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case';' -> {
								  tokens.add(new CreateToken(Kind.SEMI, indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case ','->{
								  tokens.add(new CreateToken(Kind.COMMA,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case ':' ->{
								  tokens.add(new CreateToken(Kind.COLON,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case '&' -> {
								  tokens.add(new CreateToken(Kind.AND,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case '|' -> {
								  tokens.add(new CreateToken(Kind.OR,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case '[' -> {
								  tokens.add(new CreateToken(Kind.LSQUARE,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case ']' ->{
								  tokens.add(new CreateToken(Kind.RSQUARE,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case '<' ->{
								  tokens.add(new CreateToken(Kind.LT,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case '>' -> {
								  tokens.add(new CreateToken(Kind.GT,indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							  case '=' -> {
								  state = State.HaveEqual;
								  charposition = indexPosition;
								  indexPosition = indexPosition + 1;
								  
								  //startLinePostion = startLinePostion + 1;
							  }
							  case '0','1','2','3','4','5','6','7','8','9' -> {
								  state = State.IntLiteral;
								  charposition = indexPosition;
								  System.out.println("line 228------------");
								  System.out.println(charposition);
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1;
							  }
							 default -> {
								 System.out.println("line 192");
								 if(Character.isJavaIdentifierStart(input.charAt(indexPosition))) {
									 System.out.println("line 192");
									 chdict.append(input.charAt(indexPosition));
									 state = state.Identifier;
									 charposition = indexPosition;
//									 indexPosition = indexPosition + 1;
//									  linePostion = linePostion + 1;
								 }
								 else {
//									  LexicalException lexicalexception = new LexicalException("Error in character insert", line,linePostion );
									 state = state.START;
									 indexPosition = indexPosition + 1;
									 startLinePostion = startLinePostion + 1;
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
					  			startLinePostion = startLinePostion + 1;
						
					  		}
					  		else {
					  		
					  			if(keyWords.containsKey(chdict.toString())) {
					  				
					  				tokens.add(new CreateToken(keyWords.get(chdict.toString()), indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex,albha));
					  				
					  			}
					  			else {
					  				System.out.println(" line 259");
					  				System.out.println(input.charAt(charposition));
					  				if(linecount > 0) {
					  					spacecount = charposition - charcount - linecount;	
					  				}
					  				else {
					  					spacecount = charposition;
					  				}
					  				
					  				startLinePostion = charposition;
					  				System.out.println(" line 271------------------------------");
					  				System.out.println(spacecount);
//					  				while(charposition <= indexPosition ) {
//					  					if(Character.isJavaIdentifierStart(input.charAt(charposition))) {
//						  					System.out.println(" line 2");
//						  					System.out.println(charposition);
//						  					albha = albha + Character.toString(input.charAt(charposition));
//						  				
//						  				}
//					  					charposition = charposition + 1;
//					  									  					
//					  				}	
					  				while(charposition <= givenInput.length() ) {
					  					if(Character.isWhitespace(givenInput.charAt(charposition))|| givenInput.charAt(charposition)=='\n' || givenInput.charAt(charposition)=='\r') {
					  						break;
						  				
						  				}
					  					else {
					  						System.out.println(" line 292------------------------------");
							  				System.out.println(givenInput.charAt(charposition));
						  					albha = albha + Character.toString(givenInput.charAt(charposition));
						  					charposition = charposition + 1;
					  						
					  					}
					  					
					  									  					
					  				}
					  				indexPosition = charposition;
					  				tokens.add(new CreateToken(Kind.IDENTIFIER,indexPosition,inputLength,linePostion,spacecount,input.charAt(charposition),input.charAt(charposition), albha));
					  			}
					  			albha = "";
					  			startLinePostion = startLinePostion + 1 ;
					  			charcount = charposition;
					  			linecount = 0;
					  			state = State.START;
					  			chdict.setLength(0);
					  		}
					  	}
					  	case IntLiteral -> {
					  		
					  			boolean digitflag = checkDigit(input);
					  			System.out.println("line 297------------------------");
					  			System.out.println(digitflag);
					  			if(digitflag) {
						  				
							  			if(Character.isDigit(input.charAt(indexPosition))) {
								  			chdict.append(Character.getNumericValue(input.charAt(indexPosition)));
								  			 indexPosition = indexPosition + 1;
								  			startLinePostion = startLinePostion + 1;  
								  		}
								  		else {
								  			
								  				Integer.parseInt(chdict.toString());
								  				tokens.add(new CreateToken(Kind.INT_LITERAL,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex,albha));
								  			
								  			state = State.START;
								  			chdict.setLength(0);
								  		}
						  			
						  		
					  			}
					  			else {
//					  				if(linecount > 0) {
//					  					spacecount = charposition - charcount - linecount;	
//					  				}
//					  				else {
//					  					spacecount = charposition - charcount;
//					  				}
					  				spacecount = charposition;
					  				startLinePostion = charposition;
					  				System.out.println(" line 271------------------------------");
					  				System.out.println(spacecount);	
					  				while(charposition <= givenInput.length() ) {
					  					if(Character.isWhitespace(givenInput.charAt(charposition))|| givenInput.charAt(charposition)=='\n' || givenInput.charAt(charposition)=='\r' || Character.isLetter(givenInput.charAt(charposition))) {
					  						break;
						  				
						  				}
					  					else {
					  						if(Character.isDigit(givenInput.charAt(charposition))) {
					  							System.out.println(" line 292------------------------------");
								  				System.out.println(givenInput.charAt(charposition));
							  					albha = albha + Character.toString(givenInput.charAt(charposition));
							  					charposition = charposition + 1;
					  						}	
					  						else {
					  							break;
					  						}
					  					}
					  					
					  									  					
					  				}
					  				indexPosition = charposition;
					  				tokens.add(new CreateToken(Kind.INT_LITERAL,indexPosition,inputLength,linePostion,spacecount,input.charAt(charposition),'1', albha));
					  			
					  			albha = "";
					  			startLinePostion = startLinePostion + 1 ;
					  			charcount = charposition;
					  			linecount = 0;
							  			state = State.START;
							  			chdict.setLength(0);
							  		
//
//					  			}throw new LexicalException("Error in character insert", line,linePostion );
					  			}
					  		
					  	} 
					  	case HaveEqual ->{
					  		if(((input.charAt(indexPosition)) == '=')){
					  			System.out.println("line 328");
								  tokens.add(new CreateToken(Kind.EQUALS,indexPosition,inputLength,linePostion,charposition,input.charAt(indexPosition),input.charAt(indexPosition),"=="));
								  indexPosition = indexPosition + 1;
								  startLinePostion = startLinePostion + 1; 
								  state = State.START;
					  		}
					  		else {
					  			System.out.println("line 297");
					  			System.out.println(input.charAt(indexPosition - 1));
					  			tokens.add(new CreateToken(Kind.ASSIGN,indexPosition,inputLength,linePostion,charposition,'=','=',albha));
					  			indexPosition = indexPosition + 1;
					  			startLinePostion = startLinePostion + 1;
								  state = State.START;
					  		}
					  	}
					  	case Comment->
					  	{
					  		if(input.charAt(indexPosition) == '*') {
					  			 indexPosition = indexPosition + 1;
					  			startLinePostion = startLinePostion + 1;
								  state = state.Start_Comment;
					  		}
					  		else {
					  			tokens.add(new CreateToken(Kind.DIV, indexPosition,inputLength,linePostion,startLinePostion,input.charAt(indexPosition),input.charAt(indexPosition),albha));
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
					  				startLinePostion = startLinePostion + 1;
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
				  tokens.add(new CreateToken(Kind.EOF,indexPosition,inputLength,linePostion,startLinePostion,charIndex,charIndex,albha));
				  System.out.println("line 346");
			      }
			
			  catch(Exception error) {
				 
				throw error;
				
			  }
			  return tokens;	
		}catch(Exception error) {
			 
			throw error;
			
		  }
	}
	
	
}
