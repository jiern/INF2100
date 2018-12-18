package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.runtime.RuntimeBoolValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.main.Main;

class AspBooleanLiteral extends AspAtom{

  AspBooleanLiteral(int n){
    super(n);
  }
  int i;
  boolean value = false;
  String [] cond = {"False", "True"};
  //samme type metode som atom fra forelesning
  public static AspBooleanLiteral parse(Scanner s){
    Main.log.enterParser("boolean");
    AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
    if(s.curToken().kind == falseToken){
      abl.i = 0;
      abl.value = false;
      skip(s, falseToken);

    } else if (s.curToken().kind == trueToken){
      abl.i = 1;
      abl.value = true;
      skip(s, trueToken);

    } else {
      parserError("Expected a boolean literal but found a " +
      s.curToken().kind + "!", s.curLineNum());
    }
    Main.log.leaveParser("boolean");
    return abl;
  }
  @Override
  void prettyPrint(){
    Main.log.prettyWrite(cond[i]);
  }


  @Override
  RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue {
    return new RuntimeBoolValue(value);

  }
}
