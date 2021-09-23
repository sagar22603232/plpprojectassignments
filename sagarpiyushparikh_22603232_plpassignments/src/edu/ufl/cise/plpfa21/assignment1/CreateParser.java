package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import edu.ufl.cise.plpfa21.assignment2.IPLPParser;

public class CreateParser extends ValidateParser implements IPLPParser {
	String message;
	static ArrayList<CreateToken> tokens;
	IPLPParser parse;
	ValidateParser checkParser = new ValidateParser();
	CreateParser(String message){
		this.message = message;
	}
	
	@Override
	public void parse() throws Exception {
		// TODO Auto-generated method stub
		try {
			CreateLexer lexer = new CreateLexer(this.message);
			tokens = lexer.CreateLexerTokens(this.message);
			for(CreateToken t: tokens) {
				checkParser.validateSingleParse(t);
			}
		}
		catch(Exception e) {
			throw e;
	
	}
		
	}

}
