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

class AspTermOpr extends AspSyntax{
  public TokenKind kind;

  AspTermOpr(int n){
    super(n);
  }
  String [] cond = {"+", "-"};
  int i;

  public static AspTermOpr parse(Scanner s) {
    Main.log.enterParser("term opr");
    AspTermOpr afp = new AspTermOpr(s.curLineNum());
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
    Main.log.leaveParser("term opr");
    return afp;
  }
  @Override
  void prettyPrint(){
    //  System.out.println("YEY");
    Main.log.prettyWrite(" " + cond[i] + " ");
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    RuntimeValue v = null;
    return v;
  }
}
