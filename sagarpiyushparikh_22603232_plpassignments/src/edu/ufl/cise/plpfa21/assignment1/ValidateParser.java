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
		while (this.tokenCount < this.tokens.size()) {
			switch (this.tokens.get(this.tokenCount).getKind()) {
			case KW_VAR -> {
				checkofVariable();
				break;
			}
			case KW_VAL -> {
				checkofVal();
				break;
			}
			case EOF -> {
				checkofEOF();
				break;
			}
			case KW_FUN -> {
				checkofFunc();
				break;
			}
			default -> {
				throw new SyntaxException(this.token.getText(), this.token.getLine(),
						this.token.getCharPositionInLine());
			}
			}
			consume();
			this.tokenCount = this.tokenCount + 1;
		}
	}

	public IPLPToken checkofEOF() throws SyntaxException {
		if (this.token.getKind() == Kind.EOF) {
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());

	}

	public IPLPToken checkofVariable() throws SyntaxException {
		if (this.token.getKind() == Kind.KW_VAR) {
			consume();
			this.tokenCount = this.tokenCount + 1;
			checkofNameDef();
			if (this.token.getKind() == Kind.SEMI) {
				checkofSemicolon();
			} else {
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofSemicolon();
			}
			return this.token;
		}
		throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
	}

	public IPLPToken checkofVal() throws SyntaxException {
		if (this.token.getKind() == Kind.KW_VAL) {
			consume();
			this.tokenCount = this.tokenCount + 1;
			checkofNameDef();
			consume();
			this.tokenCount = this.tokenCount + 1;
			checkofEqual();
			consume();
			this.tokenCount = this.tokenCount + 1;
			consume();
			this.tokenCount = this.tokenCount + 1;
			checkofSemicolon();
			return this.token;
		}
		throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
	}

	public IPLPToken checkofFunc() throws SyntaxException {
		consume();
		this.tokenCount = this.tokenCount + 1;
		if (this.token.getKind() == Kind.IDENTIFIER) {
			consume();
			this.tokenCount = this.tokenCount + 1;
			if (this.token.getKind() == Kind.LPAREN) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				if (this.token.getKind() == Kind.RPAREN) {
					checkofFunctionType();
				} else {
					while (this.token.getKind() != Kind.RPAREN) {
						if (this.token.getKind() == Kind.COMMA) {
							consume();
							this.tokenCount = this.tokenCount + 1;
							checkofNameDef();
						} else {
							checkofNameDef();
//							consume();
//							this.tokenCount  = this.tokenCount +1;
						}
					}
					checkofFunctionType();
					return this.token;

				}
			}
			throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
		}
		throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
	}

	public IPLPToken checkofNameDef() throws SyntaxException {
		if (this.token.getKind() == Kind.IDENTIFIER) {
			consume();
			this.tokenCount = this.tokenCount + 1;
			if (this.token.getKind() == Kind.SEMI) {
				return this.token;
			} else {
				if (this.token.getKind() == Kind.COLON) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					checkofType();
				} else {
					return this.token;
				}
			}
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}

	public IPLPToken checkofFunctionType() throws SyntaxException {
		if (this.token.getKind() == Kind.RPAREN) {
			consume();
			this.tokenCount = this.tokenCount + 1;
			if (this.token.getKind() == Kind.COLON) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofType();
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofDo();
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofEnd();
				return this.token;
			} else {
				checkofDo();
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofEnd();
				return this.token;
			}
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}

	public IPLPToken checkofEqual() throws SyntaxException {
		System.out.println("line 4" + token.getKind());
		if (this.token.getKind() == Kind.ASSIGN) {
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}

	public IPLPToken checkofType() throws SyntaxException {
		if (this.token.getKind() == Kind.KW_INT || this.token.getKind() == Kind.KW_BOOLEAN
				|| this.token.getKind() == Kind.KW_STRING) {
			return this.token;
		}

		if (this.token.getKind() == Kind.KW_LIST) {
			consume();
			this.tokenCount = this.tokenCount + 1;
			if (this.token.getKind() == Kind.LSQUARE) {
				if (this.token.getKind() == Kind.KW_INT || this.token.getKind() == Kind.KW_BOOLEAN
						|| this.token.getKind() == Kind.KW_STRING) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					if (this.token.getKind() == Kind.RSQUARE) {
						return this.token;
					} else {
						throw new SyntaxException(this.token.getText(), this.token.getLine(),
								this.token.getCharPositionInLine());
					}

				} else {
					consume();
					this.tokenCount = this.tokenCount + 1;
					if (this.token.getKind() == Kind.RSQUARE) {
						return this.token;
					} else {
						throw new SyntaxException(this.token.getText(), this.token.getLine(),
								this.token.getCharPositionInLine());
					}
				}
			} else {
				throw new SyntaxException(this.token.getText(), this.token.getLine(),
						this.token.getCharPositionInLine());
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

	public IPLPToken checkofDo() throws SyntaxException {

		if (this.token.getKind() == Kind.KW_DO) {
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}

	public IPLPToken checkofEnd() throws SyntaxException {

		if (this.token.getKind() == Kind.KW_END) {
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
