package edu.ufl.cise.plpfa21.assignment1;

public class CreateToken  implements IPLPToken {
Kind kind;
int position_Index = 0;
int input_Length = 0;
int input_Line = 1;
int posInLine = 1;
String text;
String value;
String input;
CreateToken(String input){
	this.input = input;
	this.input_Length = input.length();
}
public CreateToken (Kind kind,int position_Index, int input_Length,int input_Line, int posInLine, char inputChar, char numChar){
	this.kind = kind;
	this.position_Index = position_Index;
	this.input_Length = input_Length;
	this.input_Line = input_Line;
	this.posInLine = posInLine;
	this.text = Character.toString(inputChar);
	this.value = Character.toString(numChar);
	System.out.println("line 24 token");
	System.out.println(this.text);
}


	@Override
	public Kind getKind() {
		// TODO Auto-generated method stub
		System.out.println(this.kind);
		return this.kind;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		if(this.input != null) {
			if(this.input.substring(position_Index, position_Index+input_Length).length() > 0) {
				return this.input.substring(position_Index, position_Index+input_Length);
			}	
		}
		return null;
	}

	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return this.input_Line;
	}

	@Override
	public int getCharPositionInLine() {
		// TODO Auto-generated method stub
		
		return this.posInLine;
	}

	@Override
	public String getStringValue() {
		// TODO Auto-generated method stub
		System.out.println("text");
		System.out.println(this.text);
		return this.text;
	}

	@Override
	public int getIntValue() {
		// TODO Auto-generated method stub
		System.out.println(this.value);
		return Integer.parseInt(this.value);
	}
}
