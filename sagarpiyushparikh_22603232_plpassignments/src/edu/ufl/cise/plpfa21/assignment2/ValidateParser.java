package edu.ufl.cise.plpfa21.assignment2;

import edu.ufl.cise.plpfa21.assignment1.CreateToken;
import edu.ufl.cise.plpfa21.assignment3.astimpl.*;
import edu.ufl.cise.plpfa21.assignment1.IPLPToken;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment1.LexicalException;
import java.util.ArrayList;
import java.util.List;

import edu.ufl.cise.plpfa21.assignment3.ast.*;

public class ValidateParser {
	IPLPToken token;
	ArrayList<CreateToken> tokens;
	ExpressionParser exp = new ExpressionParser();
	ArrayList<IDeclaration> declarationsList = new ArrayList<IDeclaration>();
	ArrayList<IExpression> branchExpressions = new ArrayList<IExpression>();
	ArrayList<IBlock> blocksExpressions = new ArrayList<IBlock>();
	
	int count;
	int tokenCount;
	Kind kind;

	ValidateParser(ArrayList<CreateToken> tokens) {
		this.tokens = tokens;
		this.token = tokens.get(0);
		this.tokenCount = 0;
		this.count = 1;
	}

	public IASTNode validateSingleParse(String message) throws SyntaxException {
		if (this.token.getKind() == Kind.EOF) {
			return new Program__(this.token.getLine(), this.token.getCharPositionInLine(), this.token.getText(),
					declarationsList);

		} else {
			while (this.tokenCount < this.tokens.size()) {
				
				switch (this.tokens.get(this.tokenCount).getKind()) {
				case KW_VAR -> {
					this.declarationsList.add(checkofVariable());

				}
				case KW_VAL -> {
					this.declarationsList.add(checkofVal());

				}
				case EOF -> {
					checkofEOF();

				}
				case KW_FUN -> {
					this.declarationsList.add(checkofFunc());
					

				}
				default -> {
					break;
				}
				}
				consume();
				this.tokenCount = this.tokenCount + 1;
			}
			for(IDeclaration i:declarationsList) {
				System.out.println("Line 65"+i);
			}
			return new Program__(this.token.getLine(), this.token.getCharPositionInLine(), this.token.getText(),
					declarationsList);

		}
	}

	public IPLPToken checkofEOF() throws SyntaxException {
		if (this.token.getKind() == Kind.EOF) {
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());

	}

	public MutableGlobal__ checkofVariable() throws SyntaxException {
		IPLPToken firstToken = this.token;
		IExpression expNode = null;
		consume();
		this.tokenCount = this.tokenCount + 1;
		INameDef nameDefNode = checkofNameDef();
		if (this.token.getKind() == Kind.ASSIGN) {
			consume();
			this.tokenCount = this.tokenCount + 1;
			while (this.token.getKind() != Kind.SEMI) {
				expNode = checkofExpression();
			}
			checkofSemicolon();
		} else {
			checkofSemicolon();
		}
		return new MutableGlobal__(firstToken.getLine(), firstToken.getCharPositionInLine(), firstToken.getText(),
				nameDefNode, expNode);
	}

	public ImmutableGlobal__ checkofVal() throws SyntaxException {
		IPLPToken firstToken = this.token;
		IExpression expNode = null;
		consume();
		this.tokenCount = this.tokenCount + 1;
		INameDef nameDefNode = checkofNameDef();
		checkofEqual();
		consume();
		this.tokenCount = this.tokenCount + 1;
		while (this.token.getKind() != Kind.SEMI) {
			expNode = checkofExpression();

		}
		checkofSemicolon();
		return new ImmutableGlobal__(firstToken.getLine(), firstToken.getCharPositionInLine(), firstToken.getText(),
				nameDefNode, expNode);

	}

