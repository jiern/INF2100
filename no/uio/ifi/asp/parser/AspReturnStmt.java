package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspReturnStmt extends AspStmt{
  AspExpr ae;

  AspReturnStmt(int n){super(n);}

  static AspReturnStmt parse(Scanner s){
    Main.log.enterParser("return stmt");
    AspReturnStmt ars = new AspReturnStmt(s.curLineNum());

    skip(s, returnToken);

    ars.ae = AspExpr.parse(s);

    skip(s, newLineToken);


    Main.log.leaveParser("return stmt");
    return ars;
  }

  @Override
  void prettyPrint(){
    Main.log.prettyWrite("return ");
    ae.prettyPrint();
    Main.log.prettyWriteLn();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    RuntimeValue v = ae.eval(curScope);
    trace("return"+v.showInfo());
    throw new RuntimeReturnValue(v);
  }
}//slutt
