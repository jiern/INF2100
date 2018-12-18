package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.main.Main;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import static no.uio.ifi.asp.scanner.Token.*;
import no.uio.ifi.asp.runtime.*;

public abstract class AspAtom extends AspSyntax {
  AspAtom(int n) {
    super(n);
  }

  abstract RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue;



  static AspAtom parse(Scanner s) {

    Main.log.enterParser("atom");
    AspAtom a = null;
    switch (s.curToken().kind) {
      case falseToken:
      case trueToken:
      a = AspBooleanLiteral.parse(s);
      break;
      case floatToken:
      a = AspFloatLiteral.parse(s);
      break;
      case integerToken:
      a = AspIntegerLiteral.parse(s);
      break;
      case leftBraceToken:
      a = AspDictDisplay.parse(s);
      break;
      case leftBracketToken:
      a = AspListDisplay.parse(s);
      break;
      case leftParToken:
      a = AspInnerExpr.parse(s);
      break;
      case nameToken:
      a = AspName.parse(s);
      break;
      case noneToken:
      a = AspNoneLiteral.parse(s);
      break;
      case stringToken:
      a = AspStringLiteral.parse(s);
      break;
      default:
      parserError("Expected an expression atom but found a " + s.curToken().kind + "!", s.curLineNum());
    }
    Main.log.leaveParser("atom");
    return a;
  }



}
