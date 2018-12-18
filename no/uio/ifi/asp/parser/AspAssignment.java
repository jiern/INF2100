package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;;

class AspAssignment extends AspStmt{
  ArrayList<AspSubscription> subliste = new ArrayList<>();
  AspExpr ae;
  AspName an;

  AspAssignment(int n){
    super(n);
  }

  static AspAssignment parse(Scanner s){
    Main.log.enterParser("assignment");
    AspAssignment aa = new AspAssignment(s.curLineNum());
    aa.an = AspName.parse(s);
    while(s.curToken().kind != equalToken){
      aa.subliste.add(AspSubscription.parse(s));
    }

    skip(s, equalToken);
    aa.ae = AspExpr.parse(s);
    skip(s, newLineToken);

    Main.log.leaveParser("assignment");
    return aa;
  }

  @Override
  void prettyPrint(){
    an.prettyPrint();


    if(!subliste.isEmpty()) {
      for (int i = 0; i < subliste.size(); i++){
        subliste.get(i).prettyPrint();
      }
    }
    Main.log.prettyWrite(" = ");

    ae.prettyPrint();
    Main.log.prettyWriteLn();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{

    RuntimeValue v = ae.eval(curScope);
    RuntimeValue navn = null;

    if(!subliste.isEmpty()){
      //evaluer navnet
      navn = an.eval(curScope);
      for(int i = 0; i < subliste.size()-1; i++){
        //evaluer alle subscription
        navn = navn.evalSubscription(subliste.get(i).eval(curScope), this);
      }
      RuntimeValue siste = subliste.get(subliste.size()-1).eval(curScope);
      navn.evalAssignElem(siste, v, this);
      return v;
    }else{
      curScope.assign(an.id, v);
      return v;
    }
  }
}//slutt
