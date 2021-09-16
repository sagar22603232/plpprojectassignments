package edu.ufl.cise.plpfa21.assignment1;
/* @CreateToken this class implements interface IPLPToken
 * this class is used to create single tokens
 * */
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
/* @CreateToken this function implements
 * @kind specify the kind of token
 * @position_Index index of character
 * @input_Length length of input
 * @posInLine getCharPositionInLine of token
 * @inputChar input character
 * @numChar input number character
 * @albha token text
 * this class is used to create single tokens
 * */
public CreateToken (Kind kind,int position_Index, int input_Length,int input_Line, int posInLine, char inputChar, char numChar,  String albha){
	this.kind = kind;
	this.position_Index = position_Index;
	this.input_Length = input_Length;
	this.input_Line = input_Line;
	this.posInLine = posInLine;
	if(albha.length() > 0) {
		this.text = albha;
	}
	else {
		this.text = Character.toString(inputChar);
	}
	
	this.value = Character.toString(numChar);

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
		return this.text;
	
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

		return this.text;
	}

	@Override
	public int getIntValue() {
		// TODO Auto-generated method stub
		if(this.value == "1") {
			return Integer.parseInt(this.text);
		}
		else {
			return Integer.parseInt(this.text);
		}
		
	}
}
