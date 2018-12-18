package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.main.*;
import java.util.*;
public class RuntimeIntValue extends RuntimeValue{
  long intValue;
  boolean cond;

  public RuntimeIntValue(long v){
    intValue = v;
    cond = true;
  }

  @Override
  protected String typeName(){
    return "int";
  }
  @Override
  public String showInfo(){
    return toString();
  }

  @Override
  public String toString(){
    return String.valueOf(intValue);
  }


  @Override
  public long getIntValue(String what, AspSyntax where) {
    return intValue;
  }

  @Override
  public double getFloatValue(String what, AspSyntax where) {
    return (double)intValue;
  }
  @Override
  public boolean getBoolValue(String what, AspSyntax where){
    return cond;
  }


  @Override
  public RuntimeValue evalPositive(AspSyntax where){
    return new RuntimeIntValue(intValue);
  }
  @Override
  public RuntimeValue evalNegate(AspSyntax where){
    return new RuntimeIntValue(intValue * -1);
  }

  //vil kaller på evalAdd, og den opprinnelige ligger i RuntimeValue
  //som bare inneholder en feilmelding. men her inni intvalue, der heltallet ligger
  //her har vi en ekte evalAdd, som gjør jobben sin. (lagret som en long).
  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
    //vi er inni i RuntimeIntValue
    //og nå må vi teste, for kan være flere lovlige tillfeller.
    //vi vet at første er en int, siden vi er inni int.
    //hva er lovlig? int + int
    RuntimeValue res = null;
    //sjekker om v er instanceof RuntimeIntValue
    if(v instanceof RuntimeIntValue){
      //da vet vi at det er en int value
      //med getIntValue får vi tak i den andre operanden også legger vi de to sammen
      long v2 = v.getIntValue("+ operand", where);
      res = new RuntimeIntValue(intValue + v2); // int value PLUSS v2 gir en ny long verdi
      //nytt RuntimeIntValue, da lager vi et nytt objekt

      //kan også kan int + float
      //sjekker om v er instanceOf RuntimeFloatValue
    } else if(v instanceof RuntimeFloatValue){
      //da lager vi en double, gjør det samme som ovenfor,
      //men sender med i getFloatValue isteden.
      double v2 = v.getFloatValue("+ operand", where);
      res = new RuntimeFloatValue(intValue + v2);

    } else{
      //typefeil
      //int + noe annet, er ikke lov.
      runtimeError("Type error for +.", where);
      //runtimeError er der for å fortelle oss at BRUKEREN har gjort en feil når de programerte i Asp
      //mens panic er en melding til oss selv at VI har gjort noe feil underveis.
    }
    return res;
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("/ operand",where);
      res = new RuntimeFloatValue(intValue / v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("/ operand",where);
      res = new RuntimeFloatValue(intValue / v2);
    } else {
      runtimeError("Type error for /.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("* operand",where);
      res = new RuntimeIntValue(intValue * v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("* operand",where);
      res = new RuntimeFloatValue(intValue * v2);
    } else {
      runtimeError("Type error for *.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("- operand",where);
      res = new RuntimeIntValue(intValue - v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("- operand",where);
      res = new RuntimeFloatValue(intValue - v2);
    } else {
      runtimeError("Type error for -.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("// operand", where);
      res = new RuntimeIntValue(Math.floorDiv(intValue,v2));
    } else if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("// operand", where);
      res = new RuntimeFloatValue(Math.floor(intValue/v2));
    } else {
      runtimeError("Type error for //.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("% operand", where);
      res = new RuntimeIntValue(Math.floorMod(intValue, v2));
    } else if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("% operand", where);
      res = new RuntimeFloatValue(intValue-v2*(Math.floor(intValue/v2)));
    } else {
      runtimeError("Type error for %.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("== operand", where);
      if(intValue == v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("== operand", where);
      if(intValue == v2){
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
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("< operand", where);
      if(intValue < v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("< operand", where);
      if(intValue < v2){
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
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("!= operand", where);
      if(intValue != v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("!= operand", where);
      if(intValue != v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    }else if(v instanceof RuntimeNoneValue){
      res = new RuntimeBoolValue(false);

    } else {
      runtimeError("Type error for !=.", where);
    }
    return res;
  }
  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){

    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("<= operand", where);
      if(intValue <= v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("<= operand", where);
      if(intValue <= v2){
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
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue(">= operand", where);
      if(intValue >= v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue(">= operand", where);
      if(intValue >= v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else {
      runtimeError("Type error for >=.", where);
    }
    return res;
  }
  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("> operand", where);
      if(intValue > v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("> operand", where);
      if(intValue > v2){
        res = new RuntimeBoolValue(true);
      } else {
        res = new RuntimeBoolValue(false);
      }
    } else {
      runtimeError("Type error for >.", where);
    }
    return res;
  }
}
