package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.runtime.RuntimeValue.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.Token.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.TokenKind;
import no.uio.ifi.asp.scanner.Token;
class AspFactorOpr extends AspSyntax{
  public TokenKind kind;

  AspFactorOpr(int n){
    super(n);
  }

  String[] cond = {"*", "/", "%", "//"};
  int i;
  //samme type metode som atom fra forelesning
  static AspFactorOpr parse(Scanner s) {
    Main.log.enterParser("factor opr");
    AspFactorOpr afo = new AspFactorOpr(s.curLineNum());
    switch (s.curToken().kind) {
      case astToken:
      afo.kind = s.curToken().kind;
      skip(s, astToken);
      afo.i = 0;

      break;
      case slashToken:
      afo.kind = s.curToken().kind;
      skip(s, slashToken);
      afo.i = 1;

      break;
      case percentToken:
      afo.kind = s.curToken().kind;
      skip(s, percentToken);
      afo.i = 2;

      break;
      case doubleSlashToken:
      afo.kind = s.curToken().kind;
      skip(s, doubleSlashToken);
      afo.i = 3;

      break;
      default:
      parserError("Expected a factor operator but found a " +
      s.curToken().kind + "!", s.curLineNum());
    }
    Main.log.leaveParser("factor opr");
    return afo;
  }
  @Override
  void prettyPrint(){
    Main.log.prettyWrite(" " + cond[i] + " ");
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    RuntimeValue v = null;
    return v;
  }
}
