package no.uio.ifi.asp.parser;

import java.util.*;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;

public class AspExpr extends AspSyntax {
  ArrayList<AspAndTest> andTests = new ArrayList<>();

  AspExpr(int n) {
    super(n);
  }


  public static AspExpr parse(Scanner s) {
    Main.log.enterParser("expr");
    AspExpr ae = new AspExpr(s.curLineNum());
    //-- Must be changed in part 2:
    while(true){
      ae.andTests.add(AspAndTest.parse(s));
      if(s.curToken().kind != orToken){
        break;
      }
      skip(s, orToken);
    }

    Main.log.leaveParser("expr");
    return ae;
  }


  @Override
  public void prettyPrint() {
    //-- Must be changed in part 2:
    int nPrinted = 0;
    for(AspAndTest aat: andTests){
      if(nPrinted > 0){
        Main.log.prettyWrite(" or ");
      }
      aat.prettyPrint();
      nPrinted++;
    }

  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = andTests.get(0).eval(curScope);
    //    boolean cond = false;
    for(int i = 1; i < andTests.size(); i++){

      if(v.getBoolValue("Expr operand", this)){

        //    cond = true;
        return v;
      }
      v = andTests.get(i).eval(curScope);
    }

    return v;
  }
}
