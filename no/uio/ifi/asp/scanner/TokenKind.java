package no.uio.ifi.asp.scanner;

public enum TokenKind {

  
    // Names and literals:
    nameToken("name"),
    integerToken("integer literal"),
    floatToken("float literal"),
    stringToken("string literal"),

    // Keywords:
    andToken("and"),
    asToken("as"),
    assertToken("assert"),
    breakToken("break"),
    classToken("class"),
    continueToken("continue"),
    defToken("def"),
    delToken("del"),
    elifToken("elif"),
    elseToken("else"),
    exceptToken("except"),
    falseToken("False"),
    finallyToken("finally"),
    forToken("for"),
    fromToken("from"),
    globalToken("global"),
    ifToken("if"),
    importToken("import"),
    inToken("in"),
    isToken("is"),
    lambdaToken("lambda"),
    noneToken("None"),
    nonlocalToken("nonlocal"),
    notToken("not"),
    orToken("or"),
    passToken("pass"),
    raiseToken("raise"),
    returnToken("return"),
    trueToken("True"),
    tryToken("try"),
    whileToken("while"),
    withToken("with"),
    yieldToken("yield"),

    // Operators:
    ampToken("&"),
    astToken("*"),
    barToken("|"),
    doubleAstToken("**"),
    doubleEqualToken("=="),
    doubleGreaterToken(">>"),
    doubleLessToken("<<"),
    doubleSlashToken("//"),
    greaterToken(">"),
    greaterEqualToken(">="),
    hatToken("^"),
    lessToken("<"),
    lessEqualToken("<="),
    minusToken("-"),
    notEqualToken("!="),
    percentToken("%"),
    plusToken("+"),
    slashToken("/"),
    tildeToken("~"),

    // Delimiters:
    ampEqualToken("&="),
    astEqualToken("*="),
    atToken("@"),
    barEqualToken("|="),
    colonToken(":"),
    commaToken(","),
    dotToken("."),
    doubleAstEqualToken("**="),
    doubleGreaterEqualToken(">>="),
    doubleLessEqualToken("<<="),
    doubleSlashEqualToken("//="),
    equalToken("="),
    hatEqualToken("^="),
    leftBraceToken("{"),
    leftBracketToken("["),
    leftParToken("("),
    minusEqualToken("-="),
    percentEqualToken("%="),
    plusEqualToken("+="),
    rightBraceToken("}"),
    rightBracketToken("]"),
    rightParToken(")"),
    semicolonToken(";"),
    slashEqualToken("/="),

    // Format tokens:
    indentToken("INDENT"),
    dedentToken("DEDENT"),
    newLineToken("NEWLINE"),
    eofToken("E-o-f");

    String image;

    TokenKind(String s) {
	image = s;
    }

    public String toString() {
	return image;
    }
}
