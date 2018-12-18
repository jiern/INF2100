package no.uio.ifi.asp.runtime;

import java.util.*;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.*;
import no.uio.ifi.asp.runtime.*;

public class RuntimeFunc extends RuntimeValue{
  AspFuncDef def;
  RuntimeScope defScope;
  String name;

  public RuntimeFunc(String name){
    this.name = name;
    this.defScope = defScope;
  }

  public RuntimeFunc(AspFuncDef def, RuntimeScope defScope, String name){
    this.def = def;
    this.defScope = defScope;
    this.name = name;
  }

  protected String typeName(){
    return "Func";
  }

  public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {

    int formalsParams = def.getNames().size()-1;
    //(a) Sjekk at antallet aktuelle parametre er det samme som antallet formelle parametre.
    if(formalsParams == actualParams.size()){
      RuntimeScope skop = new RuntimeScope(defScope);
      //(b) Opprett et nytt RuntimeScope-objekt. Dette skopets outer skal være det skopet der funksjonen ble deklarert.

      //(c) Gå gjennom parameterlisten og tilordne alle de aktuelle parameterverdiene til de formelle parametrene.
      for (int i = 0 ; i < actualParams.size(); i++){
        skop.assign(def.getNames().get(i+1).getName(), actualParams.get(i));

      }
      try {
        //(d) Kall funksjonens runFunction (med det nye skopet som parameter) slik at den kan utføre innmaten av funksjonen.
        def.as.eval(skop);
      } catch (RuntimeReturnValue rrv) {
        return rrv.value;
      }
    }else{
      RuntimeValue.runtimeError("Wrong " + name + "!",where);
    }
    return null;
  }

}
