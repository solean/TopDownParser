package code;

public class TinyPL {
		
	public static void main(String args[]) {
		   Lexer.lex();
		   new Program();  
	}
}

class Program {
	Decls decls;
	Stmts stmts;
	 
	 public Program() {
		 if (Lexer.nextToken == Token.KEY_BEGIN) {
			 Lexer.lex();

			 decls = new Decls();
			 stmts = new Stmts();
			 
			 if (Lexer.nextToken == Token.KEY_END){
				 System.out.println("Program done, parsed.");
			 } else {
				 System.out.println("Forgot 'end' at end of program.");
			 }
		 } else {
			 System.out.println("Error: can't start program without 'begin' at beginning.");
		 }
	 }
}

class Decls {
	Idlist ints;
	Idlist reals;
	Idlist bools;
	
	public Decls() {
		if (Lexer.nextToken == Token.KEY_INT) {
			Lexer.lex();
			ints = new Idlist();
			if (Lexer.nextToken == Token.SEMICOLON) {
				Lexer.lex();
			} else {
				System.out.println("Decls error: missing semicolon at end.");
			}
		}
		if (Lexer.nextToken == Token.KEY_REAL) {
			Lexer.lex();
			reals = new Idlist();
			if (Lexer.nextToken == Token.SEMICOLON) {
				Lexer.lex();
			} else {
				System.out.println("Decls error: missing semicolon at end.");
			}
		}
		if (Lexer.nextToken == Token.KEY_BOOL) {
			Lexer.lex();
			bools = new Idlist();
			if (Lexer.nextToken == Token.SEMICOLON) {
				Lexer.lex();
			} else {
				System.out.println("Decls error: missing semicolon at end.");
			}
		}
	}
	 
}

class Idlist {
	 
	 public Idlist() {
		 new Id_Lit();
		 
		 while (Lexer.nextToken == Token.COMMA) {
			 Lexer.lex();
			 if (Lexer.nextToken == Token.ID) {
				 new Id_Lit();
			 } else {
				 System.out.println("Error in Idlist: expecting ID after comma.");
			 }
		 }
	 }
}

class Stmts {
	Stmt stmt;
	Stmts stmts;
	
	public Stmts() {
		if (Lexer.nextToken != Token.KEY_END) {
			stmt = new Stmt();
			if ((Lexer.nextToken != Token.KEY_END) && (Lexer.nextToken != Token.RIGHT_BRACE)) {
				stmts = new Stmts();
			}
		}
	}
}

class Stmt {
	Assign assign;
	Cond cond;
	Loop loop;
	Cmpd cmpd;
	
	public Stmt() {
		switch(Lexer.nextToken) {
			case Token.ID:
				assign = new Assign();
				break;
			case Token.KEY_IF:
				cond = new Cond();
				break;
			case Token.KEY_WHILE:
				loop = new Loop();
				break;
			case Token.LEFT_BRACE:
				cmpd = new Cmpd();
				break;
			default:
				break;
		}
	}
	 
}

class Var {
	Id_Lit id;
	
	public Var() {
		id = new Id_Lit();
	}
}

class Assign {
	Var var;
	Expr expr;

	 public Assign() {
		 var = new Var();
		 
		 if (Lexer.nextToken == Token.ASSIGN_OP) {
			 Lexer.lex();
			 expr = new Expr();
			 
			 if (Lexer.nextToken == Token.SEMICOLON) {
				 Lexer.lex();
			 } else {
				 System.out.println("Semicolon error in assign.");
			 }
		 } else {
			 System.out.println("Equal-op error in assign.");
		 }
	 }
}

class Cond  {
	Expr expr;
	Stmt stmt;
	Stmt stmt2;
	
	public Cond() {
		Lexer.lex();
		if (Lexer.nextToken == Token.LEFT_PAREN) {
			expr = new Expr();
			stmt = new Stmt();
			
			if (Lexer.nextToken == Token.KEY_ELSE) {
				Lexer.lex();
				stmt2 = new Stmt();
			}
		} else {
			System.out.println("Error in cond, no left paren");
		}
	}
}

class Loop {
	Expr expr;
	Stmt stmt;

