package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import java.util.HashMap;

public class CompilerComponentFactory extends CreateLexer implements IPLPLexer  {
	
	CompilerComponentFactory(String input) {
		super(input);
		// TODO Auto-generated constructor stub
	}
	static IPLPLexer getLexer(String input) throws LexicalException {
		//TODO  create and return a  instance to parse the given input
		CreateLexer lexer = new CreateLexer(input);
		lexer.CreateLexerTokens(input);
		return lexer; 
	}

	

	

}