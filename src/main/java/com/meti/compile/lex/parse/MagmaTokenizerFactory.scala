package com.meti.compile.lex.parse

import com.meti.compile.lex.parse.block._
import com.meti.compile.lex.parse.block.function.{AbstractTokenizerFactory, ConcreteTokenizerFactory}
import com.meti.compile.lex.parse.external.ImportRule
import com.meti.compile.lex.parse.primitive.{CharTokenizerFactory, IntTokenizerFactory}
import com.meti.compile.lex.parse.scope.{DeclareTokenizerFactory, VariableTokenizerFactory}
import com.meti.compile.lex.parse.structure.{FieldTokenizerFactory, StructDeclareFactory, StructureTokenizerFactory}
import com.meti.util.MonadStream
import com.meti.util.MonadStream.Stream

class MagmaTokenizerFactory extends CompoundTokenizerFactory {
  override def streamFactories: MonadStream[TokenizerFactory] = Stream(
    StructDeclareFactory,
    new StructureTokenizerFactory,
    new ImportRule,
    new BlockFactory,
    new ReturnRule,
    new InvocationRule,
    new DeclareTokenizerFactory,
    new ConcreteTokenizerFactory,
    new AbstractTokenizerFactory,
    new CharTokenizerFactory,
    new IntTokenizerFactory,
    new InfixRule,
    new FieldTokenizerFactory,
    new VariableTokenizerFactory
  )
}