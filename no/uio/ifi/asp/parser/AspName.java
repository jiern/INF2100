package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.*;

public class AspName extends AspAtom{
  Token t;
  public String id;

  AspName(int n){
    super(n);
  }
  public String getName(){
    return t.name;
  }

  public static AspName parse(Scanner s){
    Main.log.enterParser("name");
    AspName an = new AspName(s.curLineNum());


    an.t = s.curToken();

    skip(s, TokenKind.nameToken);

    an.id = an.t.name;
    Main.log.leaveParser("name");
    return an;
  }


  @Override
  void prettyPrint(){
    Main.log.prettyWrite(t.name);
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    //    System.out.println(id);
    return curScope.find(id, this);

  }
}//slutt
