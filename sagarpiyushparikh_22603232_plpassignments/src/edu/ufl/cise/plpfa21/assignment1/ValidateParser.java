package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment2.SyntaxException;

public class ValidateParser {
	IPLPToken token;
	ArrayList<CreateToken> tokens;
	ExpressionParser exp = new ExpressionParser();
	ArrayList<CreateToken> expressionlist = exp.expressionTokens;
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
				System.out.println("41 line");
				System.out.println(this.token.getKind());
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
			checkofEqual();
			consume();
			this.tokenCount = this.tokenCount + 1;
			checkofExpression();
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
					System.out.println("107 line");
					System.out.println(this.token.getKind());
					return this.token;
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
					//checkofFunctionType();
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
					consume();
					this.tokenCount = this.tokenCount + 1;
					return this.token;
				} else {
					return this.token;
				}
			}
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}

	public IPLPToken checkofFunctionType() throws SyntaxException {
		if (this.token.getKind() == Kind.RPAREN) {
			System.out.println("Line 151");
			System.out.println(this.token.getKind());
			consume();
			this.tokenCount = this.tokenCount + 1;
			System.out.println("Line 155");
			System.out.println(this.token.getKind());
			if (this.token.getKind() == Kind.COLON) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofType();
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofDo();
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofBlock();
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofEnd();
				return this.token;
			} else {
				System.out.println(this.token.getKind());
				checkofDo();
				consume();
				this.tokenCount = this.tokenCount + 1;
				System.out.println("Line 176");
				System.out.println(this.token.getKind());
				checkofBlock();
				consume();
				this.tokenCount = this.tokenCount + 1;
				System.out.println("181 line");
				System.out.println(this.token.getKind());
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
	public IPLPToken checkofColon() throws SyntaxException {

		if (this.token.getKind() == Kind.COLON) {
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
	
	public IPLPToken checkofBlock() throws SyntaxException {
		System.out.println("Line 261");
		System.out.println(this.token.getKind());
		while(this.token.getKind()!= Kind.KW_END || this.token.getKind()!= Kind.SEMI) {
			switch(this.token.getKind()) {
			case KW_LET ->{
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofNameDef();
				if(this.token.getKind() == Kind.ASSIGN) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					checkofExpression();
					System.out.println("280 line");
					System.out.println(this.token.getKind());
					checkofSemicolon();
//					consume();
//					this.tokenCount = this.tokenCount + 1;
					System.out.println("285 line");
					System.out.println(this.token.getKind());
					return this.token;
				}
				break;	
			}
			case KW_SWITCH ->{
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofExpression();
				while(this.token.getKind()!= Kind.KW_DEFAULT) {
					checkofSwitch();	
					}
				if(this.token.getKind() == Kind.KW_DEFAULT) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					checkofBlock();
					System.out.println("302 line");
					System.out.println(this.token.getKind());
					consume();
					this.tokenCount = this.tokenCount + 1;
					checkofEnd();
					return this.token;
				}
				break;	
				
			}
			case KW_IF,KW_WHILE ->{
				consume();
				this.tokenCount = this.tokenCount + 1;
				
					checkofExpression();
					
					System.out.println("Line 324");
					System.out.println(this.token.getKind());
				checkofDo();
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofBlock();
				consume();
				this.tokenCount = this.tokenCount + 1;
				System.out.println("Line 323");
				System.out.println(this.token.getKind());
				checkofEnd();
				return this.token;
			}
			case KW_RETURN ->{
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofExpression();
				checkofSemicolon();
				return this.token;
				
			}
			default -> {			
				System.out.println("Line 290");
				System.out.println(this.token.getKind());
				checkofExpression();
				System.out.println("Line 297");
				System.out.println(this.token.getKind());
				if(this.token.getKind() == Kind.ASSIGN) {
					System.out.println("Line 300");
					System.out.println(this.token.getKind());
					consume();
					this.tokenCount = this.tokenCount + 1;
					checkofExpression();
					System.out.println(this.token.getKind());
//					consume();
//					this.tokenCount = this.tokenCount + 1;
					checkofSemicolon();
					return this.token;
				}
				else {
					System.out.println("Line 362");
					System.out.println(this.token.getKind());
					checkofSemicolon();
					return this.token;
				}
			}
			}
			
		}
		return this.token;
	}
	public ExpressionParser checkofExpression() throws SyntaxException{
		try {
			System.out.println("Line 316");
			System.out.println(this.token.getKind());
			checkofLogicalExpression();
			return exp;
		}
		catch(Exception e) {
			throw e;
		}
	}
	public ExpressionParser checkofLogicalExpression() throws SyntaxException{
		try {
			System.out.println("Line 326");
			System.out.println(this.token.getKind());
			checkofComparisonExpression();
			System.out.println("Line 386");
			System.out.println(this.token.getKind());
			while(this.token.getKind() == Kind.AND || this.token.getKind() == Kind.OR
					)
{

				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofComparisonExpression();
			
			}
			return exp;
		}
		catch(Exception e) {
			throw e;
		}
	}
	public ExpressionParser checkofComparisonExpression() throws SyntaxException{
		try {
			System.out.println("Line 362");
			System.out.println(this.token.getKind());
			checkofAdditiveExpression();
			System.out.println("Line 407");
			System.out.println(this.token.getKind());
			if(this.token.getKind() == Kind.LT || 
			this.token.getKind() == Kind.GT ||
			this.token.getKind() == Kind.EQUALS ||
			this.token.getKind() == Kind.NOT_EQUALS
					)
{
				System.out.println("Line 411");
				System.out.println(this.token.getKind());
//				this.expressionlist.add(new CreateToken(this.token.getKind(), this.token.getCharPositionInLine(), this.token.getText().length(), 
//						this.token.getLine(),
//						this.token.getLine(), this.token.getText().charAt(0), this.token.getText().charAt(0), 
//						this.token.getText()));
				consume();
				this.tokenCount = this.tokenCount + 1;
				System.out.println("Line 418");
				System.out.println(this.token.getKind());
				checkofAdditiveExpression();
//				consume();
//				this.tokenCount = this.tokenCount + 1;
				System.out.println("Line 421");
				System.out.println(this.token.getKind());
//				this.expressionlist.add(new CreateToken(this.token.getKind(), this.token.getCharPositionInLine(), this.token.getText().length(), 
//						this.token.getLine(),
//						this.token.getLine(), this.token.getText().charAt(0), this.token.getText().charAt(0), 
//						this.token.getText()));
				return exp;
			}
			else {
				return exp;
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
	public ExpressionParser checkofAdditiveExpression() throws SyntaxException{
		try {
			System.out.println("Line 400");
			System.out.println(this.token.getKind());
			checkofMultiplicativeExpression();
			System.out.println("Line 456");
			System.out.println(this.token.getKind());
			while(this.token.getKind() == Kind.PLUS || this.token.getKind() == Kind.MINUS) {
//					this.expressionlist.add(new CreateToken(this.token.getKind(), this.token.getCharPositionInLine(), this.token.getText().length(), 
//							this.token.getLine(),
//							this.token.getLine(), this.token.getText().charAt(0), this.token.getText().charAt(0), 
//							this.token.getText()));
					consume();
					this.tokenCount = this.tokenCount + 1;
					checkofMultiplicativeExpression();
					System.out.println("Line 454");
					System.out.println(this.token.getKind());
//					this.expressionlist.add(new CreateToken(this.token.getKind(), this.token.getCharPositionInLine(), this.token.getText().length(), 
//							this.token.getLine(),
//							this.token.getLine(), this.token.getText().charAt(0), this.token.getText().charAt(0), 
//							this.token.getText()));
					
				
				
			}
			return exp;
		}
		catch(Exception e) {
			throw e;
		}
	}
	public ExpressionParser checkofMultiplicativeExpression() throws SyntaxException{
		try {
			System.out.println("Line 433");
			System.out.println(this.token.getKind());
			checkofUnaryExpression();
			System.out.println("Line 485");
			System.out.println(this.token.getKind());
			while(this.token.getKind() == Kind.TIMES || this.token.getKind() == Kind.DIV) {
//				this.expressionlist.add(new CreateToken(this.token.getKind(), this.token.getCharPositionInLine(), this.token.getText().length(), 
//						this.token.getLine(),
//						this.token.getLine(), this.token.getText().charAt(0), this.token.getText().charAt(0), 
//						this.token.getText()));
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofUnaryExpression();
//				this.expressionlist.add(new CreateToken(this.token.getKind(), this.token.getCharPositionInLine(), this.token.getText().length(), 
//						this.token.getLine(),
//						this.token.getLine(), this.token.getText().charAt(0), this.token.getText().charAt(0), 
//						this.token.getText()));
				
			
			}
			return exp;
		}
		catch(Exception e) {
			throw e;
		}
	}
	public ExpressionParser checkofUnaryExpression() throws SyntaxException{
		try {
	
			if(this.token.getKind() == Kind.BANG || this.token.getKind() == Kind.MINUS) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofPrimaryExpression();
			}
			else {
				checkofPrimaryExpression();
				return exp;
			}
			return exp;
		}
		catch(Exception e) {
			throw e;
		}
	}
	public ExpressionParser checkofPrimaryExpression() throws SyntaxException{
		System.out.println("Line 483");
		System.out.println(this.token.getKind());
		if(this.token.getKind() == Kind.KW_NIL || 
			this.token.getKind() == Kind.KW_TRUE || 
			this.token.getKind() == Kind.KW_FALSE ||
			this.token.getKind() == Kind.INT_LITERAL ||
			this.token.getKind() == Kind.STRING_LITERAL) {
//			this.expressionlist.add(new CreateToken(this.token.getKind(), this.token.getCharPositionInLine(), this.token.getText().length(), 
//					this.token.getLine(),
//					this.token.getLine(), this.token.getText().charAt(0), this.token.getText().charAt(0), 
//					this.token.getText()));
			consume();
			this.tokenCount = this.tokenCount + 1;
			return exp;
		}
		else {
			if(this.token.getKind() == Kind.LPAREN) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				
					checkofExpression();
					if(this.token.getKind() == Kind.RPAREN) {
						System.out.println("Line 571");
						System.out.println(this.token.getKind());
						consume();
						this.tokenCount = this.tokenCount + 1;
						return exp;
					}
				
				
			}
			else {
				System.out.println("Line 512");
				System.out.println(this.token.getKind());
				if(this.token.getKind() == Kind.IDENTIFIER) {
					System.out.println("Line 515");
					System.out.println(this.token.getKind());
//					this.expressionlist.add(new CreateToken(this.token.getKind(), this.token.getCharPositionInLine(), this.token.getText().length(), 
//							this.token.getLine(),
//							this.token.getLine(), this.token.getText().charAt(0), this.token.getText().charAt(0), 
//							this.token.getText()));
					consume();
					this.tokenCount = this.tokenCount + 1;
					System.out.println("Line 519");
					System.out.println(this.token.getKind());
					if(this.token.getKind() == Kind.LPAREN) {
//						this.expressionlist.add(new CreateToken(this.token.getKind(), this.token.getCharPositionInLine(), this.token.getText().length(), 
//								this.token.getLine(),
//								this.token.getLine(), this.token.getText().charAt(0), this.token.getText().charAt(0), 
//								this.token.getText()));
						consume();
						this.tokenCount = this.tokenCount + 1;
						while(this.token.getKind()!= Kind.RPAREN) {
							checkofExpression();
							consume();
							this.tokenCount = this.tokenCount + 1;
							if(this.token.getKind() == Kind.COMMA) {
								consume();
								this.tokenCount = this.tokenCount + 1;
								checkofExpression();
								
								System.out.println("Line 563");
								System.out.println(this.token.getKind());
							}
						}
						System.out.println("Line 598");
						System.out.println(this.token.getKind());
						return exp;
						
					}
					else {
						if(this.token.getKind() == Kind.LSQUARE) {
//							this.expressionlist.add(new CreateToken(this.token.getKind(), this.token.getCharPositionInLine(), this.token.getText().length(), 
//									this.token.getLine(),
//									this.token.getLine(), this.token.getText().charAt(0), this.token.getText().charAt(0), 
//									this.token.getText()));
							consume();
							this.tokenCount = this.tokenCount + 1;
							checkofExpression();
							if(this.token.getKind() == Kind.RSQUARE) {
								consume();
								this.tokenCount = this.tokenCount + 1;
								System.out.println("Line 594");
								System.out.println(this.token.getKind());
								return exp;
							}
							
						}
						else {
							System.out.println("Line 555");
							System.out.println(this.token.getKind());
							return exp;
						}
					}
					
				}
				
			}
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}
public IPLPToken checkofSwitch() throws SyntaxException{
	if(this.token.getKind() == Kind.KW_CASE) {
		checkofCase();
		consume();
		this.tokenCount = this.tokenCount + 1;
		checkofExpression();
		checkofColon();
		consume();
		this.tokenCount = this.tokenCount + 1;
		checkofBlock();
		System.out.println("Line 617");
		System.out.println(this.token.getKind());
		consume();
		this.tokenCount = this.tokenCount + 1;
		return this.token;	
	}
	throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
}
	public IPLPToken checkofCase() throws SyntaxException {
		System.out.println("line 4" + token.getKind());
		if (this.token.getKind() == Kind.KW_CASE) {
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}
	
	public void consume() throws SyntaxException {
		if (this.count >= tokens.size()) {

		} else {
			if (this.tokens.get(this.count).kind == Kind.ERROR) {

				throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
			} else {
				this.token = this.tokens.get(this.count++);
			}
		}

	}

}
