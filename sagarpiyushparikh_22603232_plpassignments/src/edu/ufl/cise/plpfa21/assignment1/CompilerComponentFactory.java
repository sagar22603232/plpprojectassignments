package edu.ufl.cise.plpfa21.assignment1;
import java.util.ArrayList;
import edu.ufl.cise.plpfa21.assignment2.IPLPParser;

public class CompilerComponentFactory extends CreateLexer implements IPLPLexer  {
	static ArrayList<CreateToken> tokens;
	static IPLPLexer singletoken;
	CompilerComponentFactory(String input) {
		super(input);
		
		// TODO Auto-generated constructor stub
	}
	/*This @getLexer Function is used to return tokenlist 
	 * @input string passed from junit testcases
	 * */
	
	static IPLPLexer getLexer(String input){
		//TODO  create and return a  instance to parse the given input
	
		CompilerComponentFactory lexer = new CompilerComponentFactory(input);
		try {
			tokens = lexer.CreateLexerTokens(input);
			singletoken  = lexer;
		} catch (LexicalException e) {
			// TODO Auto-generated catch block
			return lexer;
		}

		return singletoken; 
	}
	 public static IPLPParser getParser(String input) {
	   	 //Implement this in Assignment 2
	   	 //Your parser will create a lexer.
		 
		 getLexer(input);
		 CreateParser parser = new CreateParser(tokens, input);
		 return parser;
	    }
	

	/*This @nextToken Function is used to return single token 
	 * */
	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
	try {
		if(tokenCount >= tokens.size()) {
			return null;
		}
		if(tokens.get(tokenCount).kind == kind.ERROR) {
			LexicalException lexerror = new LexicalException("Error in character insert", 0,1 );
			 throw lexerror;
		}
	
		
		
		return tokens.get(tokenCount++);
	}
	catch(Exception e) {
		 LexicalException lexerror = new LexicalException("Error in character insert", 0,1 );
		 throw lexerror;
	}
	}


}