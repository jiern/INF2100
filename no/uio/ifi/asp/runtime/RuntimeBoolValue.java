package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeBoolValue extends RuntimeValue {
  boolean boolValue;

  public RuntimeBoolValue(boolean v) {
    boolValue = v;
  }

  @Override
  protected String typeName() {
    return "boolean";
  }

  public String showInfo() {
    return toString();
  }


  @Override
  public String toString() {
    return (boolValue ? "True" : "False");
  }


  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    return boolValue;
  }


  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    } else {
      return new RuntimeBoolValue(
      boolValue == v.getBoolValue("== operand",where));
    }
  }


  @Override
  //nå er vi i boolvalue, evalnot i RuntimeBoolValue er lovlig.
  //da lager vi en ny RuntimeBoolValue som er det motsatte av boolValue.
  //da har vi et nytt objekt med den nye verdien
  public RuntimeValue evalNot(AspSyntax where) {
    return new RuntimeBoolValue(! boolValue);
  }


  @Override
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(true);
    } else {
      return new RuntimeBoolValue(
      boolValue != v.getBoolValue("!= operand",where));
    }
  }
}
