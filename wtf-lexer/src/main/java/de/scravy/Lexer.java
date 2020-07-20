package de.scravy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Lexer {

  public static class Exception extends java.lang.Exception {

    private static final long serialVersionUID = 1L;

  }

  public static Lexer from(final Path filePath) {

    final Map<String, String> groups = new LinkedHashMap<>();

    final List<StringBuilder> rules = new ArrayList<>();
    rules.add(new StringBuilder());

    try {
      Files
          .lines(filePath, Charset.forName("UTF-8"))
          .sequential()
          .forEachOrdered(line -> {
            if (!line.startsWith(" ")) {
              rules.add(new StringBuilder());
            }
            rules.get(rules.size() - 1).append(line);
          });

      rules
          .stream()
          .sequential()
          .map(StringBuilder::toString)
          .filter(x -> !x.isEmpty())
          .forEachOrdered(
              line -> {
                final String[] parts = line.replaceAll("[ \n]+", "").split("=", 2);
                groups.put(parts[0], parts[1]);
              });

      return new Lexer(groups);
    } catch (final IOException exc) {
      throw new RuntimeException(exc);
    }
  }

  final Set<String> terminalSymbols;
  final Pattern pattern;

  private Lexer(final Map<String, String> rules) {

    final Map<String, String> myRules = new LinkedHashMap<>(rules);

    final Pattern referencePattern = Pattern.compile("<([a-zA-Z0-9]+)>");
    final Predicate<String> containsReference = regex -> referencePattern.matcher(regex).find();
    while (myRules.values().stream().anyMatch(containsReference)) {
      myRules.replaceAll((key, value) -> {
        final Matcher matcher = referencePattern.matcher(value);
        while (matcher.find()) {
          final String reference = matcher.group(1);
          if (myRules.containsKey(reference)) {
            value = value.replace(
                matcher.group(), "(" + myRules.get(reference) + ")");
          } else {
            throw new RuntimeException(reference + " is no rule.");
          }
        }
        return value;
      });
    }

    terminalSymbols = myRules.keySet().stream().sequential()
        .filter(key -> key.matches("[A-Z][a-zA-Z0-9]*"))
        .collect(Collectors.toCollection(LinkedHashSet::new));

    final String regex = terminalSymbols.stream()
        .map(key -> String.format("(?<%s>%s)", key, myRules.get(key)))
        .collect(Collectors.joining("|"));

    pattern = Pattern.compile(regex,
        Pattern.UNIX_LINES
            | Pattern.MULTILINE
            | Pattern.DOTALL
            | Pattern.UNICODE_CHARACTER_CLASS);
  }

  public List<Lexeme> lex(final Path filePath) throws IOException, Exception {
    return lex(new String(
        Files.readAllBytes(filePath),
        Charset.forName("UTF-8")));
  }

  public List<Lexeme> lex(String input) throws Exception {

    final List<Lexeme> lexemes = new ArrayList<>();

    final Matcher matcher = pattern.matcher(input);
    while (matcher.lookingAt()) {
      for (final String terminal : terminalSymbols) {
        final String match = matcher.group(terminal);
        if (match != null) {
          lexemes.add(new Lexeme(terminal, match));
          input = input.substring(match.length());
          matcher.reset(input);
          break;
        }
      }
    }
    if (!input.isEmpty()) {
      throw new Exception();
    }

    return lexemes;
  }
}
