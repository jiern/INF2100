package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.main.Main;

public abstract class AspStmt extends AspSyntax{

  AspStmt(int linjeNr){
    super(linjeNr);
  }

  AspAssignment as = null;
  AspExprStmt aes = null;
  AspIfStmt ais = null;
  AspWhileStmt aws = null;
  AspReturnStmt ars = null;
  AspPassStmt aps = null;
  AspFuncDef afd = null;

  static AspStmt parse(Scanner s) {
    Main.log.enterParser("stmt");
    AspStmt a;


    switch (s.curToken().kind) {
      case ifToken:
      AspIfStmt tmp = AspIfStmt.parse(s);
      a = tmp;
      a.ais = tmp;
      break;

      case whileToken:
      AspWhileStmt tmp1 = AspWhileStmt.parse(s);
      a = tmp1;
      a.aws = tmp1;
      break;
      case returnToken:

      AspReturnStmt tmp2 = AspReturnStmt.parse(s);
      a = tmp2;
      a.ars = tmp2;
      break;
      case passToken:

      AspPassStmt tmp3 = AspPassStmt.parse(s);
      a = tmp3;
      a.aps = tmp3;
      break;
      case defToken:

      AspFuncDef tmp4 = AspFuncDef.parse(s);


      a = tmp4;
      a.afd = tmp4;
      break;
      default:
      if(s.anyEqualToken()) {
        a = AspAssignment.parse(s);
      } else {
        a = AspExprStmt.parse(s);
      }
    }
    Main.log.leaveParser("stmt");
    return a;
  }




}
