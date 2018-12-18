package no.uio.ifi.asp.parser;


import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.main.Main;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.*;


public class AspStringLiteral extends AspAtom{
  String value;
  AspStringLiteral(int n) {
    super(n);
  }

  Token token;

  public static AspStringLiteral parse(Scanner s) {
    Main.log.enterParser("String Literal");
    AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
    if(s.curToken().kind == stringToken){
      asl.token = s.curToken();
      skip(s, stringToken);
    }else{
      parserError("Expected a "  + stringToken + " but found a " +
      s.curToken().kind + "!", s.curLineNum());
    }
    Main.log.leaveParser("String Literal");
    return asl;
  }

  @Override
  void prettyPrint(){

    Main.log.prettyWrite("\"" + token.stringLit + "\"");
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeStringValue(token.stringLit);
  }
}
