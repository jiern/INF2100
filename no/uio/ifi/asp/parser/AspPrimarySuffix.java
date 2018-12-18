package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspPrimarySuffix extends AspSyntax{
  static boolean parseSjekk = false;
  public AspSubscription as = null;
  public AspArguments arg = null;

  AspPrimarySuffix(int n){
    super(n);
  }

  static AspPrimarySuffix parse(Scanner s){
    Main.log.enterParser("primary suffix");
    AspPrimarySuffix apeker = null;

    if(s.curToken().kind == leftParToken){

      parseSjekk = true;

      AspArguments tmp1 = AspArguments.parse(s);
      apeker = tmp1;
      apeker.arg = tmp1;

    }else if(s.curToken().kind == leftBracketToken){
      parseSjekk = true;
      AspSubscription tmp = AspSubscription.parse(s);
      apeker = tmp;
      apeker.as = tmp;
    }else{
      parserError("Forvente en primary suffix, men fant noe rart som: " + s.curToken().kind, s.curLineNum());
    }


    Main.log.leaveParser("primary suffix");
    return apeker;
  }


}//slutt