	public Loop() {
		if (Lexer.nextToken == Token.KEY_WHILE) {
			Lexer.lex();
			if (Lexer.nextToken == Token.LEFT_PAREN) {
				expr = new Expr();
				stmt = new Stmt();
			} else {
				System.out.println("Left paren error in loop.");
			}
		} else {
			System.out.println("Error in loop, 'while' missing.");
		}
	}
}

class Cmpd  {
	Stmts stmts;
	 
	 public Cmpd() {
		 if (Lexer.nextToken == Token.LEFT_BRACE) {
			 Lexer.lex();
			 stmts = new Stmts();
			 
			 if (Lexer.nextToken == Token.RIGHT_BRACE) {
				 Lexer.lex();
			 } else {
				 System.out.println("Right brace error in cmpd.");
			 }
		 } else {
			 System.out.println("Left brace error in cmpd.");
		 }
	 }
}


class Expr {
	char op = ' ';
	Term term;
	Expr expr;
	 
	public Expr() {
		term = new Term();
		
		if (Lexer.nextToken == Token.ADD_OP || Lexer.nextToken == Token.SUB_OP || Lexer.nextToken == Token.OR_OP) {
			op = Lexer.ch;
			Lexer.lex();
			expr = new Expr();
		}
	}
}

class Term {
	char op = ' ';
	Factor factor;
	Term term;

	public Term() {
		factor = new Factor();
		
		if (Lexer.nextToken == Token.MULT_OP || Lexer.nextToken == Token.DIV_OP || Lexer.nextToken == Token.AND_OP) {
			op = Lexer.ch;
			Lexer.lex();
			term = new Term();
		}
	}
}


class Factor {
	Int_Lit intlit;
	Real_Lit reallit;
	Bool_Lit boollit;
	Id_Lit id;
	Factor factor;
	Expr expr;
	Expr expr2;
	
	public Factor() {
		switch(Lexer.nextToken) {
			case Token.INT_LIT:
				intlit = new Int_Lit();
				break;
			case Token.REAL_LIT:
				reallit = new Real_Lit();
				break;
			case Token.TRUE_LIT:
			case Token.FALSE_LIT:
				boollit = new Bool_Lit();
				break;
			case Token.ID:
				id = new Id_Lit();
				break;
			
			case Token.NEG_OP:
				Lexer.lex();
				factor = new Factor();
				break;
			case Token.LEFT_PAREN:
				Lexer.lex();
				expr = new Expr();
				if (Lexer.nextToken == Token.RIGHT_PAREN) {
					Lexer.lex();
				} else {
					if (Lexer.nextToken == Token.LE_OP || Lexer.nextToken == Token.GE_OP || Lexer.nextToken == Token.EQ_OP
							|| Lexer.nextToken == Token.NE_OP || Lexer.nextToken == Token.LT_OP || Lexer.nextToken == Token.GT_OP) {
						Lexer.lex();
						expr2 = new Expr();
						if (Lexer.nextToken == Token.RIGHT_PAREN) {
							Lexer.lex();
						} else {
							System.out.println("Right parenth missing in factor.");
						}
					} else {
						System.out.println("Error in factor.");
					}
				}
				break;
			default:
				System.out.println("Error: invalid token in factor.");
		}
	}
}

class Literal {
	 
	public Literal() {
		
	}
}

class Int_Lit extends Literal {
	int i;

	public Int_Lit() {
		i = Lexer.intValue;
		Lexer.lex();
	}
}

class Real_Lit extends Literal {
	double r;
	 
	public Real_Lit() {
		r = Lexer.realValue;
		Lexer.lex();
	}
}

class Bool_Lit extends Literal {
	boolean b;
	 
	public Bool_Lit() {
		if (Lexer.nextToken == Token.TRUE_LIT) {
			b = true;
			Lexer.lex();
		} else if (Lexer.nextToken == Token.FALSE_LIT) {
			b = false;
			Lexer.lex();
		} else {
			System.out.println("Error. Bool_Lit can only be True or False.");
		}
	}
}

class Id_Lit extends Literal {
	char ident;

	public Id_Lit() {
		ident = Lexer.ident;
		Lexer.lex();
	}
}

