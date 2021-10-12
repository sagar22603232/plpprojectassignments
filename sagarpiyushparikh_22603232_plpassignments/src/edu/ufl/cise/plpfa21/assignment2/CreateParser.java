package edu.ufl.cise.plpfa21.assignment2;
import edu.ufl.cise.plpfa21.assignment1.CreateToken;
import  edu.ufl.cise.plpfa21.assignment3.ast.*;
import edu.ufl.cise.plpfa21.assignment1.IPLPToken;
import edu.ufl.cise.plpfa21.assignment2.ValidateParser;
import edu.ufl.cise.plpfa21.assignment1.CreateLexer;
import java.util.ArrayList;
import edu.ufl.cise.plpfa21.assignment2.IPLPParser;
import  edu.ufl.cise.plpfa21.assignment3.ast.IASTNode;
public class CreateParser extends ValidateParser implements IPLPParser {




	String message;
	ArrayList<CreateToken> tokens;
	public CreateParser(ArrayList<CreateToken> tokens, String message) {
		super(tokens);
		this.tokens = tokens;
		this.message = message;
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public IASTNode parse() throws Exception {
		// TODO Auto-generated method stub
		try {
			ValidateParser checkParser = new ValidateParser(this.tokens);
			return checkParser.validateSingleParse(this.message);
			
		}
		catch(Exception e) {
			throw e;
	
	}
		
	}

}
