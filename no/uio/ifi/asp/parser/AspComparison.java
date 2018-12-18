package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspComparison extends AspSyntax {

  ArrayList<AspTerm> tliste = new ArrayList<>();
  ArrayList<AspCompOpr> cliste = new ArrayList<>();

  AspComparison(int n) {
    super(n);
  }

  static AspComparison parse(Scanner s) {
    Main.log.enterParser("comparison");

    AspComparison ac = new AspComparison(s.curLineNum());

    while(true) {
      ac.tliste.add(AspTerm.parse(s));

      if (s.isCompOpr()) {
        ac.cliste.add(AspCompOpr.parse(s));
      }else{
        break;
      }
    }

    Main.log.leaveParser("comparison");
    return ac;
  }

  @Override
  void prettyPrint() {
    int nPrinted = 0;
    if(cliste.size() > 0){
      for(int i = 0; i < cliste.size(); i++){
        tliste.get(i).prettyPrint();
        cliste.get(i).prettyPrint();
        nPrinted++;
      }
      tliste.get(nPrinted).prettyPrint();
    }else{
      //tilfelle der term er 1
      tliste.get(nPrinted).prettyPrint();
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    //System.out.println("skjer det her ?");
    Boolean cond = false;
    RuntimeValue v = tliste.get(0).eval(curScope);
    for(int i = 1; i < tliste.size(); i++){
      TokenKind k = cliste.get(i-1).kind;
      switch(k){
        case greaterToken:
        v = v.evalGreater(tliste.get(i).eval(curScope), this);  break;
        case lessToken:
        v = v.evalLess(tliste.get(i).eval(curScope), this); break;
        case doubleEqualToken:
        v = v.evalEqual(tliste.get(i).eval(curScope), this); break;
        case greaterEqualToken:
        v = v.evalGreaterEqual(tliste.get(i).eval(curScope), this); break;
        case lessEqualToken:
        v = v.evalLessEqual(tliste.get(i).eval(curScope), this); break;
        case notEqualToken:
        v = v.evalNotEqual(tliste.get(i).eval(curScope), this); break;
        default:
        Main.panic("Illigal term operator: " + k + "'!'");
      }
      if(tliste.size()-1 == i){
        if(v.getBoolValue("comp operand", this)){
          return new RuntimeBoolValue(true);
        }
      }
      if(tliste.size() > i){
        if(v.getBoolValue("comp operand", this)){
          v = tliste.get(i).eval(curScope);
        } else {
          return new RuntimeBoolValue(false);
        }
      }
    }
    return v;
  }
}
