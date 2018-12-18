package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.main.Main;

class AspExprStmt extends AspStmt{
  AspExprStmt(int n){
    super(n);
  }
  AspExpr ex;

  public static AspExprStmt parse(Scanner s){
    Main.log.enterParser("expr statement");
    AspExprStmt aes = new AspExprStmt(s.curLineNum());
    aes.ex = AspExpr.parse(s);
    skip(s, newLineToken);
    Main.log.leaveParser("expr statement");
    return aes;
  }

  @Override
  void prettyPrint(){
    ex.prettyPrint();
    Main.log.prettyWriteLn();
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    trace("Expr");
    RuntimeValue v =  ex.eval(curScope);
    return v;
  }

}
