package com.jlox;

public enum TokenType {
    EOF,

    // One char tokens
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    DOT, COMMA, SEMICOLON, PLUS, MINUS, STAR, SLASH,

    // One or two char tokens
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // Multi char tokens
    IDENTIFIER, STRING, NUMBER,

    // Keywords
    CLASS, FUN, RETURN, TRUE, FALSE, NIL, IF, ELSE, AND, OR,
    FOR, WHILE, VAR, PRINT, SUPER, THIS
}
