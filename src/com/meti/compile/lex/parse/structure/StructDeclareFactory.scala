package com.meti.compile.lex.parse.structure

import com.meti.compile.lex.Tokenizer
import com.meti.compile.lex.parse.TokenizerFactory
import com.meti.compile.node.structure.StructDeclareToken.Builder
import com.meti.compile.node.{Token, ValueToken}
import com.meti.util
import com.meti.util.Monad.Monad
import com.meti.util.MonadStream._
import com.meti.util.Some.Some

class TokenizerImpl(val content: String) extends Tokenizer {
  override def evaluate: util.Option[Token] = Some(content)
    .filter((value: String) => value.startsWith("<"))
    .filter((value: String) => value.contains(">"))
    .filter((value: String) => value.contains("{"))
    .filter((value: String) => value.endsWith("}"))
    .supplyValue(createBuilder)
    .map(parseFields)

  private def parseFields(builder: Builder) = extractChildren
    .filter((child: String) => !child.isEmpty)
    .map(_.trim)
    .map((value: String) => new ValueToken(value))
    .reduce(builder, (_: Builder).withChild(_: Token))
    .build()

  private def extractChildren = Monad(content)
    .extract((value: String) => value.indexOf('{'))
    .extractStart(_.indexOf('}'))
    .map((value: String, start: Int, `end`: Int) => value.substring(start + 1, `end`))
    .map(_.trim)
    .map(_.split(","))
    .apply(StreamArray(_))

  def createBuilder: Builder = Monad(content)
    .extract((value: String) => value.indexOf('<'))
    .extractStart((value: String) => value.indexOf('>'))
    .map((value: String, start: Int, end: Int) => value.substring(start + 1, end))
    .map(_.trim)
    .append(new Builder())
    .reverse()
    .apply((_: Builder).withName(_: String));
}

class StructDeclareFactory extends TokenizerFactory {
  override def create(content: String) = new TokenizerImpl(content)
}