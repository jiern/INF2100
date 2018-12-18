package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
	private LineNumberReader sourceFile = null;
	private String curFileName;
	private ArrayList<Token> curLineTokens = new ArrayList<>();
	private ArrayList<Token> ikkeibruk = new ArrayList<>();
	private int indents[] = new int[100];
	private int numIndents = 0;
	private final int tabDist = 4;
	private int indentTeller = 0;
	private Token denneToken = null;


	public Scanner(String fileName) {
		curFileName = fileName;
		indents[0] = 0;  numIndents = 1;

		try {
			sourceFile = new LineNumberReader(
			new InputStreamReader(
			new FileInputStream(fileName),
			"UTF-8"));
		} catch (IOException e) {
			scannerError("Cannot read " + fileName + "!");
		}
	}


	private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
		m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
	}


	public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
		return curLineTokens.get(0);
	}


	public void readNextToken() {
		if (!curLineTokens.isEmpty()){
			curLineTokens.remove(0);
		}
	}


	public boolean anyEqualToken() {
		for (Token t: curLineTokens) {
			if (t.kind == equalToken) return true;
		}
		return false;
	}


	private void readNextLine() {
		curLineTokens.clear();

		// Read the next line:
		String line = null;
		try {
			line = sourceFile.readLine();
			if (line == null) {
				if(indentTeller != 0){
					for(int teller = 0; teller < indents.length; teller++) {
						if(indents[teller] != 0) {
							curLineTokens.add(new Token(dedentToken));
						}
					}
				}
				curLineTokens.add(new Token(eofToken));
				sourceFile.close();
				sourceFile = null;
				for (Token t: curLineTokens)
				Main.log.noteToken(t);
				//System.exit(0);
				return;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}
		//-- Must be changed in part 1:

		String linje = expandLeadingTabs(line);
		int antallMellomrom = findIndent(linje);
		char[] linjeArray = linje.toCharArray();
		indentTeller = antallMellomrom;
		int blanks = 0;
		String token = "";


		//Ved mellomrom eller hashtag, ignoreres den. Ellers gaar vi gjennom linje og sjekker for mellomrom.
		if(linje.trim().isEmpty()) {
			//System.out.println("Tom Linje " + curLineNum());
			return;
		} else if (linjeArray[antallMellomrom] == '#') {
			//System.out.println("Hastag taper " + curLineNum());
			return;
		} else {
			//System.out.println("Mellomrom " + curLineNum());
			int i = 0;
			if (linjeArray[i] == ' ') {
				i++;
				while (linjeArray[i] == ' ') {
					i++;
				}

				/*System.out.println("Debug linje size: " +
				linjeArray.length +
				" index: " + i +
				" cur: " + curLineNum() +
				" Innhold: " + linjeArray[i]);
				*/
			}
			//Sjekker om det er indent eller dedent. Kaller saa paa push og pop funksjonene.
			if (indentTeller > indents[numIndents - 1]) {
				push(indentTeller);
				curLineTokens.add(new Token(indentToken, curLineNum()));
			}

			while (indentTeller < indents[numIndents - 1]) {
				pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}


			//midlertidig er strengen vi konkatenerer og nullstiller etter hvilket tilfelle den inntreffer.
			String midlertidig = "";

			//Sjekker om posisjonen er en array eller digit. Ved en mellomrom, stoppes lokka for da slutter ordet.
			while (i < linjeArray.length) {
				boolean flyttall = false;


				if(linjeArray[i] == ' '){
					while(linjeArray[i] == ' '){
						i++;
					}
				}

				if(isDigit(linjeArray[i])){
					for(int punktum = i; punktum < linjeArray.length; punktum++) {
						if(linjeArray[punktum] == ' '){
							break;
						}
						else if (linjeArray[punktum] == '.') {
							flyttall = true;
						}
					}
				}

				if (isLetterAZ(linjeArray[i])) {
					/*
					midlertidig += linjeArray[i];

					//For aa itere til neste bokstav, ved konkatenering.
					if(i < linjeArray.length-1){
					i++;
				}
				*/

				for(int j = i; j < linjeArray.length; j++){

					if (linjeArray[j] == ' '){
						break;
					}else if(linjeArray[j] == '_'){
						midlertidig += linjeArray[j];
						i =j;

					}else if(isDigit(linjeArray[j])){
						midlertidig += linjeArray[j];
						i = j;
					}else if(!isLetterAZ(linjeArray[j])) {
						break;
					}else{
						//Sjekker tilfelle der j = lengden til arrayet saa slutt ellers lager det duplikat

						midlertidig += linjeArray[j];
						i = j;
					}

				}



				//her skal jeg sjekke imot statement.
				if (midlertidig.equals("def")) {
					Token deffern = new Token(defToken, curLineNum());
					curLineTokens.add(deffern);
					i++;
					midlertidig = "";

				} else if (midlertidig.equals("if")) {
					Token iffern = new Token(ifToken, curLineNum());
					curLineTokens.add(iffern);
					i++;
					midlertidig = "";
				} else if (midlertidig.equals("else")) {
					Token elsern = new Token(elseToken, curLineNum());
					curLineTokens.add(elsern);
					if(isLetterAZ(linjeArray[i])) {
						i++;
					}
					midlertidig = "";
				} else if (midlertidig.equals("elif")) {
					Token elifern = new Token(elifToken, curLineNum());
					curLineTokens.add(elifern);
					i++;
					midlertidig = "";
				} else if (midlertidig.equals("while")) {
					Token whilern = new Token(whileToken, curLineNum());
					curLineTokens.add(whilern);
					i++;
					midlertidig = "";
				}else if(midlertidig.equals("return")){
					Token returnern = new Token(returnToken, curLineNum());
					curLineTokens.add(returnern);
					i++;
					midlertidig = "";
				}else if(midlertidig.equals("not")){
					Token not = new Token(notToken, curLineNum());
					curLineTokens.add(not);
					i++;
					midlertidig = "";
				}else if(midlertidig.equals("False")){
					Token falskt = new Token(falseToken, curLineNum());
					curLineTokens.add(falskt);
					i++;
					midlertidig = "";
				}else if(midlertidig.equals("or")){
					Token eller = new Token(orToken, curLineNum());
					curLineTokens.add(eller);
					i++;
					midlertidig = "";
				}else if(midlertidig.equals("and")){
					Token og = new Token(andToken, curLineNum());
					curLineTokens.add(og);
					i++;
					midlertidig = "";

				}else if(midlertidig.equals("True")){
					Token sant = new Token(trueToken, curLineNum());
					curLineTokens.add(sant);
					i++;
					midlertidig = "";



				}else{
					//Hvis ingen av de stemmer, saa lages nameToken.
					Token name = new Token(nameToken, curLineNum());
					name.name = midlertidig;
					curLineTokens.add(name);
					midlertidig = "";

					if(isLetterAZ(linjeArray[i]) || isDigit(linjeArray[i])) {
						//System.out.println("o/ker");
						i++;
					}

				}//slutt for disse statementene

			} else {

				if (linjeArray[i] == '=') {
					String tegnToken = linjeArray[i] + "" + linjeArray[i + 1];
					if (!lagTegnToken(tegnToken, linjeArray[i-1], curLineNum())) {
						Token likhet = new Token(equalToken, curLineNum());
						curLineTokens.add(likhet);
						midlertidig = "";
						i++;
					} else {
						//midlertidig = "";
						i = i + 2;
					}


				} else if (linjeArray[i] == '"') {
					midlertidig = "";
					i++;
					while(linjeArray[i] != '"') {
						midlertidig += linjeArray[i];
						i++;
					}
					Token strliteral = new Token(stringToken, curLineNum());
					strliteral.stringLit = midlertidig;
					curLineTokens.add(strliteral);
					i++;
					midlertidig = "";

				}else if(linjeArray[i] == '\''){
					midlertidig = "";
					i++;
					while(linjeArray[i] != '\''){
						midlertidig += linjeArray[i];
						i++;
					}
					Token strliteral = new Token(stringToken, curLineNum());
					strliteral.stringLit = midlertidig;
					curLineTokens.add(strliteral);
					i++;
					midlertidig = "";

				} else if (linjeArray[i] == '(') {
					Token leftpara = new Token(leftParToken, curLineNum());
					curLineTokens.add(leftpara);
					i++;
					midlertidig = "";

					//System.out.println("Vi fikk ut: " + midlertidig);

				} else if (linjeArray[i] == ')') {
					Token rightpara = new Token(rightParToken, curLineNum());
					curLineTokens.add(rightpara);
					i++;
					midlertidig = "";

				}else if (linjeArray[i] == '{') {
					Token leftkrollern = new Token(leftBraceToken, curLineNum());
					curLineTokens.add(leftkrollern);
					i++;
					midlertidig = "";

				}else if (linjeArray[i] == '}') {
					Token rightkrollern = new Token(rightBraceToken, curLineNum());
					curLineTokens.add(rightkrollern);
					i++;
					midlertidig = "";

				}else if (linjeArray[i] == '[') {
					Token leftbrakkern = new Token(leftBracketToken, curLineNum());
					curLineTokens.add(leftbrakkern);
					i++;
					midlertidig = "";
				}else if (linjeArray[i] == ']') {
					Token rightbrakkern = new Token(rightBracketToken, curLineNum());
					curLineTokens.add(rightbrakkern);
					i++;
					midlertidig = "";
				}else if (linjeArray[i] == '*') {
					Token gangern = new Token(astToken, curLineNum());
					curLineTokens.add(gangern);
					i++;
					midlertidig = "";

				}else if (linjeArray[i] == '+') {
					Token plussern = new Token(plusToken, curLineNum());
					curLineTokens.add(plussern);
					i++;
					midlertidig = "";

				}else if (linjeArray[i] == '%') {
					Token prosentern = new Token(percentToken, curLineNum());
					curLineTokens.add(prosentern);
					i++;
					midlertidig = "";

				} else if(linjeArray[i] == ':') {
					Token kolonern = new Token(colonToken, curLineNum());
					curLineTokens.add(kolonern);
					i++;
					midlertidig = "";
				}else if(linjeArray[i] == '/' && linjeArray[i+1] == '/'){
					Token dSlash = new Token(doubleSlashToken, curLineNum());
					curLineTokens.add(dSlash);
					i++;
					midlertidig = "";
				}else if(linjeArray[i] == '/' && linjeArray[i-1] != '/' ){
					Token slash = new Token(slashToken, curLineNum());
					curLineTokens.add(slash);
					i++;
					midlertidig = "";

				}else if(linjeArray[i] == '<' && linjeArray[i+1] == '='){
					Token less = new Token(lessEqualToken, curLineNum());
					curLineTokens.add(less);
					i++;
					midlertidig = "";

				} else if(linjeArray[i] == '>' && linjeArray[i+1] == '='){
					Token greater = new Token(greaterEqualToken, curLineNum());
					curLineTokens.add(greater);
					i++;
					midlertidig = "";
				}else if(linjeArray[i] == '!' && linjeArray[i+1] == '='){
					Token ikkelik = new Token(notEqualToken, curLineNum());
					curLineTokens.add(ikkelik);
					i++;
					midlertidig = "";
				}else if(linjeArray[i] == '<'){
					Token mindrern = new Token(lessToken, curLineNum());
					curLineTokens.add(mindrern);
					i++;
					midlertidig = "";

				}else if(linjeArray[i] == '>'){
					Token storrern = new Token(greaterToken, curLineNum());
					curLineTokens.add(storrern);
					i++;
					midlertidig = "";





				}else if(linjeArray[i] == ','){
					Token komma = new Token(commaToken, curLineNum());
					curLineTokens.add(komma);
					i++;
					midlertidig = "";
				} else if(linjeArray[i] == '-'){
					Token minus = new Token(minusToken, curLineNum());
					curLineTokens.add(minus);
					i++;
					midlertidig = "";




				}else if(isDigit(linjeArray[i]) && !flyttall){
					//System.out.println("Vi er i integer med tallet: " + linjeArray[i] + " lengden: " + linjeArray.length + " " + i);
					if(linjeArray[i] == '-'){
						midlertidig += linjeArray[i];
						i++;
					}

					while(i < linjeArray.length){
						if(isDigit(linjeArray[i])) {
							midlertidig += linjeArray[i];
							i++;
						}else{
							break;
						}
					}

					Token intern = new Token(integerToken, curLineNum());
					long intnummer = Long.parseLong(midlertidig);
					intern.integerLit = intnummer;
					curLineTokens.add(intern);

					if( i < linjeArray.length-1 && isDigit(linjeArray[i])) {
						i++;
					}
					midlertidig = "";

				}else if(isDigit(linjeArray[i]) && flyttall){
					//System.out.println("Vi er i integer med tallet: " + linjeArray[i] + " index: " + i + " midlertidig: " + midlertidig);

					while(i<linjeArray.length){
						if(isDigit(linjeArray[i]) || linjeArray[i] == '.'){
							midlertidig += linjeArray[i];
							i++;
						}else{
							break;
						}
					}//slutt paa while

					Token floatern = new Token(floatToken, curLineNum());
					double floatnummer = Double.parseDouble(midlertidig);
					floatern.floatLit = floatnummer;
					curLineTokens.add(floatern);
					if( i < linjeArray.length-1 && isDigit(linjeArray[i])) {
						i++;
					}
					midlertidig = "";
					flyttall = false;
				}else{
					i++;
				}
			}
		}
		curLineTokens.add(new Token(newLineToken,curLineNum()));
	}//slutt paa while




	for (Token t: curLineTokens)
	Main.log.noteToken(t);

}//slutt paa readnextline

public void lagNameToken(String navn, int curLine){
	Token name = new Token(nameToken, curLine);
	name.name = navn;
	curLineTokens.add(name);
}

public boolean lagTegnToken(String tegn, char tegn2, int curLine){
	Token t = null;
	switch (tegn){
		case "==":
		t = new Token(doubleEqualToken, curLine);
		curLineTokens.add(t);
		return true;
	}
	if(tegn2 == '<'){
		//  t = new Token(lessEqualToken, curLine);
		return true;
	} else if(tegn2 == '>'){
		//  t = new Token(greaterEqualToken, curLine);
		return true;
	} else if(tegn2 ==  '!'){
		//  t = new Token(notEqualToken, curLine);
		return true;
	}
	return false;
}

public void push(int tall){
	if(numIndents >= 99){
		System.out.println("Stacken er full, bitch");
	}else{
		indents[numIndents] = tall;
		numIndents++;
	}
}

public void pop(){
	//Sjekker om arrayet er større eller lik 0 for aa decrease arrayet. Deretter sette posisjonen til 0
	if(numIndents >= 0){
		numIndents--;
		indents[numIndents] = 0;
	}else{
		System.out.println("Stacken er tom, bro");
	}
}

public int curLineNum() {
	return sourceFile!=null ? sourceFile.getLineNumber() : 0;
}

private int findIndent(String s) {
	int indent = 0;

	while (indent<s.length() && s.charAt(indent)==' ') indent++;
	return indent;
}

private String expandLeadingTabs(String s) {
	String newS = "";
	for (int i = 0;  i < s.length();  i++) {
		char c = s.charAt(i);
		if (c == '\t') {
			do {
				newS += " ";
			} while (newS.length()%tabDist != 0);
		} else if (c == ' ') {
			newS += " ";
		} else {
			newS += s.substring(i);
			break;
		}
	}
	return newS;
}


private boolean isLetterAZ(char c) {
	return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
}


private boolean isDigit(char c) {
	return '0'<=c && c<='9';
}


public boolean isCompOpr() {
	TokenKind k = curToken().kind;
	switch(k){
		case greaterToken:
		case lessToken:
		case doubleEqualToken:
		case greaterEqualToken:
		case lessEqualToken:
		case notEqualToken:
		return true;
		default:
		//-- Must be changed in part 2:
		return false;
	}
}


public boolean isFactorPrefix() {
	TokenKind k = curToken().kind;
	switch (k) {
		case plusToken:
		case minusToken:
		return true;
		default:
		return false;
	}
}
//-- Must be changed in part 2:



public boolean isFactorOpr() {
	TokenKind k = curToken().kind;
	switch(k){
		case astToken:
		case slashToken:
		case percentToken:
		case doubleSlashToken:
		return true;
		default:
		//-- Must be changed in part 2:
		return false;
	}
}



public boolean isTermOpr() {
	TokenKind k = curToken().kind;
	switch (k) {
		case plusToken:
		case minusToken:
		return true;
		default:
		return false;

	}
}
}
