package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspNotTest extends AspSyntax {
  boolean withNot = false;
  AspComparison comp;


  AspNotTest(int n) {
    super(n);
  }

  static AspNotTest parse(Scanner s) {
    Main.log.enterParser("not test");

    AspNotTest ant = new AspNotTest(s.curLineNum());

    if (s.curToken().kind == notToken) {
      ant.withNot = true;
      skip(s, notToken);

    }
    ant.comp = AspComparison.parse(s);


    Main.log.leaveParser("not test");
    return ant;
  }

  @Override
  void prettyPrint() {
    if(withNot){
      Main.log.prettyWrite(" not ");
    }
    comp.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    RuntimeValue v = comp.eval(curScope);
    if(withNot){
      v = v.evalNot(this);
    }
    return v;
  }
}
