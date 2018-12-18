package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
public class RuntimeStringValue extends RuntimeValue {
  String strValue;
  boolean cond;

  public RuntimeStringValue(String v){
    this.strValue = v;
    if(v.equals("")){
      this.cond = false;
    }else{
      this.cond = true;
    }
  }

  @Override
  public String showInfo() {
    if (strValue.indexOf('\'') >= 0){
      return "\"" + strValue + "\"";
    }else{
      return "’" + strValue + "’";
    }
  }

  @Override
  public String toString(){
    return String.valueOf(strValue);
  }

  @Override
  protected String typeName(){
    return "String";
  }
  public String getStringValue(String what, AspSyntax where) {
    return strValue;
  }
  @Override
  public boolean getBoolValue(String what, AspSyntax where){
    return cond;
  }

  @Override
  public long getIntValue(String what, AspSyntax where) {
    return Integer.parseInt(strValue);
  }
  @Override
  public double getFloatValue(String what, AspSyntax where){
    return Float.parseFloat(strValue);
  }





  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeStringValue){
      String v2 = v.getStringValue("+ operand", where);
      res = new RuntimeStringValue(strValue + v2);
    } else {
      runtimeError("Type error for +.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("* operand", where);
      int antall = 0;
      String tmp = "";
      while(antall < v2){
        tmp += strValue;
        antall++;
      }
      res = new RuntimeStringValue(tmp);
    } else{
      runtimeError("Type error for *.", where);
    }
    return res;
  }


  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeStringValue){
      String v2 = v.getStringValue("== operand", where);
      if(strValue.equals(v2)){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    }else if(v instanceof RuntimeNoneValue){
      res = new RuntimeBoolValue(false);
    } else {
      runtimeError("Type error for ==.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if(v instanceof RuntimeStringValue){
      String v2 = v.getStringValue("< operand", where);
      if(strValue.compareTo(v2) < 0){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else {
      runtimeError("Type error for <.", where);
    }
    return res;
  }


  @Override
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeStringValue){
      String v2 = v.getStringValue("!= operand", where);
      if(!strValue.equals(v2)){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    }else if (v instanceof RuntimeIntValue){
      res = new RuntimeBoolValue(false);

    } else {
      runtimeError("Type error for !=.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if(v instanceof RuntimeStringValue){
      String v2 = v.getStringValue("== operand", where);
      if(strValue.compareTo(v2) == 1){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else {
      runtimeError("Type error for <.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if(v instanceof RuntimeStringValue){
      String v2 = v.getStringValue(">= operand", where);
      if(strValue.compareTo(v2) == 1 || strValue.compareTo(v2) == 0){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else {
      runtimeError("Type error for <=.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if(v instanceof RuntimeStringValue){
      String v2 = v.getStringValue("<= operand", where);
      if(strValue.compareTo(v2) == -1 || strValue.compareTo(v2) == 0){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else {
      runtimeError("Type error for <=.", where);
    }
    return res;
  }

  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    String retur = "";

    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("int", where);
      int index = (int) v2;
      char c = strValue.charAt(index);
      retur = "" + c;
      res = new RuntimeStringValue(retur);

    }else{
      runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
    }
    return res;  // Required by the compiler!
  }

  @Override
  public RuntimeValue evalLen(AspSyntax where){
    RuntimeIntValue res = new RuntimeIntValue(strValue.length());
    return res;
  }

  @Override
  public RuntimeValue evalNot(AspSyntax where){
    RuntimeValue res = new RuntimeBoolValue(cond);
    return res;
  }
}
