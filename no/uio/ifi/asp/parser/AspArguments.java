package no.uio.ifi.asp.parser;

import java.util.*;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;

class AspArguments extends AspPrimarySuffix{

  AspArguments(int n){
    super(n);
  }
  ArrayList<AspExpr> aspExpr = new ArrayList<>();

  boolean cond = false;
  public static AspArguments parse(Scanner s){
    Main.log.enterParser("Arguments");
    AspArguments aa = new AspArguments(s.curLineNum());

    skip(s, leftParToken);

    while(s.curToken().kind != rightParToken){
      //   if(s.curToken().kind != rightParToken)
      //hvis s.curtoken IKKE er rightPar, cond er true
      aa.cond = true;
      aa.aspExpr.add(AspExpr.parse(s));
      if(s.curToken().kind != commaToken){
        break;
      }
      skip(s, commaToken);
    }

    skip(s,rightParToken);
    Main.log.leaveParser("Arguments");
    return aa;
  }

  @Override
  void prettyPrint(){
    Main.log.prettyWrite(" (");
    if(cond){
      int print = 0;
      for(AspExpr a : aspExpr){
        if(print > 0){
          Main.log.prettyWrite(", ");
        }
        a.prettyPrint();
        print++;

      }
    }
    Main.log.prettyWrite(") ");
  }
  //(
  //expr
  //,
  //)



  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    ArrayList<RuntimeValue> params = new ArrayList<>();
    RuntimeListValue res = null;
    RuntimeValue v = null;
    //System.out.println(aspExpr.size());
    if(!aspExpr.isEmpty()){
      for(int i = 0; i < aspExpr.size(); i++){

        params.add(aspExpr.get(i).eval(curScope));
      }
    }
    //beregne akutuelle parametre
    return new RuntimeListValue(params);
  }
}
