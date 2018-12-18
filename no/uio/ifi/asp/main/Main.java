package no.uio.ifi.asp.main;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.AspProgram;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Main {
    public static final String version = "2017-08-22";

    public static LogFile log = null;

    public static void main(String arg[]) {
      // lager 2 strings filName og baseFolename og setter dem til null
	String fileName = null, baseFilename = null;
  //oppretter booleans
	boolean testExpr = false, testParser = false, testScanner = false,
	    logE = false, logP = false, logS = false, logY = false;

	System.out.println("This is the Ifi Asp interpreter (" +
			   version + ")");


//saa lenge i er mindre enn arg sin lengde, saa skal String a, vaere arg[indeks], saa plusse paa 1
//og saa fortsette gjennom arg.
	for (int i = 0;  i < arg.length;  i++) {
	    String a = arg[i];

//alle disse under ble opprettet som booleans ovenfor og satt til false. Her sjekker vi hva
//a er ved aa teste for hver av booleanene, og hvis det er en match, saa setter vi booleanen til aa vaere true
	    if (a.equals("-logE")) {
        //hvis a equals -logE, saa setter vi LogE til aa vaere true
		logE = true;
	    } else if (a.equals("-logP")) {
		logP = true;
	    } else if (a.equals("-logS")) {
		logS = true;
	    } else if (a.equals("-logY")) {
		logY = true;
	    } else if (a.equals("-testexpr")) {
		testExpr = true;
	    } else if (a.equals("-testparser")) {
		testParser = true;
	    } else if (a.equals("-testscanner")) {
		testScanner = true;
	    } else if (a.startsWith("-")) {
        //hvis a starter med en "-", kjoorer vi usage() som printer ut en error
		usage();
	    } else if (fileName != null) {
        //samme hvis fileName IKKE er  null, kjoorer usage()
		usage();
	    } else {
        //hvis ingen av de over stemmer, setter vi filName til aa vaere a
		fileName = a;
	    }
	}
  //HER SLUTTER IF

	if (fileName == null) usage();
//Hvis fileName er null, kjoorer usage()
//saa setter vi BaseFilename(som ble opprettet i starten av klassen og ble satt til 0)
//til aa veare filName.
//fileName er enten null, eller a.
	baseFilename = fileName;
	if (baseFilename.endsWith(".asp"))
  //hvis baseFilename ender med .asp, altsaa en aspfil
	    baseFilename = baseFilename.substring(0,baseFilename.length()-4);
      //saa skal baseFilename vaere lik baseFilename sin substring 0 sin lengde minus 4.
	else if (baseFilename.endsWith(".py"))
  //hvis baseFilename ender med .py, altsaa en pythonfil
	    baseFilename = baseFilename.substring(0,baseFilename.length()-3);
      //saa skal baseFilename vaere lik baseFilename sin substring 0 sin lengde minus 3.
//log er static, og er null
	log = new LogFile(baseFilename+".log");
  //log skal naa vaere en ny LogFile ned baseFilname sin String, + .log
	if (logE || testExpr) log.doLogEval = true;
  //hvis logE eller testExpr
	if (logP || testParser) log.doLogParser = true;

	if (logS || testScanner) log.doLogScanner = true;

	if (logY || testExpr || testParser) log.doLogPrettyPrint = true;


//Lager en scanner s med fileName
	Scanner s = new Scanner(fileName);
  //hvis det er testScanner
	if (testScanner)
  //saa sender vi s inn i metoden doTestScanner
	    doTestScanner(s);
	else if (testParser)
  //hvis det er testParse, sender vi s inn i doTestParser
	    doTestParser(s);
      //hvis det er testExpr, sender vi s inn i doTestExpr
	else if (testExpr)
	    doTestExpr(s);
	else
  //hvis ingen av de over stemmer sender vi s inn i doRunInterpreter
	    doRunInterpreter(s);


//hvis log ikke er null, kjoorer vi finish paa log.
	if (log != null) log.finish();
  //og avslutter
	System.exit(0);
    }


    private static void doTestScanner(Scanner s) {
	do {
    //sender s med i readNextToken, som ligger i klassen Scanner.java
	    s.readNextToken();
      //saa lenge s.curToken(som ligger i scanner.java) ikke er eofToken
	} while (s.curToken().kind != eofToken);
    }


    private static void doTestParser(Scanner s) {

      //lager en AspPorgram prog, og sender s inn i parse
	AspProgram prog = AspProgram.parse(s);
	if (log.doLogPrettyPrint)
	    prog.prettyPrint();
    }


    private static void doTestExpr(Scanner s) { //!!! DEL 3

	ArrayList<AspExpr> exprs = new ArrayList<>();
	while (s.curToken().kind != eofToken) {

	    exprs.add(AspExpr.parse(s));
	    AspExpr.skip(s, newLineToken);
	}

	RuntimeScope emptyScope = new RuntimeScope();
	for (AspExpr e: exprs) {
	    e.prettyPrint();  log.prettyWriteLn(" ==>");
	    try {
		RuntimeValue res = e.eval(emptyScope);
		log.traceEval(res.showInfo(), e); //nullpointer her
	    } catch (RuntimeReturnValue rrv) {
		panic("Uncaught return value!");
	    }
	}
    }


    private static void doRunInterpreter(Scanner s) {
	AspProgram prog = AspProgram.parse(s);
	if (log.doLogPrettyPrint){
	    prog.prettyPrint();
}
	RuntimeScope lib = new RuntimeLibrary();
	RuntimeScope globals = new RuntimeScope(lib);
	try {
	    prog.eval(globals);
	} catch (RuntimeReturnValue rrv) {
	    panic("Uncaught return value!");
	}
    }


    public static void error(String message) {
	System.out.println();
	System.err.println(message);
	if (log != null) log.noteError(message);
	System.exit(1);
    }


    public static void panic(String message, int lineNum) {
	String m = "*** ASP PANIC";
	if (lineNum > 0) m += " ON LINE " + lineNum;
	m += " ***: " + message;
	error(m);
    }


    public static void panic(String message) {
	panic(message, 0);
    }


    private static void usage() {
	error("Usage: java -jar asp.jar " +
	    "[-log{E|P|S|Y}] [-test{expr|parser|scanner}] file");
    }
}
