package no.uio.ifi.asp.parser;

import java.util.*;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;

class AspAndTest extends AspSyntax {
  ArrayList<AspNotTest> notTests = new ArrayList<>();

  AspAndTest(int n) {
    super(n);
  }

  static AspAndTest parse(Scanner s) {
    Main.log.enterParser("and test");

    AspAndTest aat = new AspAndTest(s.curLineNum());
    while (true) {
      aat.notTests.add(AspNotTest.parse(s));
      if (s.curToken().kind != andToken)
      break;
      skip(s, andToken);
    }

    Main.log.leaveParser("and test");
    return aat;
  }

  @Override
  void prettyPrint() {
    int nPrinted = 0;

    for (AspNotTest ant: notTests) {
      if (nPrinted > 0){
        Main.log.prettyWrite(" and ");
      }
      ant.prettyPrint();
      ++nPrinted;
    }
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = notTests.get(0).eval(curScope); // v = notTests get plass 0, med rekursiv kall med curScope
    for (int i = 1; i < notTests.size(); ++i) { //så lenge i er mindre enn notTests sin størrelse, i++
      if (!v.getBoolValue("and operand",this)){ //sender v inn i getBoolValue, og hvis denne er false, så returner vi v
        return v;
      }
      v = notTests.get(i).eval(curScope); // v = notTests get plass i, rekursivt kall med curScope
    } //for ferdig
    return v; //returner v
  }
}
