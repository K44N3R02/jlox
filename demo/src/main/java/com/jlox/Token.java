package com.jlox;

import com.jlox.TokenType;

public class Token {
    final TokenType tokenType;
    final int line;
    final String lexeme;
    final Object literal;

    Token(TokenType tokenType, int line, String lexeme, Object literal) {
        this.tokenType = tokenType;
        this.line = line;
        this.lexeme = lexeme;
        this.literal = literal;
    }

    public String toString() {
        return tokenType + " " + lexeme + " " + literal;
    }
}
