package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import static no.uio.ifi.asp.scanner.Token.*;

class AspPassStmt extends AspStmt{


  AspPassStmt(int n){super(n);}

  static AspPassStmt parse(Scanner s){
    Main.log.enterParser("pass stmt");
    AspPassStmt aps = new AspPassStmt(s.curLineNum());

    skip(s, passToken);
    skip(s, newLineToken);


    Main.log.leaveParser("pass stmt");
    return aps;
  }

  @Override
  void prettyPrint(){
    Main.log.prettyWrite("pass stmt");
    Main.log.prettyWriteLn();

  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    trace("pass");
    return null;
  }
}//slutt
