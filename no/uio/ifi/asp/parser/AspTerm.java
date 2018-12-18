package no.uio.ifi.asp.parser;
import java.util.ArrayList;
//import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
//import no.uio.ifi.asp.runtime.RuntimeValue.*;

//import static no.uio.ifi.asp.scanner.Token.*;

//import no.uio.ifi.asp.scanner.TokenKind;
//import no.uio.ifi.asp.scanner.Token;


class AspTerm extends AspSyntax{


  ArrayList<AspFactor> factor = new ArrayList<>();
  ArrayList<AspTermOpr> termOpr = new ArrayList<>();
  static boolean cond;


  AspTerm(int n) {
    super(n);

  }

  static AspTerm parse(Scanner s) {
    Main.log.enterParser("Term test");

    AspTerm aat = new AspTerm(s.curLineNum());
    while (true) {
      aat.factor.add(AspFactor.parse(s));
      cond = s.isTermOpr();
      if (!cond) {
        break;
      }

      aat.termOpr.add(AspTermOpr.parse(s));
    }

    Main.log.leaveParser("term");
    return aat;
  }


  @Override
  void prettyPrint() {
    int nPrinted = 0;

    for (AspFactor ant: factor) {
      if (nPrinted > 0){
        if(!termOpr.isEmpty())
        termOpr.get(0).prettyPrint();

      }
      ant.prettyPrint();
      nPrinted++;
    }
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    //System.out.println("Skjer det her?" + );
    RuntimeValue v = factor.get(0).eval(curScope);
    for (int i = 1; i < factor.size(); ++i) {
      TokenKind k = termOpr.get(i-1).kind;
      switch (k) {
        case minusToken:
        v = v.evalSubtract(factor.get(i).eval(curScope), this);  break;
        case plusToken:
        v = v.evalAdd(factor.get(i).eval(curScope), this);  break;
        default:
        Main.panic("Illegal term operator: " + k + "!");
      }
    }
    return v;

  }
}
