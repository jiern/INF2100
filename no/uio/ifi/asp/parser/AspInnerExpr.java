package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.main.Main;

class AspInnerExpr extends AspAtom{

  AspInnerExpr(int n){
    super(n);
  }
  AspExpr ex;

  public static AspInnerExpr parse(Scanner s){
    Main.log.enterParser("inner expr");
    AspInnerExpr aie = new AspInnerExpr(s.curLineNum());
    skip(s, leftParToken);
    aie.ex = AspExpr.parse(s);
    skip(s, rightParToken);

    Main.log.leaveParser("inner expr");
    return aie;
  }
  @Override
  void prettyPrint(){
    Main.log.prettyWrite(" (");
    ex.prettyPrint();
    Main.log.prettyWrite(") ");
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = ex.eval(curScope);
    return v;
  }

}
