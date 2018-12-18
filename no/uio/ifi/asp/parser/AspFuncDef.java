package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;

public class AspFuncDef extends AspStmt{
  ArrayList<AspName> nameliste = new ArrayList<>();
  public AspSuite as;

  AspFuncDef(int n){
    super(n);
  }

  public ArrayList<AspName> getNames(){
    return nameliste;
  }

  static AspFuncDef parse(Scanner s){
    Main.log.enterParser("func");
    AspFuncDef afd = new AspFuncDef(s.curLineNum());
    skip(s, defToken);
    afd.nameliste.add(AspName.parse(s));
    //skip(s, nameToken);
    skip(s, leftParToken);

    while(s.curToken().kind != rightParToken){
      afd.nameliste.add(AspName.parse(s));
      if(s.curToken().kind != commaToken){
        break;
      }
      skip(s, commaToken);
    }

    skip(s, rightParToken);
    skip(s, colonToken);
    afd.as = AspSuite.parse(s);


    Main.log.leaveParser("func def");
    return afd;
  }

  @Override
  void prettyPrint(){
    int nPrinted = 0;
    Main.log.prettyWrite("def ");
    if(!nameliste.isEmpty())
    nameliste.get(0).prettyPrint();
    Main.log.prettyWrite("( ");
    if(!nameliste.isEmpty()){
      for(int i = 0; i < nameliste.size(); i++){
        if(nPrinted > 0){
          Main.log.prettyWrite(", ");
        }
        nameliste.get(i).prettyPrint();

        nPrinted++;
      }
    }
    Main.log.prettyWrite(") ");
    Main.log.prettyWrite(" : ");
    as.prettyPrint();


  }

  public RuntimeValue runFunction(RuntimeScope curScope){
    try {
      as.eval(curScope);
    } catch (RuntimeReturnValue rrv) {
      return rrv.value;
    }
    return null;

  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    RuntimeValue v = null;
    AspName n = nameliste.get(0);
    v  = new RuntimeFunc(this, curScope, n.getName());
    curScope.assign(n.getName(), v);
    trace("def" + v.showInfo());
    return v;
  }
}//slutt
