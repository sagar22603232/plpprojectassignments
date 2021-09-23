package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment2.SyntaxException;

public class ValidateParser {
	IPLPToken token;
	ArrayList<CreateToken> tokens;
	int count;
	Kind kind;

	ValidateParser(ArrayList<CreateToken> tokens) {
		this.tokens = tokens;
		this.token = tokens.get(0);
		this.count = 1;
	}

	public void validateSingleParse(String message) throws SyntaxException {
		
	
		switch(this.token.getKind()) {
		case KW_VAR->{
			checkofVariable();
			break;
		}
		case KW_VAL->{
			checkofVal();
			break;
		}
		case EOF ->{
			checkofEOF();
			break;
		}
		default ->{
			throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
		}
		}
	
		
		
	}

	public IPLPToken checkofEOF()throws SyntaxException  {
		System.out.println("line 1" +token.getKind());
		
		if (this.token.getKind() == Kind.EOF) {
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
		
	}

	public IPLPToken checkofVariable() throws SyntaxException {
		System.out.println("line 2" +token.getKind());
		if (this.token.getKind() == Kind.KW_VAR) {
			consume();
			checkofNameDef();
			consume();
			checkofSemicolon();
			return this.token;
		}
		throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
	}
	
	public IPLPToken checkofVal() throws SyntaxException {
		System.out.println("line 2" +token.getKind());
		if (this.token.getKind() == Kind.KW_VAL) {
			consume();
			checkofNameDef();
			consume();
			checkofEqual();
			consume();
			consume();
			checkofSemicolon();
			return this.token;
		}
		throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
	}

	public IPLPToken checkofNameDef() throws SyntaxException {
		System.out.println("line 3" +token.getKind());
		if (this.token.getKind() == Kind.IDENTIFIER) {
			consume();
			checkofColon();
			consume();
			checkofType();
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}

	public IPLPToken checkofColon() throws SyntaxException {
		System.out.println("line 4" +token.getKind());
		if (this.token.getKind() == Kind.COLON) {
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}
	public IPLPToken checkofEqual() throws SyntaxException {
		System.out.println("line 4" +token.getKind());
		if (this.token.getKind() == Kind.ASSIGN) {
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}
	public IPLPToken checkofType() throws SyntaxException {
		System.out.println("line 5" +token.getKind());
		if (this.token.getKind() == Kind.KW_INT ||
				this.token.getKind() == Kind.KW_BOOLEAN ||
						this.token.getKind() == Kind.KW_STRING) {
			return this.token;
		}
		
		if (this.token.getKind() == Kind.KW_LIST) {
			consume();
				if(this.token.getKind() == Kind.LSQUARE) {
					
					System.out.println("line 8" +this.token.getKind());
					if (this.token.getKind() == Kind.KW_INT ||
							this.token.getKind() == Kind.KW_BOOLEAN ||
									this.token.getKind() == Kind.KW_STRING 
							) {
						consume();
						System.out.println("line 9" +this.token.getKind());
						if(this.token.getKind() == Kind.RSQUARE ) {
							return this.token;
						}
						else {
							throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
						}
						
					}
					else {
						consume();
						if(this.token.getKind() == Kind.RSQUARE ) {
							return this.token;
						}
						else {
							throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
						}
					}
				}
				else {
					throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
				}
				
				
			}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}
	public IPLPToken checkofSemicolon() throws SyntaxException {

		if (this.token.getKind() == Kind.SEMI) {
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}

	public void consume() throws SyntaxException {
		if (this.count >= tokens.size()) {
			
		} else {
			if (this.tokens.get(this.count).kind == kind.ERROR) {

				throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
			} else {
				this.token = this.tokens.get(this.count++);
			}
		}

	}

}
