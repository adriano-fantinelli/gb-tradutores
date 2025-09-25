package src;

import java.io.*;
import java.util.regex.*;

public class LexicalAnalyzer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java src.LexicalAnalyzer <arquivo-fonte>");
            return;
        }

        String filePath = args[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                analyzeLine(line);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    private static void analyzeLine(String line) {
    String[] keywords = { "int","String","if","else","while","for","return",
                          "System","out","println","float","double","char","boolean",
                          "true","false" };
    String keywordPattern = "\\b(" + String.join("|", keywords) + ")\\b";

    String methodPattern = "[a-zA-Z_][a-zA-Z0-9_]*\\(";
    String identifierPattern = "[a-zA-Z_][a-zA-Z0-9_]*";
    String numberPattern = "\\d+(\\.\\d+)?([eE][+-]?\\d+)?";
    String stringPattern = "\"(\\\\.|[^\"\\\\])*\"";
    String operatorPattern = "==|!=|<=|>=|\\+\\+|--|\\+=|-=|\\*=|/=|&&|\\|\\||[+\\-*/=><!]";
    String delimiterPattern = "[;{}()\\[\\],\\.]";
    String commentPattern = "//.*|/\\*(.|\\R)*?\\*/";

    Pattern pattern = Pattern.compile(
        String.join("|",
            commentPattern,
            keywordPattern,
            methodPattern,
            identifierPattern,
            numberPattern,
            stringPattern,
            operatorPattern,
            delimiterPattern
        )
    );

    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
        String token = matcher.group();
        if (token.matches(commentPattern)) continue;
        else if (token.matches(keywordPattern)) System.out.println("Palavra-chave: " + token);
        else if (token.matches(methodPattern)) System.out.println("Método: " + token);
        else if (token.matches(identifierPattern)) System.out.println("Identificador: " + token);
        else if (token.matches(numberPattern)) System.out.println("Número: " + token);
        else if (token.matches(stringPattern)) System.out.println("String: " + token);
        else if (token.matches(operatorPattern)) System.out.println("Operador: " + token);
        else if (token.matches(delimiterPattern)) System.out.println("Delimitador: " + token);
    }
}
    }
