package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Token;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class CollectiveProcessStage implements ProcessStage {
    public Token load(Token token) {
        return streamPreprocessors()
                .filter(preprocessor -> token.applyToGroup(preprocessor::canPreprocess))
                .map(preprocessor -> preprocessor.preprocess(token))
                .findFirst()
                .orElse(token);
    }

    @Override
    public Token process(Token token) {
        Token loaded = load(token);
        Dependents dependents = loaded.applyToDependents(this::transformDependents);
        Token copy = loaded.copy(dependents);
        Optional<Token> transformOptional = transformOptionally(copy);
        return transformOptional.orElse(copy);
    }

    public abstract Stream<Preprocessor> streamPreprocessors();

    public Optional<Token> transformOptionally(Token copy) {
        return streamModifiers()
                .filter(modifier1 -> copy.applyToGroup(modifier1::canProcess))
                .map(modifier1 -> modifier1.process(copy))
                .findFirst();
    }

    public abstract Stream<Processor> streamModifiers();

    private Dependents transformDependents(Dependents dependents) {
        return dependents.streamChildren()
                .map(this::process)
                .reduce(dependents.identity(), Dependents::append);
    }
}
