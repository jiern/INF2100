package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
class AspSubscription extends AspPrimarySuffix{

  AspSubscription(int n){
    super(n);
  }
  AspExpr aspExpr;
  public static AspSubscription parse(Scanner s){
    Main.log.enterParser("Subscription");
    AspSubscription ass = new AspSubscription(s.curLineNum());
    skip(s, leftBracketToken);
    ass.aspExpr = AspExpr.parse(s);
    skip(s, rightBracketToken);
    Main.log.leaveParser("Subscription");
    return ass;
  }
  @Override
  void prettyPrint(){
    Main.log.prettyWrite(" ["); //1
    aspExpr.prettyPrint(); //boksen
    Main.log.prettyWrite("] "); //2
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    RuntimeValue v = aspExpr.eval(curScope);
    return v;
  }
}

// [
//expr
//]
