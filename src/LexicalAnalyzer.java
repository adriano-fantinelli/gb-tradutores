package src;

import java.io.*;
import java.util.regex.*;

public class LexicalAnalyzer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java LexicalAnalyzer <arquivo-fonte>");
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
        String[] keywords = {"int", "String", "if", "else", "while", "for", "return", "System", "out", "println"};
        String keywordPattern = String.join("|", keywords);

        String identifier = "[a-zA-Z_][a-zA-Z0-9_]*";
        String number = "\\d+";
        String operator = "[+\\-*/=><]";
        String stringLiteral = "\".*?\"";
        String methodPattern = identifier + "\\(";
        String delimiter = "[;{}()]";

        Pattern tokenPattern = Pattern.compile(
            String.format("(%s)|(%s)|(%s)|(%s)|(%s)|(%s)|(%s)", keywordPattern, identifier, number, operator, stringLiteral, methodPattern, delimiter)
        );

        Matcher matcher = tokenPattern.matcher(line);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                System.out.println("Palavra-chave: " + matcher.group(1));
            } else if (matcher.group(2) != null) {
                System.out.println("Identificador: " + matcher.group(2));
            } else if (matcher.group(3) != null) {
                System.out.println("Número: " + matcher.group(3));
            } else if (matcher.group(4) != null) {
                System.out.println("Operador: " + matcher.group(4));
            } else if (matcher.group(5) != null) {
                System.out.println("String: " + matcher.group(5));
            } else if (matcher.group(6) != null) {
                System.out.println("Método: " + matcher.group(6));
            } else if (matcher.group(7) != null) {
                System.out.println("Delimitador: " + matcher.group(7));
            }
        }
    }
}