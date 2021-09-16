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
	/*This @getLexer Function is used to return tokenlist 
	 * @input string passed from junit testcases
	 * */
	
	static IPLPLexer getLexer(String input) throws LexicalException {
		//TODO  create and return a  instance to parse the given input
	
		CompilerComponentFactory lexer = new CompilerComponentFactory(input);
		tokens = lexer.CreateLexerTokens(input);
		System.out.println("-----------------------");
		for(CreateToken i: tokens) {
			System.out.println(i.kind);
			System.out.println(i.input_Line);
			System.out.println(i.posInLine);
			System.out.println(i.text);
		}
		System.out.println("----------return calls-------------");
		System.out.println("-----------------------");
		IPLPLexer singletoken  = lexer;
		return singletoken; 
	

		
		

	}
	/*This @nextToken Function is used to return single token 
	 * */
	
	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
	
		
		//tokens.remove(tokens.size());
	
		
		if(tokenCount >= tokens.size()) {
			return null;
		}
		return tokens.get(tokenCount++);
	
	
			


	}

}