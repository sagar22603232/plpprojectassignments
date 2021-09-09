package edu.ufl.cise.plpfa21.assignment1;

public class CreateToken implements IPLPToken{
Kind kind;
int position_Index = 0;
int input_Length = 0;
int input_Line = 1;
int posInLine = 1;
String input;
CreateToken(String input){
	this.input = input;
	this.input_Length = input.length();
}
CreateToken(Kind kind,int position_Index, int input_Length,int input_Line, int posInLine){
	this.kind = kind;
	this.position_Index = position_Index;
	this.input_Length = input_Length;
	this.input_Line = input_Line;
	this.posInLine = posInLine;
}

	@Override
	public Kind getKind() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		if(this.input.substring(position_Index, position_Index+input_Length).length() > 0) {
			return this.input.substring(position_Index, position_Index+input_Length);
		}
		return null;
	}

	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCharPositionInLine() {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public String getStringValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntValue() {
		// TODO Auto-generated method stub
		return 0;
	}
}
