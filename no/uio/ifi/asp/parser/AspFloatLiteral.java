package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.main.Main;

class AspFloatLiteral extends AspAtom{
  float value;
  AspFloatLiteral(int n){
    super(n);
  }
  Token t;

  public static AspFloatLiteral parse(Scanner s){
    Main.log.enterParser("float literal");
    AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());

    afl.t = s.curToken();
    skip(s, floatToken);

    Main.log.leaveParser("float literal");
    return afl;
  }
  @Override
  void prettyPrint(){
    Main.log.prettyWrite(t.floatLit + "");
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeFloatValue(t.floatLit);
  }
}
