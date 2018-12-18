package no.uio.ifi.asp.runtime;

// For part 4:

public class RuntimeReturnValue extends Exception {
    public RuntimeValue value;

    public RuntimeReturnValue(RuntimeValue v) {
	value = v;
    }
}
