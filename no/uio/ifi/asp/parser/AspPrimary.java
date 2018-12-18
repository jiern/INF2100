
package no.uio.ifi.asp.parser;

import java.util.*;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.scanner.TokenKind;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.runtime.RuntimeValue;

class AspPrimary extends AspStmt{
  AspAtom atompeker;
  ArrayList<AspPrimarySuffix> aps = new ArrayList<>();

  AspPrimary(int n){
    super(n);
  }

  static AspPrimary parse(Scanner s){

    Main.log.enterParser("Primary");
    AspPrimary ap = new AspPrimary(s.curLineNum());
    ap.atompeker = AspAtom.parse(s);

    //Siden Primarysuffix er abstract, saa maa vi sjekke om det er parentes eller bracket
    while(s.curToken().kind == leftParToken || s.curToken().kind == leftBracketToken){
      ap.aps.add(AspPrimarySuffix.parse(s));

      if(AspPrimarySuffix.parseSjekk){
        break;
      }
    }
    Main.log.leaveParser("Primary");
    return ap;
  }

  @Override
  void prettyPrint(){
    atompeker.prettyPrint();
    for(int i = 0; i < aps.size(); i++){
      aps.get(i).prettyPrint();
    }
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    RuntimeValue v = null;
    v = atompeker.eval(curScope);
    ArrayList<RuntimeValue> list;

    if ( !aps.isEmpty()){
      for(int i = 0; i < aps.size(); i++){

        if(aps.get(i) instanceof AspSubscription){
          v = v.evalSubscription(aps.get(i).eval(curScope), this);

        } else if(aps.get(i) instanceof AspArguments){
          list = aps.get(i).eval(curScope).getList(" AspPrim get list" , this);

          v = v.evalFuncCall(list, this);
        }
      }
    }
    return v;
  }







}//slutt
