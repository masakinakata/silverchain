package parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

final class ParserTests {

  @Test
  void testQualifiedName() {
    test(Parser::qualifiedName, "foo");
    test(Parser::qualifiedName, "foo.bar");
    test(Parser::qualifiedName, "foo.bar.baz");
  }

  @Test
  void testTypeParameter() {
    test(Parser::typeParameter, "T");
  }

  @Test
  void testTypeParameterList() {
    test(Parser::typeParameterList, "T");
    test(Parser::typeParameterList, "T,S");
  }

  @Test
  void testTypeParameters() {
    test(Parser::typeParameters, "T;S");
    test(Parser::typeParameters, "T");
    test(Parser::typeParameters, ";T");
  }

  @Test
  void testType() {
    test(Parser::type, "Foo");
    test(Parser::type, "Foo<T>");
  }

  @Test
  void testTypeArgument() {
    test(Parser::typeArgument, "T");
  }

  @Test
  void testTypeArguments() {
    test(Parser::typeArguments, "T");
    test(Parser::typeArguments, "T,S");
  }

  @Test
  void testTypeReference() {
    test(Parser::typeReference, "Foo");
    test(Parser::typeReference, "foo.Bar<T>");
  }

  @Test
  void testMethodParameter() {
    test(Parser::methodParameter, "Foo foo");
  }

  @Test
  void testMethodParameters() {
    test(Parser::methodParameters, "Foo foo");
    test(Parser::methodParameters, "Foo foo,Bar bar");
  }

  @Test
  void testMethod() {
    test(Parser::method, "foo()");
    test(Parser::method, "foo(Bar bar)");
  }

  @Test
  void testRuleElement() {
    test(Parser::ruleElement, "foo()");
    test(Parser::ruleElement, "(foo()|bar())");
  }

  @Test
  void testRepeatOperator() {
    test(Parser::repeatOperator, "*");
    test(Parser::repeatOperator, "+");
    test(Parser::repeatOperator, "?");
    test(Parser::repeatOperator, "{1}", "+");
    test(Parser::repeatOperator, "{0,1}", "?");
    test(Parser::repeatOperator, "{2}");
    test(Parser::repeatOperator, "{0,2}");
    test(Parser::repeatOperator, "{1,2}");
    test(Parser::repeatOperator, "{2,3}");
  }

  @Test
  void testRuleFactor() {
    test(Parser::ruleFactor, "foo()");
    test(Parser::ruleFactor, "foo()*");
  }

  @Test
  void testRuleTerm() {
    test(Parser::ruleTerm, "foo()");
    test(Parser::ruleTerm, "foo() bar()");
  }

  @Test
  void testRuleExpression() {
    test(Parser::ruleExpression, "foo()");
    test(Parser::ruleExpression, "foo()|bar()|baz()");
  }

  @Test
  void testRule() {
    test(Parser::rule, "foo();");
    test(Parser::rule, "foo() Foo;");
  }

  @Test
  void testRules() {
    test(Parser::rules, "foo() Foo; bar() Bar;");
  }

  @Test
  void testGrammar() {
    test(Parser::grammar, "Foo:");
    test(Parser::grammar, "Foo: foo() Foo;");
  }

  private void test(RuleSelector<Parser, ?> selector, String text) {
    test(selector, text, text);
  }

  private void test(RuleSelector<Parser, ?> selector, String text, String expected) {
    InputStream stream = new ByteArrayInputStream(text.getBytes());
    Parser parser = new Parser(stream);
    try {
      Object result = selector.apply(parser);
      Object token = parser.getNextToken();
      assert result.toString().equals(expected);
      assert token.toString().isEmpty();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}