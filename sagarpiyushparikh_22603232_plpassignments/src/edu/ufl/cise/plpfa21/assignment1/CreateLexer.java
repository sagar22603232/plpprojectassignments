package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

/* @CreateLexer this class implements interface IPLPToken
 * this class is used to create lexer token list
 * */
public class CreateLexer extends CreateToken implements IPLPToken {

	static ArrayList<CreateToken> tokens;
	IPLPLexer lexer;
	CreateToken singletoken;
	static HashMap<String, Kind> keyWords = new HashMap<String, Kind>();
	int tokenCount;
 int charinLineCount = 0;
	CreateLexer(String input) {
		super(input);
		tokens = new ArrayList<CreateToken>();
		keyWords.put("VAL", kind.KW_VAL);
		keyWords.put("VAR", kind.KW_VAR);
		keyWords.put("FUN", kind.KW_FUN);
		keyWords.put("DO", kind.KW_DO);
		keyWords.put("END", kind.KW_END);
		keyWords.put("LET", kind.KW_LET);
		keyWords.put("SWITCH", kind.KW_SWITCH);
		keyWords.put("CASE", kind.KW_CASE);
		keyWords.put("DEFAULT", kind.KW_DEFAULT);
		keyWords.put("IF", kind.KW_IF);
		keyWords.put("WHILE", kind.KW_WHILE);
		keyWords.put("RETURN", kind.KW_RETURN);
		keyWords.put("NIL", kind.KW_NIL);
		keyWords.put("TRUE", kind.KW_TRUE);
		keyWords.put("FALSE", kind.KW_FALSE);
		keyWords.put("INT", kind.KW_INT);
		keyWords.put("STRING", kind.KW_STRING);
		keyWords.put("BOOLEAN", kind.KW_BOOLEAN);
		keyWords.put("LIST", kind.KW_LIST);
		// TODO Auto-generated constructor stub
	}

	/*
	 * This @State Function is used to define states of DFA
	 */

	public static enum State {
		START("start"),
		KEYWORD("keyword"),
		Identifier("identifier"),
		IntLiteral("intliteral"),
		StringLiteralForSingleQuote("stringliteralforsinglequote"),
		StringLiteralForDoubleQuote("stringliteralfordoublequote"),
		EscapeSeq("escapeseq"),
		Comment("comment"),
		AndLiteral("andliteral"),
		OrLiteral("orliteral"), 
		NotEqual("noteequal"), 
		HaveEqual("haveequal"), 
		WhiteSpace("whitespace"),
		Start_Comment("startcomment");

		State(String stateName) {
			this.stateName = stateName;
		}

		String stateName;

		public String getStateName() {
			return stateName;
		}
	}

	/*
	 * This return type int @removeSpaces Function is used to remove spaces
	 * 
	 * @indexPostion position of current index
	 * 
	 * @inputLength length of string
	 * 
	 * @input string provided by user
	 */
	public static int removeSpaces(int indexPostion, int inputLength, String input) {
		while (Character.isWhitespace(input.charAt(indexPostion))) {
			if (input.charAt(indexPostion) == '\r' || input.charAt(indexPostion) == '\n') {
				return indexPostion;
			} else {
				indexPostion = indexPostion + 1;
				if (indexPostion == inputLength) {
					return indexPostion;
				}
			}
		}
		return indexPostion;
	}

	/*
	 * This @CreateLexerTokens Function is used to create tokenlist of lexer tokens
	 * 
	 * @input string passed from junit testcases
	 */

