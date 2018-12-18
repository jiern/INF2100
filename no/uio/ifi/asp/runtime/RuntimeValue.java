package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public abstract class RuntimeValue {
    abstract protected String typeName();

    public String showInfo() {
	return toString();
//i de fleste subklassen kan vi bare bruke denne som den er
//men ikke i String. den har redefinert showinfo til å skrive ut tekstverden med annførselstegn RuntimeDictValue
// enkle eller doble. det sjekker den.
//også RuntimeDictValue og RuntimeListValue må redefineres

    }

    public ArrayList<RuntimeValue> getList(String what, AspSyntax where){
        runtimeError("Dette er feil", where);
        return null;
    }

    // For parts 3 and 4:

    public boolean getBoolValue(String what, AspSyntax where) {
	runtimeError("Type error: "+what+" is not a Boolean!", where);
	return false;  // Required by the compiler!
    }

    public double getFloatValue(String what, AspSyntax where) {
	runtimeError("Type error: "+what+" is not a float!", where);
	return 0.0;  // Required by the compiler!
    }

    public long getIntValue(String what, AspSyntax where) {
	runtimeError("Type error: "+what+" is not an integer!", where);
	return 0;  // Required by the compiler!
    }

    public String getStringValue(String what, AspSyntax where) {
	runtimeError("Type error: "+what+" is not a text string!", where);
	return null;  // Required by the compiler!
    }

    // For part 3:

//pluss er udefinert
// where = parameter. hvor skjedde det?
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
	runtimeError("'+' undefined for "+typeName()+"!", where);
//kommer aldri til return, for runtimeerror vil stoppe det hele
  return null;  // Required by the compiler!
    }

    //vi tar utganspunkt i at alt er feil :)))
    //skriver vi "xx" / 4, sender over som et uttrykk
    //vi finner /, kaller på evaldevide, den andre er 4
    //vi får en runtimefeil, pga vi har en string før /.


//heltalls divisjon med dobbel skråstrek
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
	runtimeError("'/' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }

    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
	runtimeError("'==' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }

    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
	runtimeError("'>' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }

    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
	runtimeError("'>=' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }

    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
	runtimeError("'//' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }

    public RuntimeValue evalLen(AspSyntax where) {
	runtimeError("'len' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }

    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
	runtimeError("'<' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }

    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
	runtimeError("'<=' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }


// %
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
	runtimeError("'%' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }

    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
	runtimeError("'*' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }


// -4
    public RuntimeValue evalNegate(AspSyntax where) {
	runtimeError("Unary '-' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }


//skal bare jobbe på seg selv, ikke en ekstra runtimevalue parameter
    public RuntimeValue evalNot(AspSyntax where) {
	runtimeError("'not' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }



    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
	runtimeError("'!=' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }


//unærpluss, skal gi en pluss tilbake
    public RuntimeValue evalPositive(AspSyntax where) {
	runtimeError("Unary '+' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }


//alle kaller på runtimeerror
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
	runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }

    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
	runtimeError("'-' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }

    // General:

    public static void runtimeError(String message, AspSyntax where) {
	String m = "Asp runtime error on line " + where.lineNum + ": " + message;
	Main.error(m);
    }

    // For part 4:

    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
	runtimeError("subscription undefined for "+typeName()+"!", where);
    }

    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,  AspSyntax where) {
	runtimeError("'Function call (...)' undefined for "+typeName()+"!", where);
	return null;  // Required by the compiler!
    }
}
