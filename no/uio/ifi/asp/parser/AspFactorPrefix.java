package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.runtime.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactorPrefix extends AspSyntax{
  public TokenKind kind;
  AspFactorPrefix(int n){
    super(n);
  }

  String[] cond = {"+", "-"};
  int i;

  //samme type metode som atom fra forelesning
  public static AspFactorPrefix parse(Scanner s) {
    Main.log.enterParser("factor prefix");
    AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
    switch (s.curToken().kind) {
      case plusToken:
      afp.kind = s.curToken().kind;
      skip(s, plusToken);
      afp.i = 0;

      break;
      case minusToken:
      afp.kind = s.curToken().kind;
      skip(s, minusToken);
      afp.i= 1;

      break;
      default:
      parserError("Expected a factor operator but found a " +
      s.curToken().kind + "!", s.curLineNum());
    }
    Main.log.leaveParser("factor operator");
    return afp;
  }
  @Override
  void prettyPrint(){
    //    System.out.println("YAAAY");
    Main.log.prettyWrite(" " + cond[i] +  " ");
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    RuntimeValue v = null;
    return v;
  }
}
