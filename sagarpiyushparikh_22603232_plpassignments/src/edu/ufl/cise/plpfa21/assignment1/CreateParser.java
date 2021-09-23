package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import edu.ufl.cise.plpfa21.assignment2.IPLPParser;

public class CreateParser extends ValidateParser implements IPLPParser {




	String message;
	ArrayList<CreateToken> tokens;
	CreateParser(ArrayList<CreateToken> tokens, String message) {
		super(tokens);
		this.tokens = tokens;
		this.message = message;
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void parse() throws Exception {
		// TODO Auto-generated method stub
		try {
			ValidateParser checkParser = new ValidateParser(this.tokens);
			checkParser.validateSingleParse(this.message);
			
		}
		catch(Exception e) {
			throw e;
	
	}
		
	}

}
