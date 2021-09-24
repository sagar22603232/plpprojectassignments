package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment2.SyntaxException;

public class ValidateParser {
	IPLPToken token;
	ArrayList<CreateToken> tokens;
	int count;
	int tokenCount;
	Kind kind;

	ValidateParser(ArrayList<CreateToken> tokens) {
		this.tokens = tokens;
		this.token = tokens.get(0);
		this.tokenCount = 0;
		this.count = 1;
	}

	public void validateSingleParse(String message) throws SyntaxException {
		while(this.tokenCount  < this.tokens.size()) {
			switch(this.tokens.get(this.tokenCount).getKind()) {
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
			consume();
			this.tokenCount  = this.tokenCount +1;
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
			this.tokenCount  = this.tokenCount +1;
			checkofNameDef();
			consume();
			this.tokenCount  = this.tokenCount +1;
			System.out.println("line 11" +token.getKind());
			checkofSemicolon();
			return this.token;
		}
		throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
	}
	
	public IPLPToken checkofVal() throws SyntaxException {
		System.out.println("line 2" +token.getKind());
		if (this.token.getKind() == Kind.KW_VAL) {
			consume();
			this.tokenCount  = this.tokenCount +1;
			checkofNameDef();
			consume();
			this.tokenCount  = this.tokenCount +1;
			checkofEqual();
			consume();
			this.tokenCount  = this.tokenCount +1;
			consume();
			this.tokenCount  = this.tokenCount +1;
			checkofSemicolon();
			return this.token;
		}
		throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
	}

	public IPLPToken checkofNameDef() throws SyntaxException {
		System.out.println("line 3" +token.getKind());
		if (this.token.getKind() == Kind.IDENTIFIER) {
			consume();
			this.tokenCount  = this.tokenCount +1;
			checkofColon();
			consume();
			this.tokenCount  = this.tokenCount +1;
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
			this.tokenCount  = this.tokenCount +1;
				if(this.token.getKind() == Kind.LSQUARE) {
					
					System.out.println("line 8" +this.token.getKind());
					if (this.token.getKind() == Kind.KW_INT ||
							this.token.getKind() == Kind.KW_BOOLEAN ||
									this.token.getKind() == Kind.KW_STRING 
							) {
						consume();
						this.tokenCount  = this.tokenCount +1;
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
						this.tokenCount  = this.tokenCount +1;
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
			System.out.println("line 12" +token.getKind());
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
