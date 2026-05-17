package pl.put.poznan.texttransformer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.texttransformer.dto.TransformationRequest;
import pl.put.poznan.texttransformer.dto.TransformationResponse;
import pl.put.poznan.texttransformer.service.TransformerService;
import pl.put.poznan.texttransformer.service.decoratorfactory.TransformationDecoratorPipelineFactory;

import java.util.Arrays;

@RestController
@RequestMapping("/{text}")
public class TextTransformerController {

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    private final TransformationDecoratorPipelineFactory pipelineFactory;

    public TextTransformerController(TransformationDecoratorPipelineFactory pipelineFactory) {
        this.pipelineFactory = pipelineFactory;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@PathVariable String text,
            @RequestParam(value = "transforms", defaultValue = "upper,escape") String[] transforms) {

        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));

        return "Todo";
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public TransformationResponse post(@PathVariable String text,
            @RequestBody TransformationRequest request) {

        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(request.getTransformation()));

        TransformerService pipeline = pipelineFactory.buildPipeline(request.getTransformation());
        String result = pipeline.transform(text);

        return new TransformationResponse(result);
    }

}
