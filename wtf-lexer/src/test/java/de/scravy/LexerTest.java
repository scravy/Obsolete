package de.scravy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class LexerTest {

  private static Lexer lexer;

  @DataPoints
  public static final Object[][] data = {

      { " ", "Juxta" },
      { "   ", "Juxta" },

      { "\n", "Indent" },
      { "    \n", "Indent" },
      { "\n    ", "Indent" },
      { "\n  \n  ", "Indent" },
      { "\n  \n\n  ", "Indent" },
      { "   \n    ", "Indent" },
      { "\n   \n  \n  \n", "Indent" },

      { ";;hello", "Comment" },

      { "(", "OpeningParenthesis" },
      { "{", "OpeningCurlyBrace" },
      { "[", "OpeningSquareBracket" },

      { ")", "ClosingParenthesis" },
      { "}", "ClosingCurlyBrace" },
      { "]", "ClosingSquareBracket" },

      { ",", "Comma" },
      { ";", "Semicolon" },
      { "_", "Hole" },

      { ".", "Dot" },
      { "..", "Range" },
      { "...", "Ellipsis" },

      { "14:00:00", "Time" },
      { "14:00", "Time" },
      { "14:00:00.234", "Time" },
      { "259:23:354", "Time" },

      { "2014-09-09", "Date" },
      { "1987-10-29", "Date" },
      { "0-0-0", "Date" },
      { "92739-2-3", "Date" },

      { "1599-12-30T13:40:23Z", "DateTime" },
      { "1599-12-30T13:40:23+01:00", "DateTime" },
      { "1599-12-30T13:40:23-01:00", "DateTime" },
      { "1599-12-30T13:40:23.23Z", "DateTime" },
      { "1599-12-30T13:40+01", "DateTime" },

      { "de305d54-75b4-431b-adb2-eb6b9e546014", "UUID" },
      { "123e4567-e89b-12d3-a456-426655440000", "UUID" },

      { "127.0.0.1", "Ip4addr" },

      { "::0:0:0", "Ip6addr" },
      { "0:0:0::", "Ip6addr" },
      { "2001:db8:85a3:0:0:8a2e:370:7334", "Ip6addr" },
      { "2001:db8:85a3::8a2e:370:7334", "Ip6addr" },
      { "0:0:0:0:0:0:0:0", "Ip6addr" },
      { "::0", "Ip6addr" },
      { "::1", "Ip6addr" },
      { "::ffff:c000:0280", "Ip6addr" },
      { "2001:db8::2:1", "Ip6addr" },
      { "2001:db8:0:1:1:1:1:1", "Ip6addr" },
      { "2001:DB8::1", "Ip6addr" },
      { "2001:db8:0:0:1:0:0:1", "Ip6addr" },
      { "2001:db8::1:0:0:1", "Ip6addr" },

      { "10e30", "Number" },
      { "1000_0000", "Number" },
      { "0_0_0", "Number" },

      { "0x10e30", "HexNumber" },

      { "0b1000_0000", "BinNumber" },
      { "0b0_0_0", "BinNumber" },

      { "0_0_0@32_9", "NumberWithBase" },
      { "cafebabe@16", "NumberWithBase" },

      { "0.0", "Float" },
      { "1.0", "Float" },
      { "3.14", "Float" },
      { "10e-3", "Float" },
      { "14.30e30", "Float" },

      { "this-is-a-name-too", "Name" },
      { "hello-world", "Name" },
      { "helloWorld", "Name" },
      { "_9", "Name" },
      { "M_PI", "Name" },
      { "MIN_MAX_MEGA", "Name" },
      { "_hello_world", "Name" },
      { "_923_43", "Name" },
      { "_923_43'", "Name" },
      { "Haskell'", "Name" },
      { "this:is_9prefixed", "Name" },
      { "this':is_9prefixed", "Name" },
      { "this:is_9prefixed'", "Name" },

      { "__FILE__", "MagicName" },
      { "__autoload__", "MagicName" },

      { "0.0.0", "Version" },
      { "0.0.0-beta", "Version" },
      { "1.0.0-alpha+001", "Version" },
      { "1.0.0+20130313144700", "Version" },
      { "1.0.0-beta+exp.sha.5114f85", "Version" },
      { "1.0.0-0.3.7", "Version" },
      { "1.0.0-x.7.z.92", "Version" },
      { "1.0.0-alpha.1", "Version" },
      { "1.0.0-beta.11", "Version" },

      { "Something{", "CurlyBraceInvocation" },
      { "this-is-something{", "CurlyBraceInvocation" },
      { "x{", "CurlyBraceInvocation" },
      { "_1337{", "CurlyBraceInvocation" },
      { "pfx:Something{", "CurlyBraceInvocation" },
      { "_0:this-is-something{", "CurlyBraceInvocation" },
      { "H':x{", "CurlyBraceInvocation" },
      { "woop-woop:_1337{", "CurlyBraceInvocation" },

      { "Something(", "ParenthesisInvocation" },
      { "this-is-something(", "ParenthesisInvocation" },
      { "_1337(", "ParenthesisInvocation" },
      { "x(", "ParenthesisInvocation" },

      { "'x'", "Char" },
      { "'\"'", "Char" },
      { "'\\''", "Char" },
      { "'\\\"'", "Char" },
      { "'\\\\'", "Char" },
      { "'\\a'", "Char" },
      { "'\\b'", "Char" },
      { "'\\e'", "Char" },
      { "'\\f'", "Char" },
      { "'\\n'", "Char" },
      { "'\\r'", "Char" },
      { "'\\t'", "Char" },
      { "'\\v'", "Char" },
      { "'\\&'", "Char" },
      { "'\\?'", "Char" },
      { "'\\$'", "Char" },
      { "'\\0'", "Char" },
      { "'\\uCAFE'", "Char" },
      { "'\\u4711'", "Char" },
      { "'\\x0A'", "Char" },
      { "'\\xFF'", "Char" },
      { "'\\{RIGHTWARDS_ARROW}'", "Char" },

      { "''Haskell' is great.''", "AsciiString" },
      { "''Haskell'''", "AsciiString" },

      { "`hello`", "Backticks" },

      { "/^[0-9]+$/", "Regex" },
      { "/^[0-9]+$/g", "Regex" },
      { "/^[0-9]+$/gU", "Regex" },
      { "/^[0-9]+$/m", "Regex" },

      { "http://www.example.org/", "URL" },
      { "http://127.0.0.1/", "URL" },
      { "http://[::1]/", "URL" },
      { "http://www.example.org:1337/", "URL" },
      { "http://127.0.0.1:8080/", "URL" },
      { "http://[::1]:81/", "URL" },
      { "http://www.example.org/test.xml", "URL" },
      { "http://127.0.0.1/test.xml", "URL" },
      { "http://[::1]/test.xml", "URL" },
      { "http://www.example.org:1337/test.xml", "URL" },
      { "http://127.0.0.1:8080/test.xml", "URL" },
      { "http://[::1]:81/test.xml", "URL" },

      { ",;", new String[] { ",", "Comma" }, new String[] { ";", "Semicolon" } }
  };

  @BeforeClass
  public static void setUp() throws IOException, URISyntaxException {
    lexer = Lexer.from(Paths.get(Lexer.class.getResource("lexer-test-lexemes.txt").toURI()));
  }

  @Theory
  public void test(final Object[] data) throws Lexer.Exception {

    if (data.length == 2 && data[1] instanceof String) {
      try {
        final List<Lexeme> result = lexer.lex(data[0].toString());
        if (result.isEmpty()) {
          Assert.fail("Experiment for " + data[0] + " matched nothing.");
        } else {
          Assert
              .assertEquals("Experiment for " + data[0] + " failed:",
                  new Lexeme(data[1].toString(), data[0].toString()),
                  result.get(0));
        }
      } catch (final Lexer.Exception exc) {
        Assert.fail("Experiment for " + data[0] + " threw an Exception.");
      }

    } else if (data.length >= 2 && data[1] instanceof String[]) {
      final List<Lexeme> expectedResult = new ArrayList<>();

      for (int i = 1; i < data.length; i += 1) {
        final String[] element = (String[]) data[i];
        expectedResult.add(new Lexeme(element[1], element[0]));
      }
      final List<Lexeme> result = lexer.lex(data[0].toString());
      Assert.assertEquals(expectedResult, result);
    }
  }
}
