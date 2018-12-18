package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspWhileStmt extends AspStmt {
  AspExpr test;
  AspSuite body;

  AspWhileStmt(int n) {
    super(n);
  }

  static AspWhileStmt parse(Scanner s) {
    Main.log.enterParser("while stmt");
    AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
    skip(s, whileToken);
    aws.test = AspExpr.parse(s);
    skip(s, colonToken);
    aws.body = AspSuite.parse(s);
    Main.log.leaveParser("while stmt");
    return aws;
  }

  @Override
  void prettyPrint(){
    Main.log.prettyWrite("while ");
    test.prettyPrint();
    Main.log.prettyWrite(": ");
    body.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{

    RuntimeValue v = null;


    while(test.eval(curScope).getBoolValue("while test.", this)){

      v = body.eval(curScope);
      //return v;
    }
    return v;
  }
}
