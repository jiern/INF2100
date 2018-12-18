package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.main.Main;

class AspNoneLiteral extends AspAtom{


  AspNoneLiteral(int n){
    super(n);
  }

  public static AspNoneLiteral parse(Scanner s){
    Main.log.enterParser("None");
    AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
    skip(s, noneToken);
    Main.log.leaveParser("None");
    return anl;
  }
  @Override
  void prettyPrint(){
    Main.log.prettyWrite("None");
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue res = null;
    res = new RuntimeNoneValue();
    return res;
  }
}
