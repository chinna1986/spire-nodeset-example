package com.spotify.spire.model.nodeset;

import java_cup.runtime.Symbol;

%%
%class NodeSetScanner
%cupsym NodeSetParserSymbols
%cup
%final
%unicode
%column
%%
/* Ignore any whitespace */
[ \t\f\r\n] { /* ignore */ }

/* Set operators */
"\\" { return new Symbol(NodeSetParserSymbols.BACKSLASH, 1, yycolumn + 1, null); }
"/"  { return new Symbol(NodeSetParserSymbols.SLASH, 1, yycolumn + 1, null); }
"|"  { return new Symbol(NodeSetParserSymbols.PIPE, 1, yycolumn + 1, null); }
"^"  { return new Symbol(NodeSetParserSymbols.CIRCUMFLEX, 1, yycolumn + 1, null); }
"&"  { return new Symbol(NodeSetParserSymbols.AMPERSAND, 1, yycolumn + 1, null); }
"∪"  { return new Symbol(NodeSetParserSymbols.UNION, 1, yycolumn + 1, null); }
"⊖"  { return new Symbol(NodeSetParserSymbols.CIRCLED_MINUS, 1, yycolumn + 1, null); }
"∆"  { return new Symbol(NodeSetParserSymbols.INCREMENT, 1, yycolumn + 1, null); }
"∩"  { return new Symbol(NodeSetParserSymbols.INTERSECTION, 1, yycolumn + 1, null); }

/* Other operators */
"," { return new Symbol(NodeSetParserSymbols.COMMA, 1, yycolumn + 1, null); }
"." { return new Symbol(NodeSetParserSymbols.DOT, 1, yycolumn + 1, null); }
"∅" { return new Symbol(NodeSetParserSymbols.EMPTY_SET, 1, yycolumn + 1, null); }
"@" { return new Symbol(NodeSetParserSymbols.AT, 1, yycolumn + 1, null); }

/* Parentheses */
"(" { return new Symbol(NodeSetParserSymbols.LPAREN, 1, yycolumn + 1, null); }
")" { return new Symbol(NodeSetParserSymbols.RPAREN, 1, yycolumn + 1, null); }
"[" { return new Symbol(NodeSetParserSymbols.LBRACKET, 1, yycolumn + 1, null); }
"]" { return new Symbol(NodeSetParserSymbols.RBRACKET, 1, yycolumn + 1, null); }

/* Literals */
\'([^\'\\]|\\[\'\\])*\' { return new Symbol(NodeSetParserSymbols.QSTRING, 1, yycolumn + 1, yytext().substring(1, yytext().length() - 1).replaceAll("\\'", "'").replaceAll("\\\\", "\\")); }
\"([^\"\\]|\\[\"\\])*\" { return new Symbol(NodeSetParserSymbols.QSTRING, 1, yycolumn + 1, yytext().substring(1, yytext().length() - 1).replaceAll("\\\"", "\"").replaceAll("\\\\", "\\")); }
-?[0-9]+ { return new Symbol(NodeSetParserSymbols.INTEGER, 1, yycolumn + 1, Long.parseLong(yytext())); }
-?[0-9]+\.[0-9]+([eE][0-9]+)? { return new Symbol(NodeSetParserSymbols.DECIMAL, 1, yycolumn + 1, Double.parseDouble(yytext())); }
false { return new Symbol(NodeSetParserSymbols.BOOLEAN, 1, yycolumn + 1, Boolean.FALSE); }
true { return new Symbol(NodeSetParserSymbols.BOOLEAN, 1, yycolumn + 1, Boolean.TRUE); }

[[:letter:][:digit:]_-]+ { return new Symbol(NodeSetParserSymbols.IDENTIFIER, 1, yycolumn + 1, yytext()); }
