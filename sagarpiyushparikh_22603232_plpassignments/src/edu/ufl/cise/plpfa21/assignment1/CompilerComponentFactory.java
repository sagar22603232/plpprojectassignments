package edu.ufl.cise.plpfa21.assignment1;

import java.util.*;

public class CompilerComponentFactory implements PLPTokenKinds,IPLPLexer{
	ArrayList<IPLPToken> tokens;

	static IPLPLexer getLexer(String input) {
		//TODO  create and return a Lexer instance to parse the given input.
		if(input.length() > 0) {
			return null;
		}
		else {
			tokens.add(Kind.EOF)
			return ;
		}
	}
	

}
