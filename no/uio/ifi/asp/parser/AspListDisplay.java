package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
class AspListDisplay extends AspAtom{

  AspListDisplay(int n){
    super(n);
  }
  //arraylist for bokser
  ArrayList<AspExpr> aspExpr = new ArrayList<>();
  boolean cond = false;

  public static AspListDisplay parse(Scanner s){
    Main.log.enterParser("List Display");
    AspListDisplay ald = new AspListDisplay(s.curLineNum());
    skip(s, leftBracketToken);
    while(s.curToken().kind != rightBracketToken){
      //while kjoorer til vi kommer til en right Bracket
      ald.cond = true; //setter cond til å vaere true
      ald.aspExpr.add(AspExpr.parse(s)); //legger aspExpr som vi finner inn i arraylist
      if(s.curToken().kind == commaToken){ //hvis det er comma
        skip(s, commaToken);
      }
    }
    skip(s, rightBracketToken); // ute av while, funnet og skipper  right bracket
    Main.log.leaveParser("List Display");
    return ald;
  }
  @Override
  void prettyPrint(){
    Main.log.prettyWrite("["); //1
    if(cond){
      int print = 0;

      for(AspExpr a : aspExpr){//kun EN arraylist
        if(print > 0){
          Main.log.prettyWrite(", ");
        }//2
        a.prettyPrint();
        print++;
      }
    }
    Main.log.prettyWrite("]"); //3. og ferdig

  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    ArrayList<RuntimeValue> list = new ArrayList<>();
    RuntimeValue v = null;
    if(cond){
      if(!aspExpr.isEmpty()){
        for(int i = 0; i < aspExpr.size(); i++){
          v = aspExpr.get(i).eval(curScope);
          list.add(aspExpr.get(i).eval(curScope));
        }
      }
    }
    return new RuntimeListValue(list);
  }
}
