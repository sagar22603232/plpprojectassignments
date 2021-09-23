package edu.ufl.cise.plpfa21.assignment1;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment2.SyntaxException;
public class ValidateParser{
	
	public void validateSingleParse(IPLPToken token) throws SyntaxException{
		checkofEOF(token);
	}
	public IPLPToken checkofEOF(IPLPToken token)throws SyntaxException {
		
			if(token.getKind() == Kind.EOF) {
				return token;
			}
			throw new SyntaxException(token.getText(),token.getLine(),token.getCharPositionInLine());	
	}
	
}
