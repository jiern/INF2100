package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.main.*;
import java.util.*;

public class RuntimeFloatValue extends RuntimeValue{
  double floatValue;
  boolean cond;


  //evalPositive, evalNegate
  //evalNotEqual
  //evalLessEqual, evalGreater, evalGreaterEqual og evalNot
  //DETTE SKAL INT OGSÅ HA

  public RuntimeFloatValue(double v){
    floatValue = v;
    cond = true;
  }

  @Override
  protected String typeName(){
    return "float";
  }

  @Override
  public String showInfo(){
    return toString();
  }
  @Override
  public String toString(){
    return String.valueOf(floatValue);
  }


  @Override
  public double getFloatValue(String what, AspSyntax where) {
    return floatValue;
  }


  @Override
  public long getIntValue(String what, AspSyntax where) {
    return (long)floatValue;
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where){
    return cond;
  }


  @Override
  public RuntimeValue evalPositive(AspSyntax where){
    return new RuntimeFloatValue(floatValue);

    // på negate er det floatvale * -1
  }


  @Override
  public RuntimeValue evalNegate(AspSyntax where){
    return new RuntimeFloatValue(floatValue * -1);
  }
  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("+ operand", where);
      res = new RuntimeFloatValue(floatValue + v2);
    } else if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("+ operand", where);
      res = new RuntimeFloatValue(floatValue + v2);
    } else {
      runtimeError("Type error for +.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("/ operand", where);
      res = new RuntimeFloatValue(floatValue / v2);
    } else if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("/ operand", where);
      res = new RuntimeFloatValue(floatValue / v2);
    } else {
      runtimeError("Type error for /.", where);
    }
    return res;
  }


  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("* operand", where);
      res = new RuntimeFloatValue(floatValue * v2);
    } else if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("* operand", where);
      res = new RuntimeFloatValue(floatValue * v2);
    } else {
      runtimeError("Type error for *.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("- operand", where);
      res = new RuntimeFloatValue(floatValue - v2);
    } else if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("- operand", where);
      res = new RuntimeFloatValue(floatValue - v2);
    } else {
      runtimeError("Type error for -.", where);
    }
    return res;
  }


  @Override
  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("// operand", where);
      res = new RuntimeFloatValue(Math.floor(floatValue/v2));
    } else if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("// operand", where);
      res = new RuntimeFloatValue(Math.floor(floatValue/v2));
    } else {
      runtimeError("Type error for //.", where);
    }
    return res;
  }


  @Override
  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("% operand", where);
      res = new RuntimeFloatValue((floatValue - v2) * (Math.floor(floatValue/v2)));
    } else if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("% operand", where);
      res = new RuntimeFloatValue((floatValue - v2) * (Math.floor(floatValue/v2)));
    } else{
      runtimeError("Type error for %.", where);
    }
    return res;
  }


  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("== operand", where);
      if(floatValue == v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("== operand", where);
      if(floatValue == v2){
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
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("!= operand", where);
      if(floatValue != v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("!= operand", where);
      if(floatValue != v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else {
      runtimeError("Type error for !=.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("< operand", where);
      if(floatValue < v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("< operand", where);
      if(floatValue < v2){
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
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("> operand", where);
      if(floatValue > v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("> operand", where);
      if(floatValue > v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    }else {
      // Brukerfeil: Feil i innsendt asp-program
      runtimeError("Type error for >.", where);
    }
    return res;
  }
  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("<= operand", where);
      if(floatValue <= v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("<= operand", where);
      if(floatValue <= v2){
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
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue(">= operand", where);
      if(floatValue >= v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue(">= operand", where);
      if(floatValue >= v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else {
      runtimeError("Type error for >=.", where);
    }
    return res;
  }
}
