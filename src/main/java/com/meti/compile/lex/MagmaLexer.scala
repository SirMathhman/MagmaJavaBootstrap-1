package com.meti.compile.lex

import com.meti.compile.lex.parse.MagmaTokenizerFactory
import com.meti.compile.lex.resolve.MagmaResolveRule
import com.meti.compile.node.Token
import com.meti.util

class MagmaLexer() extends Lexer {
  final private val rootParserRule = new MagmaTokenizerFactory
  final private val rootResolveRule = new MagmaResolveRule
  final private val lexer = new RootLexer(rootParserRule, rootResolveRule)

  override def parseToOption(content: String): util.Option[Token] = lexer.parseToOption(content)

  override def parse(content: String) = throw new UnsupportedOperationException

  override def resolve(name: String) = throw new UnsupportedOperationException
}