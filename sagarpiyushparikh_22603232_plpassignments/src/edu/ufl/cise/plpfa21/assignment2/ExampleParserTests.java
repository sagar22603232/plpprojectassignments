package edu.ufl.cise.plpfa21.assignment2;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.ufl.cise.plpfa21.assignment1.CompilerComponentFactory;


class ExampleParserTests {
	
	static boolean VERBOSE = true;
	
	void noErrorParse(String input)  {
		IPLPParser parser = CompilerComponentFactory.getParser(input);
		try {
			parser.parse();
		} catch (Throwable e) {
			throw new RuntimeException(e); 
		}
	}
	

	private void syntaxErrorParse(String input, int line, int column) {
		IPLPParser parser = CompilerComponentFactory.getParser(input);
		assertThrows(SyntaxException.class, () -> {
			try {
			parser.parse();
			}
			catch(SyntaxException e) {
				if (VERBOSE) System.out.println(input + '\n' + e.getMessage());
				Assertions.assertEquals(line, e.line);
				Assertions.assertEquals(column, e.column);
				throw e;
			}
		});
		
	}

	

	@Test public void test0() {
		String input = """

		""";
		noErrorParse(input);
	}
	

		@Test public void test1() {
		String input = """
		VAL a: INT = 0;
		""";
		noErrorParse(input);
		}


		@Test public void test2() {
		String input = """
		VAL a: STRING = "hello";
		""";
		noErrorParse(input);
		}


		@Test public void test3() {
		String input = """
		VAL b: BOOLEAN = 	TRUE;
		""";
		noErrorParse(input);
		}


		@Test public void test4() {
		String input = """
		VAR b: LIST[];
		""";
		noErrorParse(input);
		}
		@Test public void test6() {
			String input = """
					VAR A:LIST[LIST[INT]];
					""";
			noErrorParse(input);
			}
		@Test public void test16() {
			String input = """
			VAR c = f();
			""";
			noErrorParse(input);
			}
		@Test public void test17() {
			String input = """
			VAL d = ((a+b)/(c+f()));
			""";
			noErrorParse(input);
			}
		@Test public void test18() {
			String input = """
			VAR A:LIST[INT];
			""";
			noErrorParse(input);
			}
		@Test public void test19() {
			String input = """
			VAL a:LIST[INT] = NIL;
			""";
			noErrorParse(input);
			}
		@Test public void test20() {
			String input = """
			FUN f() : LIST[BOOLEAN]
DO
	RETURN NIL;
END
			""";
			noErrorParse(input);
			}
		@Test public void test21() {
			String input = """
			VAR a:LIST[LIST[]];
			""";
			noErrorParse(input);
			}
		
       //This input has a syntax error at line 2, position 19.
		@Test public void test5()  {
		String input = """
		FUN func() DO
		WHILE x>0 DO x=x-1 END
		END
		""";
		syntaxErrorParse(input,2,19);
		}
		//This input has a syntax error at line 2, position 19.
				@Test public void test11()  {
				String input = """
				FUN func() DO
				IF x>0 && y>0 && Z>10
				DO
				x=x+y+z+a;
				END
				END
				""";
				noErrorParse(input);
				}
				//This input has a syntax error at line 2, position 19.
				@Test public void test12()  {
				String input = """
				FUN func(): BOOLEAN DO
				SWITCH condition
				CASE 1: RETURN TRUE;
				CASE Hellow: LET name= "world";
				CASE 2: (work(TRUE,FALSE));
				DEFAULT LET check =num[200];
				END
				IF a>0 &&
				b<0 || z==10
				DO
				a=a*b-20;
				END
				END
				""";
				noErrorParse(input);
				}
				//This input has a syntax error at line 2, position 19.
				@Test public void test13()  {
				String input = """
				FUN func() DO
				SWITCH condition
				CASE 1: RETURN TRUE;
				CASE Hellow: LET name= "world";
				CASE 2: (work(TRUE,FALSE));
				DEFAULT LET check =num[200];
				END
				IF a>0 &&
				b<0 || z==10
				DO
				a=a*b-20;
				END
				RETURN x*y;
				END
				""";
				noErrorParse(input);
				}
		//This input has a syntax error at line 2, position 19.
		@Test public void test8()  {
		String input = """
		FUN a(b C:STRING) DO END
		""";
		syntaxErrorParse(input,1,8);
		}

	       //This input has a syntax error at line 2, position 19.
		@Test public void test9()  {
		String input = """
		FUN g():INT DO RETURN 1; END
FUN f()
DO
   RETURN g();
   END
		""";
		noErrorParse(input);
		}
		


		//This input has a syntax error at line 2, position 19.
		@Test public void test10()  {
		String input = """
		FUN func() DO
		IF x>0 || y!=0 && Z>10
		DO
		x=100;
		y=20/30-6;
		VAR a;
		END
		END
		""";
		syntaxErrorParse(input,6,0);
		}

	

}
