package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

public class CompilerComponentFactory extends CreateLexer implements IPLPLexer  {
	static ArrayList<CreateToken> tokens;
	CompilerComponentFactory(String input) {
		super(input);
		
		// TODO Auto-generated constructor stub
	}
	static IPLPLexer getLexer(String input) throws LexicalException {
		//TODO  create and return a  instance to parse the given input
		try {
			CompilerComponentFactory lexer = new CompilerComponentFactory(input);
			tokens = lexer.CreateLexerTokens(input);
			System.out.println("-----------------------");
//			System.out.println(tokens.size());
//			System.out.println(tokens.get(0).kind);
//			System.out.println(tokens.get(0).input_Line);
//			System.out.println(tokens.get(0).posInLine);
//			System.out.println(tokens.get(0).text);
//			System.out.println("-----------------------");
//			System.out.println(tokens.get(1).kind);
//			System.out.println(tokens.get(1).input_Line);
//			System.out.println(tokens.get(1).posInLine);
//			System.out.println(tokens.get(1).text);
//			System.out.println("-----------------------");
//			System.out.println(tokens.get(2).kind);
//			System.out.println(tokens.get(2).input_Line);
//			System.out.println(tokens.get(2).posInLine);
//			System.out.println(tokens.get(2).text);
//			System.out.println("-----------------------");
//			System.out.println(tokens.get(3).kind);
//			System.out.println(tokens.get(3).input_Line);
//			System.out.println(tokens.get(3).posInLine);
//			System.out.println(tokens.get(3).text);
			System.out.println("----------return calls-------------");
			System.out.println("-----------------------");
			IPLPLexer singletoken  = lexer;
			return singletoken; 
		}catch(StringIndexOutOfBoundsException error) {
			LexicalException lexicalexception = new LexicalException("Error in character insert", 0,1 );
			 throw lexicalexception;
		}

	}
	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
		int a = 100;
		//tokens.remove(tokens.size());
	
		
		if(tokenCount >= tokens.size()) {
			return null;
		}
		return tokens.get(tokenCount++);
	}
	

	

}