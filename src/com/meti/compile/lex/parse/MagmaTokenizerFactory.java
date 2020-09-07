package com.meti.compile.lex.parse;

import com.meti.compile.lex.parse.block.*;
import com.meti.compile.lex.parse.block.function.AbstractTokenizerFactory;
import com.meti.compile.lex.parse.block.function.ConcreteTokenizerFactory;
import com.meti.compile.lex.parse.external.ImportRule;
import com.meti.compile.lex.parse.primitive.CharTokenizerFactory;
import com.meti.compile.lex.parse.primitive.IntTokenizerFactory;
import com.meti.compile.lex.parse.scope.DeclareTokenizerFactory;
import com.meti.compile.lex.parse.scope.VariableTokenizerFactory;
import com.meti.compile.lex.parse.structure.FieldTokenizerFactory;
import com.meti.compile.lex.parse.structure.StructDeclareTokenizerFactory;
import com.meti.compile.lex.parse.structure.StructureTokenizerFactory;
import com.meti.util.MonadStream;

import java.util.Collection;
import java.util.List;

import static com.meti.util.MonadStream.Stream;

public class MagmaTokenizerFactory extends CompoundTokenizerFactory {
	@Override
	public Collection<TokenizerFactory> supplyFactories() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MonadStream<TokenizerFactory> streamFactories(){
		return Stream(
				new StructDeclareTokenizerFactory(),
				new StructureTokenizerFactory(),
				new ImportRule(),
				new BlockRule(),
				new ReturnRule(),
				new InvocationRule(),
				new DeclareTokenizerFactory(),
				new ConcreteTokenizerFactory(),
				new AbstractTokenizerFactory(),
				new CharTokenizerFactory(),
				new IntTokenizerFactory(),
				new InfixRule(),
				new FieldTokenizerFactory(),
				new VariableTokenizerFactory());
	}
}