	public FunctionDeclaration___ checkofFunc() throws SyntaxException {
		IPLPToken firstToken = this.token;
		IType typeNode = null;
		IBlock blockNode = null;
		ArrayList<INameDef> funArgs = new ArrayList<INameDef>();
		consume();
		this.tokenCount = this.tokenCount + 1;
		if (this.token.getKind() == Kind.IDENTIFIER) {
			IIdentifier name = new Identifier__(this.token.getLine(), this.token.getCharPositionInLine(),
					this.token.getText(), this.token.getStringValue());
			consume();
			this.tokenCount = this.tokenCount + 1;
			if (this.token.getKind() == Kind.LPAREN) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				if (this.token.getKind() == Kind.RPAREN) {
					typeNode = checkofFunctionType();
					checkofDo();
					consume();
					this.tokenCount = this.tokenCount + 1;	
						blockNode = checkforBlock();
						return new FunctionDeclaration___(firstToken.getLine(), firstToken.getCharPositionInLine(),
								firstToken.getText(), name, funArgs, typeNode, blockNode);
				} else {
					while (this.token.getKind() != Kind.RPAREN) {
						funArgs.add(checkofNameDef());
						if (this.token.getKind() != Kind.COMMA || this.token.getKind() == Kind.RPAREN) {
							break;
						}
						if (this.token.getKind() == Kind.COMMA) {
							consume();
							this.tokenCount = this.tokenCount + 1;
							funArgs.add(checkofNameDef());
							if(this.token.getKind() == Kind.RPAREN) {
								break;
							}
							else {
								consume();
								this.tokenCount = this.tokenCount + 1;
								
							}
						}
					}
					
					if (this.token.getKind() == Kind.KW_DO) {
						while(this.token.getKind() != Kind.KW_END) {
							checkofDo();
							consume();
							this.tokenCount = this.tokenCount + 1;
								blockNode = checkforBlock();
								System.out.println("Line 165"+ this.token.getKind());
						}
						System.out.println("Line 168"+ funArgs);
						return new FunctionDeclaration___(firstToken.getLine(), firstToken.getCharPositionInLine(),
								firstToken.getText(), name, funArgs, typeNode, blockNode);
							
						
					} else {
						typeNode = checkofFunctionType();
						while(this.token.getKind() != Kind.KW_END) {
							checkofDo();
							consume();
							this.tokenCount = this.tokenCount + 1;
								blockNode = checkforBlock();
								System.out.println("Line 177"+ this.token.getKind());
						}
						System.out.println("Line 182"+ funArgs);
						return new FunctionDeclaration___(firstToken.getLine(), firstToken.getCharPositionInLine(),
								firstToken.getText(), name, funArgs, typeNode, blockNode);	
						
					}
				}
				
				
			}
			throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
		}
		throw new SyntaxException(token.getText(), token.getLine(), token.getCharPositionInLine());
	}

	public INameDef checkofNameDef() throws SyntaxException {
		IPLPToken firstToken = this.token;
		IIdentifier identifierNode = checkofIDENTIFIER();
		consume();
		this.tokenCount = this.tokenCount + 1;
		if (this.token.getKind() == Kind.SEMI) {
			return new NameDef__(firstToken.getLine(), firstToken.getCharPositionInLine(), firstToken.getText(),
					identifierNode, null);
		} else {
			if (this.token.getKind() == Kind.COLON) {
				
				consume();
				this.tokenCount = this.tokenCount + 1;
				IType typeNode = checkofType();
				consume();
				this.tokenCount = this.tokenCount + 1;
				return new NameDef__(firstToken.getLine(), firstToken.getCharPositionInLine(), firstToken.getText(),
						identifierNode, typeNode);
			} else {
				return new NameDef__(firstToken.getLine(), firstToken.getCharPositionInLine(), firstToken.getText(),
						identifierNode, null);
			}
		}

	}

	public IType checkofFunctionType() throws SyntaxException {
		if (this.token.getKind() == Kind.RPAREN) {
			consume();
			this.tokenCount = this.tokenCount + 1;
			if (this.token.getKind() == Kind.COLON) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				IType typeNode = checkofType();
				consume();
				this.tokenCount = this.tokenCount + 1;

				return typeNode;
			} else {

				return null;
			}
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}

	public IBlock checkforBlock() throws SyntaxException {
		IPLPToken firstToken = this.token;
		ArrayList<IStatement> statements = new ArrayList<IStatement>();
		while (this.token.getKind() != Kind.KW_END) {
			statements.add(checkofBlock());
			consume();
			this.tokenCount = this.tokenCount + 1;
		}
		checkofEnd();
		return new Block__(firstToken.getLine(), firstToken.getCharPositionInLine(), firstToken.getText(), statements);
	}

	public IBlock checkforCaseBlock() throws SyntaxException {
		IPLPToken firstToken = this.token;
		ArrayList<IStatement> statements = new ArrayList<IStatement>();
		while(this.token.getKind()!=Kind.KW_CASE && this.token.getKind()!=Kind.KW_DEFAULT ) {
			statements.add(checkofBlock());	
			if(this.token.getKind() == Kind.KW_DEFAULT) {
				break;
			}
			consume();
		this.tokenCount = this.tokenCount + 1;
		}
		return new Block__(firstToken.getLine(), firstToken.getCharPositionInLine(), firstToken.getText(), statements);
	}

	public IPLPToken checkofEqual() throws SyntaxException {
		if (this.token.getKind() == Kind.ASSIGN) {
			return this.token;
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}

	public IIdentifier checkofIDENTIFIER() throws SyntaxException {
		if (this.token.getKind() == Kind.IDENTIFIER) {
			return new Identifier__(this.token.getLine(), this.token.getCharPositionInLine(), this.token.getText(),
					this.token.getText());
		}
		throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
	}

	public IType checkofType() throws SyntaxException {
		switch (this.token.getKind()) {
		case KW_INT -> {
			return new PrimitiveType__(this.token.getLine(), this.token.getCharPositionInLine(), this.token.getText(),
					IType.TypeKind.INT);
		}
		case KW_BOOLEAN -> {
			return new PrimitiveType__(this.token.getLine(), this.token.getCharPositionInLine(), this.token.getText(),
					IType.TypeKind.BOOLEAN);
		}
		case KW_STRING -> {
			return new PrimitiveType__(this.token.getLine(), this.token.getCharPositionInLine(), this.token.getText(),
					IType.TypeKind.STRING);
		}
		case KW_LIST -> {
			IPLPToken firstToken = this.token;
			IType typeNode = null;
			consume();
			this.tokenCount = this.tokenCount + 1;
			if (this.token.getKind() == Kind.LSQUARE) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				while (this.token.getKind() != Kind.RSQUARE) {
					typeNode = checkofType();
					consume();
					this.tokenCount = this.tokenCount + 1;
				}
				System.out.println("Line 299"+this.token.getKind());
				return new ListType__(firstToken.getLine(), firstToken.getCharPositionInLine(), firstToken.getText(),
						typeNode);
			}
		}
		default -> {
			throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
		}
		}
		return new PrimitiveType__(this.token.getLine(), this.token.getCharPositionInLine(), this.token.getText(),
				IType.TypeKind.INT);
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

	public IStatement checkofBlock() throws SyntaxException {
		while (this.token.getKind() != Kind.KW_END) {
			switch (this.token.getKind()) {
			case KW_LET -> {
				System.out.println("Line 347"+this.token.getKind());
				IPLPToken firstToken = this.token;
				IExpression expNode = null;
				IBlock blockNode = null;
				consume();
				this.tokenCount = this.tokenCount + 1;
				INameDef nameDefNode = checkofNameDef();
				if (this.token.getKind() == Kind.ASSIGN) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					expNode = checkofExpression();
					System.out.println("Line 358"+this.token.getKind());
					while(this.token.getKind() != Kind.KW_END) {
						checkofDo();
						consume();
						this.tokenCount = this.tokenCount + 1;
							blockNode = checkforBlock();
					}
						return new LetStatement__(firstToken.getLine(), firstToken.getCharPositionInLine(),
								firstToken.getText(), blockNode, expNode, nameDefNode);
					

				}
				else {
					System.out.println("Line 358"+this.token.getKind());
					while(this.token.getKind() != Kind.KW_END) {
						checkofDo();
						consume();
						this.tokenCount = this.tokenCount + 1;
							blockNode = checkforBlock();
					}
						return new LetStatement__(firstToken.getLine(), firstToken.getCharPositionInLine(),
								firstToken.getText(), blockNode, expNode, nameDefNode);
				}
			}
			case KW_SWITCH -> {
				IPLPToken firstToken = this.token;
				IBlock defaultBlockNode = null;
				consume();
				this.tokenCount = this.tokenCount + 1;
				IExpression switchNode = checkofExpression();
				while (this.token.getKind() != Kind.KW_DEFAULT) {
					checkofSwitch();
				}
				if (this.token.getKind() == Kind.KW_DEFAULT) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					defaultBlockNode = checkforBlock();
					checkofEnd();
				}
				return new SwitchStatement__(firstToken.getLine(), firstToken.getCharPositionInLine(),
						firstToken.getText(), switchNode, this.branchExpressions, this.blocksExpressions,
						defaultBlockNode);

			}
			case KW_IF -> {
				IPLPToken firstToken = this.token;
				IExpression expNode = null;
				IBlock blockNode = null;
				consume();
				this.tokenCount = this.tokenCount + 1;
				expNode = checkofExpression();
				while(this.token.getKind() != Kind.KW_END) {
					checkofDo();
					consume();
					this.tokenCount = this.tokenCount + 1;
					
						blockNode = checkforBlock();
				}
			
					return new IfStatement__(firstToken.getLine(), firstToken.getCharPositionInLine(),
							firstToken.getText(), expNode, blockNode);
				
			}
			case KW_WHILE -> {
				System.out.println("Line 406"+this.token.getKind());
				IPLPToken firstToken = this.token;
				IExpression expNode = null;
				IBlock blockNode = null;
				consume();
				this.tokenCount = this.tokenCount + 1;
				expNode = checkofExpression();
				while(this.token.getKind() != Kind.KW_END) {
					checkofDo();
					consume();
					this.tokenCount = this.tokenCount + 1;
						blockNode = checkforBlock();
				}
				
					System.out.println("Line 417"+this.token.getKind());
					return new WhileStatement__(firstToken.getLine(), firstToken.getCharPositionInLine(),
							firstToken.getText(), expNode, blockNode);
				
			}
			case KW_RETURN -> {
				IPLPToken firstToken = this.token;
				IExpression expNode = null;
				consume();
				this.tokenCount = this.tokenCount + 1;
				expNode = checkofExpression();
				checkofSemicolon();
				return new ReturnStatement__(firstToken.getLine(), firstToken.getCharPositionInLine(),
						firstToken.getText(), expNode);

			}
			default -> {
				System.out.println("Line 431"+this.token.getKind());
				IPLPToken firstToken = this.token;
				IExpression lefExpNode = checkofExpression();
				System.out.println("Line 434"+this.token.getKind());
				if (this.token.getKind() == Kind.ASSIGN) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					IExpression rightExpNode = checkofExpression();
					checkofSemicolon();
					return new AssignmentStatement__(firstToken.getLine(), firstToken.getCharPositionInLine(),
							firstToken.getText(), lefExpNode, rightExpNode);
				} else {
					System.out.println("Line 443"+this.token.getKind());
					checkofSemicolon();
					return new AssignmentStatement__(firstToken.getLine(), firstToken.getCharPositionInLine(),
							firstToken.getText(), lefExpNode, null);
				}
			}
			}

		}
		return null;
	}

	public IExpression checkofExpression() throws SyntaxException {
		try {
			return checkofLogicalExpression();
		} catch (Exception e) {
			throw e;
		}
	}

	public IExpression checkofLogicalExpression() throws SyntaxException {
		try {
			IPLPToken firstToken = this.token;
			IExpression leftExpressionNode = checkofComparisonExpression();
			IExpression rightExpressionNode = null;
			while (this.token.getKind() == Kind.AND || this.token.getKind() == Kind.OR) {
				IPLPToken operand = this.token;
				consume();
				this.tokenCount = this.tokenCount + 1;
				rightExpressionNode = checkofComparisonExpression();
				leftExpressionNode = new BinaryExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
						firstToken.getText(), leftExpressionNode, rightExpressionNode, operand.getKind());

			}

			return leftExpressionNode;

		} catch (Exception e) {
			throw e;
		}
	}

	public IExpression checkofComparisonExpression() throws SyntaxException {
		try {
			IPLPToken firstToken = this.token;
			IExpression leftExpressionNode = checkofAdditiveExpression();
			IExpression rightExpressionNode = null;
			if (this.token.getKind() == Kind.LT || this.token.getKind() == Kind.GT
					|| this.token.getKind() == Kind.EQUALS || this.token.getKind() == Kind.NOT_EQUALS) {
				IPLPToken operand = this.token;
				consume();
				this.tokenCount = this.tokenCount + 1;
				rightExpressionNode = checkofAdditiveExpression();
				;
				leftExpressionNode = new BinaryExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
						firstToken.getText(), leftExpressionNode, rightExpressionNode, operand.getKind());
			}
			return leftExpressionNode;

		} catch (Exception e) {
			throw e;
		}
	}

	public IExpression checkofAdditiveExpression() throws SyntaxException {
		try {
			IPLPToken firstToken = this.token;
			IExpression leftExpressionNode = checkofMultiplicativeExpression();
			IExpression rightExpressionNode = null;
			while (this.token.getKind() == Kind.PLUS || this.token.getKind() == Kind.MINUS) {
				IPLPToken operand = this.token;
				consume();
				this.tokenCount = this.tokenCount + 1;
				rightExpressionNode = checkofMultiplicativeExpression();

				leftExpressionNode = new BinaryExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
						firstToken.getText(), leftExpressionNode, rightExpressionNode, operand.getKind());
			}

			return leftExpressionNode;

		} catch (Exception e) {
			throw e;
		}
	}

	public IExpression checkofMultiplicativeExpression() throws SyntaxException {
		try {
			IPLPToken firstToken = this.token;
			IExpression leftExpressionNode = checkofUnaryExpression();
			IExpression rightExpressionNode = null;
			while (this.token.getKind() == Kind.TIMES || this.token.getKind() == Kind.DIV) {
				IPLPToken operand = this.token;
				consume();
				this.tokenCount = this.tokenCount + 1;
				rightExpressionNode = checkofUnaryExpression();
				leftExpressionNode = new BinaryExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
						firstToken.getText(), leftExpressionNode, rightExpressionNode, operand.getKind());
			}

			return leftExpressionNode;

		} catch (Exception e) {
			throw e;
		}
	}

	public IExpression checkofUnaryExpression() throws SyntaxException {
		try {
			IPLPToken firstToken = this.token;
			IExpression singleExpressionNode = null;
			if (this.token.getKind() == Kind.BANG || this.token.getKind() == Kind.MINUS) {
				IPLPToken operand = this.token;
				consume();
				this.tokenCount = this.tokenCount + 1;
				singleExpressionNode = checkofPrimaryExpression();
				return new UnaryExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
						firstToken.getText(), singleExpressionNode, operand.getKind());
			} else {
				singleExpressionNode = checkofPrimaryExpression();
				return singleExpressionNode;
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public IExpression checkofPrimaryExpression() throws SyntaxException {
		IPLPToken firstToken = this.token;
		switch (this.token.getKind()) {
		case KW_NIL -> {
			consume();
			this.tokenCount = this.tokenCount + 1;
			return new NilConstantExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
					firstToken.getText());
		}
		case KW_TRUE -> {
			consume();
			this.tokenCount = this.tokenCount + 1;
			return new BooleanLiteralExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
					firstToken.getText(), true);
		}
		case KW_FALSE -> {
			consume();
			this.tokenCount = this.tokenCount + 1;
			return new BooleanLiteralExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
					firstToken.getText(), false);
		}
		case INT_LITERAL -> {
			consume();
			this.tokenCount = this.tokenCount + 1;
			return new IntLiteralExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
					firstToken.getText(), firstToken.getIntValue());
		}
		case STRING_LITERAL -> {
			consume();
			this.tokenCount = this.tokenCount + 1;
			return new StringLiteralExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
					firstToken.getText(), firstToken.getStringValue());
		}
		case LPAREN -> {
			IExpression expNode;
			consume();
			this.tokenCount = this.tokenCount + 1;
			System.out.println("Line 407"+this.token.getKind());
			if (this.token.getKind() == Kind.RPAREN) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				return null;
			} else {
				expNode = checkofExpression();
				System.out.println("Line 613"+this.token.getKind());
				if (this.token.getKind() == Kind.RPAREN) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					return expNode;
				} else {
					break;
				}
			}
		}
		case IDENTIFIER -> {
			IIdentifier name = new Identifier__(firstToken.getLine(), firstToken.getCharPositionInLine(),
					firstToken.getText(), firstToken.getText());
			;
			ArrayList<IExpression> args = new ArrayList<IExpression>();
			System.out.println("Line 629"+this.token.getKind());
			consume();
			this.tokenCount = this.tokenCount + 1;
			System.out.println("Line 632"+this.token.getKind());
			if (this.token.getKind() == Kind.LPAREN) {
				consume();
				this.tokenCount = this.tokenCount + 1;
				if (this.token.getKind() == Kind.RPAREN) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					
					return new FunctionCallExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
							firstToken.getText(), name, args);
				}
				while (this.token.getKind() != Kind.RPAREN) {
					args.add(checkofExpression());
					System.out.println("Line 669"+this.token.getKind());
					consume();
					this.tokenCount = this.tokenCount + 1;
					System.out.println("Line 648"+this.token.getKind());
					if (this.token.getKind() == Kind.COMMA) {
						consume();
						this.tokenCount = this.tokenCount + 1;
						args.add(checkofExpression());
						if(this.token.getKind() == Kind.RPAREN) {
							break;
						}
						consume();
						this.tokenCount = this.tokenCount + 1;
						
					}
					
					if(this.token.getKind() == Kind.SEMI) {
						break;
					}
					if(this.token.getKind() == Kind.ASSIGN) {
						break;
					}
				}
				System.out.println("Line 662"+this.token.getKind());
				return new FunctionCallExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
						firstToken.getText(), name, args);

			} else {
				if (this.token.getKind() == Kind.LSQUARE) {
					consume();
					this.tokenCount = this.tokenCount + 1;
					IExpression exp = checkofExpression();
					if (this.token.getKind() == Kind.RSQUARE) {
						consume();
						this.tokenCount = this.tokenCount + 1;
						return new ListSelectorExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
								firstToken.getText(), name, exp);
					}

				} else {
					return new IdentExpression__(firstToken.getLine(), firstToken.getCharPositionInLine(),
							firstToken.getText(), name);
				}
			}

		}
		default -> {
			throw new SyntaxException(this.token.getText(), this.token.getLine(), this.token.getCharPositionInLine());
		}
		}
		return null;
	}

	public IPLPToken checkofSwitch() throws SyntaxException {
		if (this.token.getKind() == Kind.KW_CASE) {
			checkofCase();
			consume();
			this.tokenCount = this.tokenCount + 1;
			this.branchExpressions.add(checkofExpression());
			;
			checkofColon();
			consume();
			this.tokenCount = this.tokenCount + 1;
				this.blocksExpressions.add(checkforCaseBlock());
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
				LexicalException lexerror = new LexicalException("Error in character insert", 0, 1);
				throw lexerror;
			}
			return this.token;

		} catch (Exception e) {
			LexicalException lexerror = new LexicalException("Error in character insert", 0, 1);
			throw lexerror;
		}
	}

}