package no.uio.ifi.asp.runtime;
import java.util.*;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue{
  HashMap<String,RuntimeValue> dictValue = new HashMap<String, RuntimeValue>();
  boolean boolValue;

  public RuntimeDictValue(HashMap<String,RuntimeValue> v){
    dictValue = v;

    if(!dictValue.isEmpty()){
      boolValue = true;
    }else{
      boolValue = false;
    }
  }


  @Override
  protected String typeName(){
    return "dict";
  }

  @Override
  public String showInfo() {
    return String.valueOf(dictValue);
  }

  //showInfo er redefinert her siden disse kan inneholde tekster.
  //se RuntimeStringValue


  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    return boolValue;
  }

  @Override
  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {

    RuntimeValue res = null;

    if(v instanceof RuntimeStringValue){
      RuntimeStringValue realnokkel = (RuntimeStringValue) v;
      String streng = realnokkel.strValue;

      for(String nokkel: dictValue.keySet()){
        if(nokkel.equalsIgnoreCase(streng)){
          res = dictValue.get(streng);
        }
      }

    }else{
      runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
    }
    return res;  // Required by the compiler!
  }
  public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
    return;
  }

}//slutt
