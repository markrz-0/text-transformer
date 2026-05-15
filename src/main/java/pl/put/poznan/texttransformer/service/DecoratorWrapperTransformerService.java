package pl.put.poznan.texttransformer.service;

public class DecoratorWrapperTransformerService extends TransformerService {
    private final TransformerService strategy;
    private final TransformerService wrapped;

    public DecoratorWrapperTransformerService(TransformerService strategy, TransformerService wrapped) {
        this.strategy = strategy;
        this.wrapped = wrapped;
    }

    @Override
    public String getName() {
        return "decoratorwrapper";
    }

    @Override
    public String transform(String text) {

        String innerResult = wrapped.transform(text);

        return strategy.transform(innerResult);
    }
}
