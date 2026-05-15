package pl.put.poznan.texttransformer.service.decoratorfactory;

import org.springframework.stereotype.Service;
import pl.put.poznan.texttransformer.service.DecoratorWrapperTransformerService;
import pl.put.poznan.texttransformer.service.IdentityTransformerService;
import pl.put.poznan.texttransformer.service.TransformerService;
import pl.put.poznan.texttransformer.service.registry.TransformationServiceRegistry;

@Service
public class TransformationDecoratorPipelineFactory {
    private final TransformationServiceRegistry registry;

    public TransformationDecoratorPipelineFactory(TransformationServiceRegistry registry) {
        this.registry = registry;
    }

    /**
     * @param transformations: list of transformations
     * @return pipeline that will execute all the provided transformations in order
     * @throws IllegalArgumentException if transformation with such name doesn't exist
     */
    public TransformerService buildPipeline(String[] transformations) throws IllegalArgumentException {
        TransformerService pipeline = new IdentityTransformerService();
        for (String transformation : transformations) {
            TransformerService strategy = registry.getService(transformation);
            pipeline = new DecoratorWrapperTransformerService(strategy, pipeline);
        }
        return pipeline;
    }
}
