package com.jlox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Scanner {

    static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and",    TokenType.AND);
        keywords.put("class",  TokenType.CLASS);
        keywords.put("else",   TokenType.ELSE);
        keywords.put("false",  TokenType.FALSE);
        keywords.put("for",    TokenType.FOR);
        keywords.put("func",   TokenType.FUNC);
        keywords.put("if",     TokenType.IF);
        keywords.put("nil",    TokenType.NIL);
        keywords.put("or",     TokenType.OR);
        keywords.put("print",  TokenType.PRINT);
        keywords.put("return", TokenType.RETURN);
        keywords.put("super",  TokenType.SUPER);
        keywords.put("this",   TokenType.THIS);
        keywords.put("true",   TokenType.TRUE);
        keywords.put("var",    TokenType.VAR);
        keywords.put("while",  TokenType.WHILE);
    }
    

    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private int start = 0;
    private int current = 0;
    private int line = 1;

    public Scanner(String source) {
        this.source = source;
    }

    /**
     * Go through source and add each token to this.tokens
     */
    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme
            start = current;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, line, "", null));
        return tokens;
    }

    /**
     * Go through source
     * Add first token found to this.tokens
     * Print error message on unexpected char
     */
    private void scanToken() {
        char c = advance();
        switch (c) {
            // Single char tokens
            case '(': addToken(TokenType.LEFT_PAREN); break;
            case ')': addToken(TokenType.RIGHT_PAREN); break;
            case '{': addToken(TokenType.LEFT_BRACE); break;
            case '}': addToken(TokenType.RIGHT_BRACE); break;
            case '.': addToken(TokenType.DOT); break;
            case ',': addToken(TokenType.COMMA); break;
            case ';': addToken(TokenType.SEMICOLON); break;
            case '+': addToken(TokenType.PLUS); break;
            case '-': addToken(TokenType.MINUS); break;
            case '*': addToken(TokenType.STAR); break;

            // Single-double char tokens
            case '!':
                addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
                break;
            case '=':
                addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
                break;
            case '>':
                addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
                break;
            case '<':
                addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
                break;

            case '/':
                if (match('/')) {
                    // if comment, skip until end of line
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(TokenType.SLASH);
                }
                break;
            
            // ignore whitespace
            case ' ':
            case '\r':
            case '\t':
                break;

            case '\n':
                line++;
                break;

            case '"': lexString(); break;
            
            // Keywords

            default:
                if (isNumber(c)) {
                    lexNumber();
                } else if (isAlpha(c)) {
                    lexIdentifier();
                } else {
                    JLox.error(line, "Unexpected character");
                }
                break;
        }
    }

    private void lexIdentifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);
        TokenType tokenType = keywords.get(text);

        if (tokenType == null)
            addToken(TokenType.IDENTIFIER);
        else
            addToken(tokenType);
    }

    /**
     * Lex number until its end
     */
    private void lexNumber() {
        while (isNumber(peek()) && !isAtEnd()) {
            advance();
        }

        if (peek() == '.' && isNumber(peekNext())) {
            advance();
            while (isNumber(peek()) && !isAtEnd())
                advance();
        }

        // Trim surrounding quotes
        addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
    }
    
    /**
     * Lex string until its end
     * TODO add escape chars
     */
    private void lexString() {
        while (peek() != '\n' && !isAtEnd()) {
            if (peek() == '\n')
                line++;
            advance();
        }

        if (isAtEnd()) {
            JLox.error(line, "Unterminated string");
            return;
        }

        // closing '"'
        advance();

        // Trim surrounding quotes
        addToken(TokenType.STRING, source.substring(start + 1, current - 1));
    }

    /**
     * Get next char
     * @return next char in source
     */
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    /**
     * Get next next char
     * @return two next char in source
     */
    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    /**
     * Check next char without moving current
     * @param expected
     * @return true if next char equals expected
     */
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private void addToken(TokenType tokenType) {
        addToken(tokenType, null);
    }

    private void addToken(TokenType tokenType, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(tokenType, line, text, literal));
    }

    /**
     * First get current char then move current one forward.
     * @return next char in source
     */
    private char advance() {
        return source.charAt(current++);
    }

    private boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
               c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isNumber(c);
      }

    private boolean isAtEnd() {
        return current >= source.length();
    }

}
