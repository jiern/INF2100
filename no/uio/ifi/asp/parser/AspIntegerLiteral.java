package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.main.Main;

class AspIntegerLiteral extends AspAtom{
  int value;

  AspIntegerLiteral(int n){
    super(n);
  }

  Token t;
  public static AspIntegerLiteral parse(Scanner s) {
    Main.log.enterParser("integer literal");
    AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());
    if(s.curToken().kind == integerToken) {
      ail.t = s.curToken();
      ail.value = (int) ail.t.integerLit;
      skip(s, integerToken);
    }else {
      parserError("Expected a integer literal but found a " +
      s.curToken().kind + "!", s.curLineNum());
    }
    Main.log.leaveParser("integer literal");
    return ail;
  }
  @Override
  void prettyPrint(){
    Main.log.prettyWrite(t.integerLit + "");
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue res = null;

    return new RuntimeIntValue(value);

    //    return res;
  }
}
