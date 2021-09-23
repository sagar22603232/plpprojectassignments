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
		StringLiteral("stringliteral"), 
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
						indexPosition = removeSpaces(indexPosition, inputLength, input);
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

							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case '\n', '\r' -> {

							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							linecount = linecount + 1;
							linePostion = linePostion + 1;

						}
						case '+' -> {
							tokens.add(new CreateToken(Kind.PLUS, indexPosition, inputLength, linePostion,
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case '-' -> {
							tokens.add(new CreateToken(Kind.MINUS, indexPosition, inputLength, linePostion,
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case '/' -> {
							charposition = indexPosition;
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
							state = state.Comment;

						}
						case '*' -> {
							tokens.add(new CreateToken(Kind.TIMES, indexPosition, inputLength, linePostion,
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case '(' -> {
							tokens.add(new CreateToken(Kind.LPAREN, indexPosition, inputLength, linePostion,
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case ')' -> {
							tokens.add(new CreateToken(Kind.RPAREN, indexPosition, inputLength, linePostion,
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case ';' -> {
							tokens.add(new CreateToken(Kind.SEMI, indexPosition, inputLength, linePostion,
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case ',' -> {
							tokens.add(new CreateToken(Kind.COMMA, indexPosition, inputLength, linePostion,
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case ':' -> {
							tokens.add(new CreateToken(Kind.COLON, indexPosition, inputLength, linePostion,
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
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
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case ']' -> {
							tokens.add(new CreateToken(Kind.RSQUARE, indexPosition, inputLength, linePostion,
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case '<' -> {
							tokens.add(new CreateToken(Kind.LT, indexPosition, inputLength, linePostion,
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case '>' -> {
							tokens.add(new CreateToken(Kind.GT, indexPosition, inputLength, linePostion,
									startLinePostion, input.charAt(indexPosition), input.charAt(indexPosition), albha));
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						case '\'', '\"' -> {
							state = State.StringLiteral;

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
							charposition = indexPosition;
							indexPosition = indexPosition + 1;
							startLinePostion = startLinePostion + 1;
						}
						default -> {

							if (Character.isJavaIdentifierStart(input.charAt(indexPosition))) {
								chdict.append(input.charAt(indexPosition));
								state = state.Identifier;
								charposition = indexPosition;

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
								if (Character.isWhitespace(givenInput.charAt(charposition))
										|| givenInput.charAt(charposition) == '\n'
										|| givenInput.charAt(charposition) == '\r') {
									break;

								} else {

									albha = albha + Character.toString(givenInput.charAt(charposition));
									charposition = charposition + 1;

								}

							}
							if (keyWords.containsKey(albha)) {
								indexPosition = charposition;
								tokens.add(new CreateToken(keyWords.get(albha), indexPosition, inputLength, linePostion,
										spacecount, input.charAt(charposition), input.charAt(charposition), albha));
							} else {
								indexPosition = charposition;
								tokens.add(new CreateToken(Kind.IDENTIFIER, indexPosition, inputLength, linePostion,
										spacecount, input.charAt(charposition), input.charAt(charposition), albha));
							}

						}
						albha = "";
						startLinePostion = startLinePostion + 1;
						charcount = charposition;
						linecount = 0;
						state = State.START;
						chdict.setLength(0);
					}
				}
				case IntLiteral -> {
					try {
						spacecount = charposition;
						startLinePostion = charposition;

						while (charposition <= givenInput.length()) {
							if (Character.isWhitespace(givenInput.charAt(charposition))
									|| givenInput.charAt(charposition) == '\n'
									|| givenInput.charAt(charposition) == '\r'
									|| Character.isLetter(givenInput.charAt(charposition))) {
								break;

							} else {
								if (Character.isDigit(givenInput.charAt(charposition))) {

									albha = albha + Character.toString(givenInput.charAt(charposition));
									charposition = charposition + 1;
								} else {
									break;
								}
							}

						}
						indexPosition = charposition;
						int valuedigit = Integer.parseInt(albha);
						if (valuedigit < Integer.MAX_VALUE) {
							tokens.add(new CreateToken(Kind.INT_LITERAL, indexPosition, inputLength, linePostion,
									spacecount, input.charAt(charposition), '1', albha));
						}

						albha = "";
						startLinePostion = startLinePostion + 1;
						charcount = charposition;
						linecount = 0;
						state = State.START;
						chdict.setLength(0);

					} catch (Exception e) {
						tokens.add(new CreateToken(Kind.ERROR, indexPosition, inputLength, linePostion,
								startLinePostion, charIndex, charIndex, albha));
						indexPosition = indexPosition + 1;
						break;

					}
				}
				case NotEqual -> {
					if (((input.charAt(indexPosition)) == '=')) {

						tokens.add(new CreateToken(Kind.NOT_EQUALS, indexPosition, inputLength, linePostion,
								charposition, input.charAt(indexPosition), input.charAt(indexPosition), "!="));
						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;
						state = State.START;
					} else {

						tokens.add(new CreateToken(Kind.BANG, indexPosition, inputLength, linePostion, charposition,
								'!', '!', "!"));
						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;
						state = State.START;
					}
				}
				case HaveEqual -> {
					if (((input.charAt(indexPosition)) == '=')) {

						tokens.add(new CreateToken(Kind.EQUALS, indexPosition, inputLength, linePostion, charposition,
								input.charAt(indexPosition), input.charAt(indexPosition), "=="));
						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;
						state = State.START;
					} else {

						tokens.add(new CreateToken(Kind.ASSIGN, indexPosition, inputLength, linePostion, charposition,
								'=', '=', albha));
						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;
						state = State.START;
					}
				}
				case AndLiteral -> {
					if (((input.charAt(indexPosition)) == '&')) {
						tokens.add(new CreateToken(Kind.AND, indexPosition, inputLength, linePostion, charposition,
								input.charAt(indexPosition), input.charAt(indexPosition), "&&"));
						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;
						state = State.START;

					} else {
						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;
						state = State.START;
					}
				}
				case OrLiteral -> {
					if (((input.charAt(indexPosition)) == '|')) {
						tokens.add(new CreateToken(Kind.OR, indexPosition, inputLength, linePostion, charposition,
								input.charAt(indexPosition), input.charAt(indexPosition), "||"));
						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;
						state = State.START;

					} else {
						indexPosition = indexPosition + 1;
						startLinePostion = startLinePostion + 1;
						state = State.START;
					}
				}
				case StringLiteral -> {
					while (charposition <= givenInput.length()) {
						if (givenInput.charAt(charposition) == '\'' || givenInput.charAt(charposition) == '\"') {
							break;
						} else {
							albha = albha + Character.toString(givenInput.charAt(charposition));
							charposition = charposition + 1;
						}
					}
					indexPosition = charposition;
					tokens.add(new CreateToken(Kind.STRING_LITERAL, indexPosition, inputLength, linePostion, spacecount,
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
						indexPosition = indexPosition + 1;
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
