package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSuite extends AspSyntax{
  ArrayList<AspStmt> stmtliste = new ArrayList<>();

  AspSuite(int n){
    super(n);
  }

  static AspSuite parse(Scanner s){
    Main.log.enterParser("suite");
    AspSuite as = new AspSuite(s.curLineNum());

    skip(s, newLineToken);
    skip(s, indentToken);
    while(s.curToken().kind != dedentToken) {
      as.stmtliste.add(AspStmt.parse(s));
    }
    skip(s, dedentToken);



    Main.log.leaveParser("suite");
    return as;
  }

  @Override
  void prettyPrint(){
    Main.log.prettyWriteLn();
    Main.log.prettyIndent();
    for(int i = 0; i < stmtliste.size(); i++){
      stmtliste.get(i).prettyPrint();
    }
    Main.log.prettyDedent();

  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
    RuntimeValue v = stmtliste.get(0).eval(curScope);
    for(int i = 1; i < stmtliste.size(); i++){
      v = stmtliste.get(i).eval(curScope);
    }
    return v;
  }
}//slutt
