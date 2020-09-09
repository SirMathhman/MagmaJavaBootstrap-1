package com.meti.compile.lex.parse;

import com.meti.compile.lex.parse.block.*;
import com.meti.compile.lex.parse.block.function.AbstractLexRule;
import com.meti.compile.lex.parse.block.function.ConcreteLexRule;
import com.meti.compile.lex.parse.external.ImportRule;
import com.meti.compile.lex.parse.primitive.CharLexRule;
import com.meti.compile.lex.parse.primitive.IntLexRule;
import com.meti.compile.lex.parse.scope.DeclareLexRule;
import com.meti.compile.lex.parse.scope.VariableLexRule;
import com.meti.compile.lex.parse.structure.FieldLexRule;
import com.meti.compile.lex.parse.structure.StructDeclareLexRule;
import com.meti.compile.lex.parse.structure.StructureLexRule;

import java.util.Collection;
import java.util.List;

public class MagmaLexRule extends CompoundLexRule {
	@Override
	public Collection<LexRule> supplyRules() {
		return List.of(
				new StructDeclareLexRule(),
				new StructureLexRule(),
				new ImportRule(),
				new BlockRule(),
				new ReturnRule(),
				new InvocationRule(),
				new DeclareLexRule(),
				new ConcreteLexRule(),
				new AbstractLexRule(),
				new CharLexRule(),
				new IntLexRule(),
				new InfixRule(),
				new FieldLexRule(),
				new VariableLexRule()
		);
	}
}
