package com.jlox;

// public class AstPrinter implements Expr.Visitor<String> {
//     String print(Expr expr) {
//         return expr.accept(this);
//     }

//     private String parenthesize(String name, Expr... exprs) {
//         StringBuilder builder = new StringBuilder();

//         builder.append("(").append(name);
//         for (Expr expr : exprs)
//             builder.append(" ").append(expr.accept(this));
//         builder.append(")");

//         return builder.toString();
//     }

//     @Override
//     public String visitBinaryExpr(Expr.Binary expr) {
//         return parenthesize(expr.operator.lexeme, expr.left, expr.right);
//     }

//     @Override
//     public String visitGroupingExpr(Expr.Grouping expr) {
//         return parenthesize("group", expr.expression);
//     }

//     @Override
//     public String visitLiteralExpr(Expr.Literal expr) {
//         if (expr.value == null) return "nil";
//         return expr.value.toString();
//     }

//     @Override
//     public String visitUnaryExpr(Expr.Unary expr) {
//         return parenthesize(expr.operator.lexeme, expr.right);
//     }
    
//     public static void main(String[] args) {
//         Expr testExpr = new Expr.Binary(
//             new Expr.Unary(
//                 new Token(TokenType.MINUS, 1, "-", null),
//                 new Expr.Literal(123)
//             ), 
//             new Token(TokenType.STAR, 1, "*", null), 
//             new Expr.Grouping(new Expr.Literal(45.67))
//         );
//         System.out.println(new AstPrinter().print(testExpr));
//     }
// }
