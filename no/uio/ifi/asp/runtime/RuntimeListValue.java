package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

import java.util.ArrayList;

public class RuntimeListValue extends RuntimeValue{
  //showInfo er redefinert her siden disse kan inneholde tekster.
  //se RuntimeStringValue

  boolean boolValue;
  ArrayList<RuntimeValue> list = new ArrayList<>();

  public RuntimeListValue(ArrayList<RuntimeValue> list){
    this.list = list;
    if(!list.isEmpty()){
      boolValue = true;
    }else{
      boolValue = false;
    }
  }
  @Override
  public ArrayList<RuntimeValue> getList(String what, AspSyntax where){
    return list;
  }

  @Override
  protected String typeName(){
    return "List";
  }

  @Override
  public String showInfo() {
    String retur = null;
    String midlertidig = null;
    if(boolValue){
      midlertidig = "[";

      for(int i = 0; i < list.size(); i++) {
        if (list.get(i).equals("String")) {
          midlertidig += "'";
          midlertidig += list.get(i);
          midlertidig += "'";
        }else{
          midlertidig += list.get(i);
        }
        midlertidig += ", ";
      }
      midlertidig += "]";
    }else {
      midlertidig = "[]";
    }
    retur = midlertidig;
    return  String.valueOf(retur);

  }

  @Override
  public String toString(){
    return String.valueOf(list);
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    return boolValue;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    ArrayList<RuntimeValue> nyliste = new ArrayList<>();

    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("* operand", where);
      int antall = 0;
      ArrayList<RuntimeValue> temp = new ArrayList<>();
      while(antall < v2) {
        for (int i = 0; i < list.size(); i++) {
          temp.add(list.get(i));
        }
        antall++;
      }
      res = new RuntimeListValue(temp);

    } else {
      runtimeError("Type error for Multiply.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {

    RuntimeValue res = null;

    if(v instanceof RuntimeIntValue){
      long indexLong = v.getIntValue("Index", where);
      int index = (int) indexLong;
      res = list.get(index);

    }else{
      runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
    }
    return res;  // Required by the compiler!
  }

  public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
    RuntimeValue res = null;
    if(inx instanceof RuntimeIntValue){
      int i = (int)((RuntimeIntValue)inx).intValue;
      list.set(i, val);
    }else{
      RuntimeValue.runtimeError("Type error for list " ,where);
    }
  }
}
