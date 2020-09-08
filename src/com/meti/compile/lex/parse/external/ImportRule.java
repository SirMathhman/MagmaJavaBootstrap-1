package com.meti.compile.lex.parse.external;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.Tokenizer;
import com.meti.compile.lex.parse.FilteredTokenizerFactory;
import com.meti.compile.node.Dependents;
import com.meti.compile.node.EmptyDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ImportRule extends FilteredTokenizerFactory {
	public static final String HEADER = "import native ";

	@Override
	public boolean canQualify(String content) {
		return content.startsWith(HEADER);
	}

	@Override
	public Token parseQualified(String content, Lexer lexer) {
		String value = content.substring(HEADER.length());
		return new ImportToken(value);
	}

    @Override
    public Tokenizer create(String content) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private static final class ImportToken implements Token {
		private final String value;

		private ImportToken(String value) {
			this.value = value;
		}

		@Override
		public void acceptDependents(Consumer<Dependents> consumer) {
            consumer.accept(EmptyDependents.Empty);
		}

		@Override
		public <T> T applyToDependents(Function<Dependents, T> mapper) {
            return mapper.apply(EmptyDependents.Empty);
		}

		@Override
		public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
			return mapper.apply(TokenGroup.Import);
		}

		@Override
		public Token copy(Dependents dependents) {
			return new ImportToken(value);
		}

		@Override
		public String render() {
			String message = "#include <%s.h>\n";
			return message.formatted(value);
		}

        @Override
        public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
            return Optional.empty();
        }
    }
}
