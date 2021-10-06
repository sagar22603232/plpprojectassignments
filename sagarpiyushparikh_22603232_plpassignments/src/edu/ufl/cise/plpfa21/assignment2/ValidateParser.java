package edu.ufl.cise.plpfa21.assignment2;
import edu.ufl.cise.plpfa21.assignment1.CreateToken;
import edu.ufl.cise.plpfa21.assignment1.IPLPToken;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment1.LexicalException;
import java.util.ArrayList;

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
			if (this.token.getKind() == Kind.ASSIGN) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				while(this.token.getKind() != Kind.SEMI) {
					checkofExpression();
				}
				checkofSemicolon();
			} else {
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
			while(this.token.getKind() != Kind.SEMI) {
				checkofExpression();
				
			}
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
					return this.token;
				} else {
					while (this.token.getKind() != Kind.RPAREN) {
						checkofNameDef();
						if(this.token.getKind()!= Kind.COMMA ||this.token.getKind() == Kind.RPAREN) {
							break;
						}
						if (this.token.getKind() == Kind.COMMA) {
							consume();
							this.tokenCount = this.tokenCount + 1;
							checkofNameDef();
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
				while(this.token.getKind() !=Kind.KW_END)
				{
					checkofBlock();
					consume();
					this.tokenCount = this.tokenCount + 1;
				}
					checkofEnd();
					return this.token;	
			} else {
				checkofDo();
				consume();
				this.tokenCount = this.tokenCount + 1;
				while(this.token.getKind() !=Kind.KW_END)
				{
					checkofBlock();
					consume();
					this.tokenCount = this.tokenCount + 1;
				}
				checkofEnd();
				return this.token;
			}
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}

	public IPLPToken checkofEqual() throws SyntaxException {
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
				consume();
				this.tokenCount = this.tokenCount + 1;
				while(this.token.getKind() != Kind.RSQUARE) {
					checkofType();
					consume();
					this.tokenCount = this.tokenCount + 1;
				}
				return this.token;
				} 
			}

				throw new SyntaxException(this.token.getText(), this.token.getLine(),
						this.token.getCharPositionInLine());
			

		
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
		while(this.token.getKind()!= Kind.KW_END ) {
			switch(this.token.getKind()) {
			case KW_LET ->{
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofNameDef();
				if(this.token.getKind() == Kind.ASSIGN) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					checkofExpression();
					checkofDo();
					while(this.token.getKind()!=Kind.KW_END) {
						consume();
						this.tokenCount = this.tokenCount + 1;
						checkofBlock();	
					}
					checkofEnd();
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
				checkofDo();
				while(this.token.getKind()!=Kind.KW_END) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					checkofBlock();	
				}
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
				checkofExpression();
				if(this.token.getKind() == Kind.ASSIGN) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					checkofExpression();
					checkofSemicolon();
					return this.token;
				}
				else {
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
			checkofLogicalExpression();
			return exp;
		}
		catch(Exception e) {
			throw e;
		}
	}
	public ExpressionParser checkofLogicalExpression() throws SyntaxException{
		try {
			checkofComparisonExpression();
			while(this.token.getKind() == Kind.AND || this.token.getKind() == Kind.OR)
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
			checkofAdditiveExpression();
			if(this.token.getKind() == Kind.LT || 
			this.token.getKind() == Kind.GT ||
			this.token.getKind() == Kind.EQUALS ||
			this.token.getKind() == Kind.NOT_EQUALS
					)
{
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofAdditiveExpression();;
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
			checkofMultiplicativeExpression();
			while(this.token.getKind() == Kind.PLUS || this.token.getKind() == Kind.MINUS) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					checkofMultiplicativeExpression();
			}
			return exp;
		}
		catch(Exception e) {
			throw e;
		}
	}
	public ExpressionParser checkofMultiplicativeExpression() throws SyntaxException{
		try {
			checkofUnaryExpression();
			while(this.token.getKind() == Kind.TIMES || this.token.getKind() == Kind.DIV) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				checkofUnaryExpression();
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
		if(this.token.getKind() == Kind.KW_NIL || 
			this.token.getKind() == Kind.KW_TRUE || 
			this.token.getKind() == Kind.KW_FALSE ||
			this.token.getKind() == Kind.INT_LITERAL ||
			this.token.getKind() == Kind.STRING_LITERAL) {
			consume();
			this.tokenCount = this.tokenCount + 1;
			return exp;
		}
		else {
			if(this.token.getKind() == Kind.LPAREN) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				if(this.token.getKind() == Kind.RPAREN) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					return exp;
				}else {
					checkofExpression();
					if(this.token.getKind() == Kind.RPAREN) {
						consume();
						this.tokenCount = this.tokenCount + 1;
						return exp;
					}	
				}	
			}
			else {
				if(this.token.getKind() == Kind.IDENTIFIER) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					if(this.token.getKind() == Kind.LPAREN) {
						consume();
						this.tokenCount = this.tokenCount + 1;
						if(this.token.getKind() == Kind.RPAREN) {
							consume();
							this.tokenCount = this.tokenCount + 1;
							return exp;
						}
						while(this.token.getKind()!= Kind.RPAREN) {
							checkofExpression();
							consume();
							this.tokenCount = this.tokenCount + 1;
							if(this.token.getKind() == Kind.COMMA) {
								consume();
								this.tokenCount = this.tokenCount + 1;
								checkofExpression();
							}
						}
						return exp;
						
					}
					else {
						if(this.token.getKind() == Kind.LSQUARE) {
							consume();
							this.tokenCount = this.tokenCount + 1;
							checkofExpression();
							if(this.token.getKind() == Kind.RSQUARE) {
								consume();
								this.tokenCount = this.tokenCount + 1;
								return exp;
							}
							
						}
						else {
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
		consume();
		this.tokenCount = this.tokenCount + 1;
		return this.token;	
	}
	throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
}
	public IPLPToken checkofCase() throws SyntaxException {
		if (this.token.getKind() == Kind.KW_CASE) {
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}
	
	public void consume() throws SyntaxException {
		if (this.count >= tokens.size()) {

		} else {
			if (this.tokens.get(this.count).getKind() == Kind.ERROR) {
				try {
					throwAdded();
				} catch (LexicalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				this.token = this.tokens.get(this.count++);
			}
		}

	}
	
	public IPLPToken throwAdded() throws LexicalException {
		// TODO Auto-generated method stub
	try {
		
	
		
		if (this.tokens.get(this.count).getKind() == Kind.ERROR) {
			LexicalException lexerror = new LexicalException("Error in character insert", 0,1 );
			 throw lexerror;
		}
		return this.token;
		
	}
	catch(Exception e) {
		 LexicalException lexerror = new LexicalException("Error in character insert", 0,1 );
		 throw lexerror;
	}
	}

}