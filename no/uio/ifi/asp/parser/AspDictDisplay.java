package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;
import java.util.*;

class AspDictDisplay extends AspAtom{

  AspDictDisplay(int n){
    super(n);
  }
  //bokser
  ArrayList<AspStringLiteral> stringLiteral  = new ArrayList<>();
  ArrayList<AspExpr> aspExpr  = new ArrayList<>();

  boolean cond = false;


  public static AspDictDisplay parse (Scanner s){
    Main.log.enterParser("string literal");
    AspDictDisplay addp = new AspDictDisplay(s.curLineNum());
    skip(s,leftBraceToken);
    while(s.curToken().kind != rightBraceToken){ // naar rightBrace kommer = slutten
      addp.cond = true;
      addp.stringLiteral.add(AspStringLiteral.parse(s));
      skip(s,colonToken);
      addp.aspExpr.add(AspExpr.parse(s));
      if(s.curToken().kind == commaToken){
        skip(s, commaToken);
      }
    }
    //ute av while, kommet en right bracket
    skip(s, rightBraceToken);
    Main.log.leaveParser("String Literal");
    return addp;
  }
  @Override
  void prettyPrint(){
    Main.log.prettyWrite(" {"); //1
    if(cond){
      int print = 0;
      int i = 0;
      while(i < stringLiteral.size()){
        if(print > 0){
          Main.log.prettyWrite(", "); //2
        }
        stringLiteral.get(i).prettyPrint();
        Main.log.prettyWrite(": "); //3
        aspExpr.get(i).prettyPrint();
        print++;

        i++;
      }
    }
    Main.log.prettyWrite("} ");
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    HashMap<String, RuntimeValue> dict = new HashMap<>();
    if(stringLiteral.size() == 0){
      return new RuntimeDictValue(dict);
    }
    for(int i = 0; i < stringLiteral.size(); i++){
      dict.put(stringLiteral.get(i).eval(curScope).getStringValue("string oprand.", this), this.aspExpr.get(i).eval(curScope));
    }
    return new RuntimeDictValue(dict);
  }
}
//leftBraceToken
//string literal
// :
// expr
//rightBraceToken
