package no.uio.ifi.asp.parser;

import java.util.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import static no.uio.ifi.asp.scanner.Token.*;
import no.uio.ifi.asp.main.Main;
class AspCompOpr extends AspSyntax{
  public TokenKind kind;

  AspCompOpr(int n){
    super(n);
  }
  String[] cond = {">", "<", "==", ">=", "<=", "!="};
  int i;
  static String value;
  //samme type metode som i Atom fra forelesing
  static AspCompOpr parse(Scanner s) {
    Main.log.enterParser("Comp operator");
    AspCompOpr aco = new AspCompOpr(s.curLineNum());
    switch (s.curToken().kind) {

      case greaterToken:
      aco.kind = s.curToken().kind;
      skip(s, greaterToken);
      aco.i = 0;
      break;
      case lessToken:
      aco.kind = s.curToken().kind;
      skip(s, lessToken);
      aco.i = 1;
      break;
      case doubleEqualToken:
      aco.kind = s.curToken().kind;
      skip(s, doubleEqualToken);
      aco.i = 2;
      break;
      case greaterEqualToken:
      aco.kind = s.curToken().kind;
      skip(s, greaterEqualToken);
      aco.i = 3;
      break;
      case lessEqualToken:
      aco.kind = s.curToken().kind;
      skip(s, lessEqualToken);
      aco.i = 4;

      break;
      case notEqualToken:
      aco.kind = s.curToken().kind;
      skip(s, notEqualToken);
      aco.i = 5;
      break;
      default:
      parserError("Expected a comp operator but found a " +
      s.curToken().kind + "!", s.curLineNum());
    }
    Main.log.leaveParser("comp operator");
    return aco;
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
