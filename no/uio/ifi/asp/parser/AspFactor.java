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

class AspFactor extends AspSyntax{
  ArrayList<AspPrimary> primaryliste = new ArrayList<>();
  ArrayList<AspFactorOpr> factoprliste = new ArrayList<>();
  AspFactorPrefix fp;

  private boolean factorPre;
  private boolean factOpr;

  AspFactor(int n){
    super(n);
  }

  static AspFactor parse(Scanner s){

    Main.log.enterParser("factor");
    AspFactor af = new AspFactor(s.curLineNum());

    //Sjekker om det er FactPrefix
    af.factorPre = s.isFactorPrefix();

    if(af.factorPre){

      af.fp = AspFactorPrefix.parse(s);
    }
    while(true){
      af.primaryliste.add(AspPrimary.parse(s));
      //sjekker om factopr, hvis ikke saa breaker vi
      af.factOpr = s.isFactorOpr();
      if(!af.factOpr){
        break;
      }
      af.factoprliste.add(AspFactorOpr.parse(s));
    }


    Main.log.leaveParser("factor");
    return af;
  }

  @Override
  void prettyPrint() {
    int nPrinted = 0;
    if (fp != null) {
      fp.prettyPrint();
    }
    if (factoprliste.size() > 0) {
      for (int i = 0; i < factoprliste.size(); i++) {
        primaryliste.get(i).prettyPrint();
        factoprliste.get(i).prettyPrint();
        nPrinted++;
      }
      //tilfelle der term er 1
    }
    primaryliste.get(nPrinted).prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    RuntimeValue v = null;
    if(fp != null){
      TokenKind k = fp.kind;
      switch (k) {
        case minusToken:
        v = primaryliste.get(0).eval(curScope).evalNegate(this); break;
        case plusToken:
        v = primaryliste.get(0).eval(curScope).evalPositive(this); break;
        default:
        Main.panic("Illegal term operator: " + k + "!");
      }
    } else {
      v = primaryliste.get(0).eval(curScope);
    }

    if(!factoprliste.isEmpty()){
      for(int i = 1; i < primaryliste.size(); ++i){
        TokenKind k = factoprliste.get(i-1).kind;
        switch(k){
          case astToken:
          v = v.evalMultiply(primaryliste.get(i).eval(curScope), this); break;
          case slashToken:
          v = v.evalDivide(primaryliste.get(i).eval(curScope), this); break;
          case percentToken:
          v = v.evalModulo(primaryliste.get(i).eval(curScope), this); break;
          case doubleSlashToken:
          v = v.evalIntDivide(primaryliste.get(i).eval(curScope), this); break;
          default:
          Main.panic("Illigal term operator: " + k + "!");
        }
      }
    }
    return v;
  }

}//slutt
