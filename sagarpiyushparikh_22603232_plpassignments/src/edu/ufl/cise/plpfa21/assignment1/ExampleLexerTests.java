package edu.ufl.cise.plpfa21.assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExampleLexerTests implements PLPTokenKinds {

	IPLPLexer getLexer(String input) throws LexicalException {
		return CompilerComponentFactory.getLexer(input);
	}

	@Test
	public void test0() throws LexicalException {
		String input = """

				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test1() throws LexicalException {
		String input = """
				abc
				  def
				     ghi

				""";
		IPLPLexer lexer = getLexer(input);

		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String text = token.getText();
			assertEquals(text, "abc");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 2);
			String text = token.getText();
			assertEquals(text, "def");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 3);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 5);
			String text = token.getText();
			assertEquals(text, "ghi");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test2() throws LexicalException {
		String input = """
				a123 123a
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String text = token.getText();
			assertEquals(text, "a123");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 5);
			String text = token.getText();
			assertEquals(text, "123");
			int val = token.getIntValue();
			assertEquals(val, 123);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 8);
			String text = token.getText();
			assertEquals(text, "a");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test3() throws LexicalException {
		String input = """
				= == ===
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String text = token.getText();
			assertEquals(text, "=");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EQUALS);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 2);
			String text = token.getText();
			assertEquals(text, "==");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EQUALS);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 5);
			String text = token.getText();
			assertEquals(text, "==");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 7);
			String text = token.getText();
			assertEquals(text, "=");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test4() throws LexicalException {
		String input = """
				a %
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String text = token.getText();
			assertEquals(text, "a");
		}
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});
	}
	
	@Test
	public void test10() throws LexicalException {
		String input = """
				&&&
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.AND);
		}
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});
	}
	
	@Test
	public void test11() throws LexicalException {
		String input = """
				!&&
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.BANG);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.AND);
		}
		
	}
	
	@Test
	public void test12() throws LexicalException {
		String input = """
				END- END* ENDabc
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_END);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.MINUS);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_END);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.TIMES);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_END);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		
	}


	@Test
	public void test5() throws LexicalException {
		String input = """
				99999999999999999999999999999999999999999999999999999999999999999999999
				""";
		IPLPLexer lexer = getLexer(input);
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});
	}

	@Test
	public void test6() throws LexicalException {
		String input = """
				CASE
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_CASE);
		}
	}

	@Test
	public void test7() throws LexicalException {
		String input = """
				+-
				""";

		IPLPLexer lexer = getLexer(input);

		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.PLUS);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.MINUS);
		}
	}

	@Test
	public void test8() throws LexicalException {
		String input = """
				/* jndknf */
				""";

		IPLPLexer lexer = getLexer(input);

		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}

	}
	

	@Test
	public void test13() throws LexicalException {
		String input = """
				"This is a 
				string""And another
				string"
				""";

		IPLPLexer lexer = getLexer(input);

		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			String text = token.getText();
			assertEquals(text, "\"This is a\n"
					+ "string\"");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			String text = token.getText();
			assertEquals(text, "\"And another\n"
					+ "string\"");
		}

	}
	@Test
	public void test14() throws LexicalException {
		String input = """
				abc = 'abc"def'
				""";

		IPLPLexer lexer = getLexer(input);

		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			String text = token.getText();
			assertEquals(text, "abc");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
			String text = token.getText();
			assertEquals(text, "=");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			String text = token.getText();
			assertEquals(text, "\'abc\"def\'");
		}

	}
	@Test public void test59() throws LexicalException{
		String input = """
		a:INT = 88888888888888888888888888888888888888888888888888888888888;
		""";
		try{
		IPLPLexer lexer = getLexer(input);
		{
		IPLPToken token = lexer.nextToken();
		Kind kind = token.getKind();
		assertEquals(Kind.IDENTIFIER,kind,"at "  + token.getLine() + ":" + token.getCharPositionInLine());
		int line = token.getLine();
		assertEquals(1,line,"line position error for token"+"a");
		int charPositionInLine = token.getCharPositionInLine();
		assertEquals(0,charPositionInLine,"char position error for token"+"a");
		String text = token.getText();
		assertEquals("a",text,"at "  + token.getLine() + ":" + token.getCharPositionInLine());
		}
		{
		IPLPToken token = lexer.nextToken();
		Kind kind = token.getKind();
		assertEquals(Kind.COLON,kind,"at "  + token.getLine() + ":" + token.getCharPositionInLine());
		int line = token.getLine();
		assertEquals(1,line,"line position error for token"+":");
		int charPositionInLine = token.getCharPositionInLine();
		assertEquals(1,charPositionInLine,"char position error for token"+":");
		String text = token.getText();
		assertEquals(":",text,"at "  + token.getLine() + ":" + token.getCharPositionInLine());
		}
		{
		IPLPToken token = lexer.nextToken();
		Kind kind = token.getKind();
		assertEquals(Kind.KW_INT,kind,"at "  + token.getLine() + ":" + token.getCharPositionInLine());
		int line = token.getLine();
		assertEquals(1,line,"line position error for token"+"INT");
		int charPositionInLine = token.getCharPositionInLine();
		assertEquals(2,charPositionInLine,"char position error for token"+"INT");
		String text = token.getText();
		assertEquals("INT",text,"at "  + token.getLine() + ":" + token.getCharPositionInLine());
		}
		{
		IPLPToken token = lexer.nextToken();
		Kind kind = token.getKind();
		assertEquals(Kind.ASSIGN,kind,"at "  + token.getLine() + ":" + token.getCharPositionInLine());
		int line = token.getLine();
		assertEquals(1,line,"line position error for token"+"=");
		int charPositionInLine = token.getCharPositionInLine();
		assertEquals(6,charPositionInLine,"char position error for token"+"=");
		String text = token.getText();
		assertEquals("=",text,"at "  + token.getLine() + ":" + token.getCharPositionInLine());
		}
		assertThrows(LexicalException.class, ()-> {
			IPLPToken token = lexer.nextToken();
			});
		}
			catch(LexicalException e) {
			    System.out.println("Unexpected LexicalException:  " + e.getMessage());
			    System.out.println("input=");
			    System.out.println(input);
			    System.out.println("-----------");
			    throw e;
		    }
			catch(Throwable e) {
				System.out.println(e.getClass() + "  "  + e.getMessage());
				System.out.println("input=");
				System.out.println(input);
				System.out.println("-----------");
			    throw e;
		    }

	}
		


}