	public ArrayList<CreateToken> CreateLexerTokens(String input) throws LexicalException {

		int inputLength = input.length();
		String givenInput = input;
		int indexPosition = 0;
		char charIndex = 0;
		int linePostion = 1;
		int startLinePostion = 0;
		int charcount = 0;
		int spacecount = 0;
		int linecount = 0;
		char EOFchar = 0;
		int valuedigit  = 0;
		int inLinechar = 0;
		int charatpostion = 0;
		String albha = "";
		int charposition = 0;
		StringBuilder chdict = new StringBuilder();
		State state = State.START;

		try {
			
			while (indexPosition <= inputLength) {

				char singleChar = input.charAt(indexPosition);

				if (indexPosition < inputLength) {
					charIndex = input.charAt(indexPosition);
				} else {
					charIndex = EOFchar;
				}

				switch (state) {

				case START -> {

					if (indexPosition < inputLength) {
						//indexPosition = removeSpaces(indexPosition, inputLength, input);
						if (indexPosition < inputLength) {
							charIndex = input.charAt(indexPosition);
						} else {
							charIndex = EOFchar;
						}

						switch (charIndex) {

						case 0 -> {

							tokens.add(new CreateToken(Kind.EOF, indexPosition, inputLength, linePostion,
									startLinePostion, charIndex, charIndex, albha));
							indexPosition = indexPosition + 1;
							break;
						}
						case ' ', '\t' -> {
							inLinechar = inLinechar +1;
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case '\n', '\r' -> {

							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							linecount = linecount + 1;
							linePostion = linePostion + 1;
							inLinechar = 0;

						}
						case '+' -> {
							tokens.add(new CreateToken(Kind.PLUS, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar +1;
						}
						case '-' -> {
							tokens.add(new CreateToken(Kind.MINUS, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar +1;
						}
						case '/' -> {
							charposition = indexPosition;
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							state = state.Comment;

						}
						case '*' -> {
							tokens.add(new CreateToken(Kind.TIMES, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar +1;
						}
						case '(' -> {
							tokens.add(new CreateToken(Kind.LPAREN, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar + 1;
						}
						case ')' -> {
							tokens.add(new CreateToken(Kind.RPAREN, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar + 1;
						}
						case ';' -> {
							tokens.add(new CreateToken(Kind.SEMI, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar + 1;
						}
						case ',' -> {
							tokens.add(new CreateToken(Kind.COMMA, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar + 1;
						}
						case ':' -> {
							tokens.add(new CreateToken(Kind.COLON, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar + 1;
						}
						case '&' -> {
							state = State.AndLiteral;
							charposition = indexPosition;
							indexPosition = indexPosition + 1;
							
						}
						case '|' -> {
							state = State.OrLiteral;
							charposition = indexPosition;
							indexPosition = indexPosition + 1;
						}
						case '[' -> {
							tokens.add(new CreateToken(Kind.LSQUARE, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar + 1;
						}
						case ']' -> {
							tokens.add(new CreateToken(Kind.RSQUARE, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar + 1;
						}
						case '<' -> {
							tokens.add(new CreateToken(Kind.LT, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar + 1;
						}
						case '>' -> {
							tokens.add(new CreateToken(Kind.GT, indexPosition, inputLength, linePostion,
									inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							inLinechar = inLinechar + 1;
						}
						case '\'' -> {
							state = State.StringLiteralForSingleQuote;
							albha = "\'";
							indexPosition = indexPosition + 1;
							charposition = indexPosition;
							startLinePostion = startLinePostion + 1;
						}
						case '\"' ->{
							state = State.StringLiteralForDoubleQuote;
							albha = "\"";
							indexPosition = indexPosition + 1;
							charposition = indexPosition;
							startLinePostion = startLinePostion + 1;
						}
						case '!' -> {
							state = State.NotEqual;
							charposition = indexPosition;
							indexPosition = indexPosition + 1;

							// startLinePostion = startLinePostion + 1;
						}
						case '=' -> {
							state = State.HaveEqual;
							charposition = indexPosition;
							indexPosition = indexPosition + 1;

							// startLinePostion = startLinePostion + 1;
						}
						case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
							state = State.IntLiteral;
							charatpostion = inLinechar;
							charposition = indexPosition;
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							//inLinechar = inLinechar +1;
						}
						default -> {

							if (Character.isJavaIdentifierStart(input.charAt(indexPosition))) {
								chdict.append(input.charAt(indexPosition));
					            
								state = state.Identifier;
								charatpostion = inLinechar;
								charposition = indexPosition;
								//inLinechar = inLinechar +1;
								

							} else {

								tokens.add(new CreateToken(Kind.ERROR, indexPosition, inputLength, linePostion,
										startLinePostion, charIndex, charIndex, albha));

								indexPosition = indexPosition + 1;
								break;

							}
						}

						}
					}

				}
				case Identifier -> {

					if (Character.isJavaIdentifierStart(input.charAt(indexPosition))) {
						chdict.append(input.charAt(indexPosition));

						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;

					} else {

						if (keyWords.containsKey(chdict.toString())) {

							tokens.add(new CreateToken(keyWords.get(chdict.toString()), indexPosition, inputLength,
									linePostion, startLinePostion, charIndex, charIndex, albha));

						} else {

							if (linecount > 0) {
								spacecount = charposition - charcount - linecount;
							} else {
								spacecount = charposition;
							}

							startLinePostion = charposition;

							while (charposition <= givenInput.length()) {
								if(Character.isWhitespace(givenInput.charAt(charposition))){
									break;
								}else {
									if ( givenInput.charAt(charposition) == '\n'
											|| givenInput.charAt(charposition) == '\r'
											|| givenInput.charAt(charposition) == ':'
											|| givenInput.charAt(charposition) == '['
											|| givenInput.charAt(charposition) == ']'
											|| givenInput.charAt(charposition) == ';'
											|| givenInput.charAt(charposition) == '+'
											|| givenInput.charAt(charposition) == '-'
											|| givenInput.charAt(charposition) == '('
											|| givenInput.charAt(charposition) == ')'
											|| givenInput.charAt(charposition) == '>'
											|| givenInput.charAt(charposition) == '<'
											|| givenInput.charAt(charposition) == '='
											|| givenInput.charAt(charposition) == '!'
											|| givenInput.charAt(charposition) == '|'
											|| givenInput.charAt(charposition) == '&'
											|| givenInput.charAt(charposition) == ','
											|| givenInput.charAt(charposition) == '*'
											|| givenInput.charAt(charposition) == '/') {
									
										break;

									} 
									if (keyWords.containsKey(albha)) {
										break;
									}
									else {

										albha = albha + Character.toString(givenInput.charAt(charposition));
										charposition = charposition + 1;
										inLinechar = inLinechar + 1;
									}	
								}
							}
							if (keyWords.containsKey(albha)) {
								indexPosition = charposition;
								tokens.add(new CreateToken(keyWords.get(albha), indexPosition, inputLength, linePostion,
										charatpostion, input.charAt(charposition), input.charAt(charposition), albha));
							} else {
								indexPosition = charposition;
								tokens.add(new CreateToken(Kind.IDENTIFIER, indexPosition, inputLength, linePostion,
										charatpostion, input.charAt(charposition), input.charAt(charposition), albha));
							}

						}
						albha = "";
						startLinePostion = startLinePostion + 1;
						charcount = charposition;
						state = State.START;
						chdict.setLength(0);
					}
				}
				case IntLiteral -> {
					try {
						spacecount = charposition;
						startLinePostion = charposition;

						while (charposition <= givenInput.length()) {
							if(Character.isWhitespace(givenInput.charAt(charposition))){
								
								break;
							}else {
								if (
										givenInput.charAt(charposition) == '\n'
										|| givenInput.charAt(charposition) == '\r' || givenInput.charAt(charposition) == ':'
										|| givenInput.charAt(charposition) == '[' || givenInput.charAt(charposition) == ']'
										|| givenInput.charAt(charposition) == ';' || givenInput.charAt(charposition) == '+'
										|| givenInput.charAt(charposition) == '-' || givenInput.charAt(charposition) == '('
										|| givenInput.charAt(charposition) == ')' || givenInput.charAt(charposition) == '>'
										|| givenInput.charAt(charposition) == '<' || givenInput.charAt(charposition) == '='
										|| givenInput.charAt(charposition) == '!' || givenInput.charAt(charposition) == '|'
										|| givenInput.charAt(charposition) == '&' || givenInput.charAt(charposition) == ','
										|| givenInput.charAt(charposition) == '*' || givenInput.charAt(charposition) == '/'
										|| Character.isLetter(givenInput.charAt(charposition))) {
									
									break;

								} else {
									if (Character.isDigit(givenInput.charAt(charposition))) {

										albha = albha + Character.toString(givenInput.charAt(charposition));
										valuedigit = Integer.parseInt(albha);
										charposition = charposition + 1;
										inLinechar = inLinechar +1;
									} else {
										break;
									}
								}	
							}

						}
						indexPosition = charposition;
						if (valuedigit < Integer.MAX_VALUE) {
							tokens.add(new CreateToken(Kind.INT_LITERAL, indexPosition, inputLength, linePostion,
									charatpostion, input.charAt(charposition), '1', albha));
						}

						albha = "";
						startLinePostion = startLinePostion + 1;
						charcount = charposition;
						state = State.START;
						chdict.setLength(0);

					} catch (Exception e) {
						tokens.add(new CreateToken(Kind.ERROR, indexPosition, inputLength, linePostion,
								startLinePostion, charIndex, charIndex, albha));
						
						indexPosition = inputLength;
						break;

					}
				}
				case NotEqual -> {
					if (((input.charAt(indexPosition)) == '=')) {

						tokens.add(new CreateToken(Kind.NOT_EQUALS, indexPosition, inputLength, linePostion,
								inLinechar, input.charAt(indexPosition), input.charAt(indexPosition), "!="));
						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;
						inLinechar = inLinechar + 2;
						state = State.START;
					} else {

						tokens.add(new CreateToken(Kind.BANG, indexPosition, inputLength, linePostion, inLinechar,
								'!', '!', "!"));
						inLinechar = inLinechar + 1;
						state = State.START;
					}
				}
				case HaveEqual -> {
					if (((input.charAt(indexPosition)) == '=')) {

						tokens.add(new CreateToken(Kind.EQUALS, indexPosition, inputLength, linePostion, inLinechar,
								input.charAt(indexPosition), input.charAt(indexPosition), "=="));
						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;
						inLinechar = inLinechar + 2;
						state = State.START;
					} else {
						tokens.add(new CreateToken(Kind.ASSIGN, indexPosition, inputLength, linePostion, inLinechar,
								'=', '=', albha));
						startLinePostion = startLinePostion + 1;
						inLinechar = inLinechar + 1;
						state = State.START;
					}
				}
				case AndLiteral -> {
					if (((input.charAt(indexPosition)) == '&')) {
						tokens.add(new CreateToken(Kind.AND, indexPosition, inputLength, linePostion, inLinechar,
								input.charAt(indexPosition), input.charAt(indexPosition), "&&"));
						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;
						inLinechar = inLinechar + 2;
						state = State.START;

					} else {
						tokens.add(new CreateToken(Kind.ERROR, indexPosition, inputLength, linePostion,
								startLinePostion, charIndex, charIndex, albha));
						indexPosition = indexPosition + 1;
						break;
					}
				}
				case OrLiteral -> {
					if (((input.charAt(indexPosition)) == '|')) {
						tokens.add(new CreateToken(Kind.OR, indexPosition, inputLength, linePostion, inLinechar,
								input.charAt(indexPosition), input.charAt(indexPosition), "||"));
						indexPosition = indexPosition + 1;
						inLinechar = inLinechar + 2;
						startLinePostion = startLinePostion + 1;
						state = State.START;

					} else {
						
						startLinePostion = startLinePostion + 1;
						state = State.START;
					}
				}
				case StringLiteralForSingleQuote -> {
					while (charposition <= givenInput.length()) {
						if(givenInput.charAt(charposition) == '\n' || givenInput.charAt(charposition) == '\r') {
							
							albha = albha +'\n';
							charposition = charposition + 1;
							inLinechar =  inLinechar  + 1;
						}
						if(givenInput.charAt(charposition) == '\t') {
							albha = albha +'\t';
							charposition = charposition + 1;
							inLinechar =  inLinechar  + 1;
						}
						if (givenInput.charAt(charposition) == '\'') {
							break;
						} else {
							albha = albha + Character.toString(givenInput.charAt(charposition));
							charposition = charposition + 1;
							inLinechar =  inLinechar  + 1;
						}
						
					}
					
					indexPosition = charposition+1;
					albha = albha + '\'';
					tokens.add(new CreateToken(Kind.STRING_LITERAL, indexPosition, inputLength, linePostion, inLinechar,
							input.charAt(charposition), input.charAt(charposition), albha));
					albha = "";
					startLinePostion = startLinePostion + 1;
					charcount = charposition;
					linecount = 0;
					state = State.START;
					chdict.setLength(0);
				}
				case StringLiteralForDoubleQuote -> {
					while (charposition <= givenInput.length()) {
						if(givenInput.charAt(charposition) == '\n' || givenInput.charAt(charposition) == '\r') {
							
							albha = albha +'\n';
							charposition = charposition + 1;
							inLinechar =  inLinechar  + 1;
						}
						if(givenInput.charAt(charposition) == '\t') {
							albha = albha +'\t';
							charposition = charposition + 1;
							inLinechar =  inLinechar  + 1;
						}
						if (givenInput.charAt(charposition) == '\"') {
							break;
						} else {
							albha = albha + Character.toString(givenInput.charAt(charposition));
							charposition = charposition + 1;
							inLinechar =  inLinechar  + 1;
						}
						
					}
					indexPosition = charposition+1;
					albha = albha + '\"';
					tokens.add(new CreateToken(Kind.STRING_LITERAL, indexPosition, inputLength, linePostion, inLinechar,
							input.charAt(charposition), input.charAt(charposition), albha));
					albha = "";
					startLinePostion = startLinePostion + 1;
					charcount = charposition;
					linecount = 0;
					state = State.START;
					chdict.setLength(0);
				}
				case Comment -> {
					if (input.charAt(indexPosition) == '*') {
						state = state.Start_Comment;
					} else {
						tokens.add(new CreateToken(Kind.DIV, indexPosition, inputLength, linePostion, startLinePostion,
								input.charAt(indexPosition), input.charAt(indexPosition), albha));
						state = State.START;
					}
				}
				case Start_Comment -> {
					if (input.charAt(indexPosition) == '*') {
						indexPosition = indexPosition + 1;
						while (indexPosition <= givenInput.length()) {
							if (input.charAt(indexPosition) == '*') {
								break;
							} else {
								indexPosition = indexPosition + 1;
							}

						}
						indexPosition = indexPosition + 1;
						if (input.charAt(indexPosition) == '/') {
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							state = State.START;
						}
					} else {
						tokens.add(new CreateToken(Kind.DIV, indexPosition, inputLength, linePostion, startLinePostion,
								input.charAt(charposition), input.charAt(charposition), albha));
						
						startLinePostion = startLinePostion + 1;
						state = State.START;

					}
				}
				}
			}
		} catch (StringIndexOutOfBoundsException e) {
			tokens.add(new CreateToken(Kind.EOF, indexPosition, inputLength, linePostion, startLinePostion, charIndex,
					charIndex, albha));

		}
		return tokens;

	}

}