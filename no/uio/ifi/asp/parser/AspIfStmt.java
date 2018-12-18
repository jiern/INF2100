package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspIfStmt extends AspStmt{
  ArrayList<AspExpr> exprliste = new ArrayList <>();
  ArrayList<AspSuite> suiteliste = new ArrayList <>();
  boolean elseSkjer = false;

  AspIfStmt(int n){super(n);}

  static AspIfStmt parse(Scanner s){
    Main.log.enterParser("if stmt");
    AspIfStmt is = new AspIfStmt(s.curLineNum());

    skip(s, ifToken);
    while(true){
      is.exprliste.add(AspExpr.parse(s));
      skip(s, colonToken);
      is.suiteliste.add(AspSuite.parse(s));
      if(s.curToken().kind != elifToken){
        break;
      }
      skip(s, elifToken);
    }
    if(s.curToken().kind == elseToken) {
      is.elseSkjer = true;
      skip(s, elseToken);
      skip(s, colonToken);
      is.suiteliste.add(AspSuite.parse(s));
    }

    Main.log.leaveParser("if stmt");
    return is;
  }

  @Override
  void prettyPrint(){
    Main.log.prettyWrite("if ");
    int count = 0;


    for(int i = 0; i < exprliste.size(); i++){
      if(count > 0){
        Main.log.prettyWrite("elif ");
      }
      exprliste.get(i).prettyPrint();
      Main.log.prettyWrite(" : ");
      suiteliste.get(i).prettyPrint();
      count++;

    }

    if(elseSkjer){
      Main.log.prettyWrite("else");
      Main.log.prettyWrite(":");
      suiteliste.get(suiteliste.size()-1).prettyPrint();
    }

  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    RuntimeValue v = null;
    for(int i = 0; i < exprliste.size(); i++){
      v = exprliste.get(i).eval(curScope);
      if(exprliste.get(i).eval(curScope).getBoolValue("if test.", this)){
        suiteliste.get(i).eval(curScope);
        return v;
      }
    }
    if(suiteliste.size() > exprliste.size()){
      suiteliste.get(suiteliste.size()-1).eval(curScope);
    }

    trace("if" + v.showInfo());
    return v;
  }
}//slutt
