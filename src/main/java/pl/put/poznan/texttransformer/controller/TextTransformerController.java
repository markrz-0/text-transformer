package pl.put.poznan.texttransformer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.texttransformer.dto.TransformationRequest;
import pl.put.poznan.texttransformer.dto.TransformationResponse;
import pl.put.poznan.texttransformer.service.TransformerService;
import pl.put.poznan.texttransformer.service.decoratorfactory.TransformationDecoratorPipelineFactory;

import java.util.Arrays;

@RestController
@RequestMapping("/transform")
public class TextTransformerController {

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    private final TransformationDecoratorPipelineFactory pipelineFactory;

    public TextTransformerController(TransformationDecoratorPipelineFactory pipelineFactory) {
        this.pipelineFactory = pipelineFactory;
    }

    @PostMapping
    public ResponseEntity<TransformationResponse> post(@RequestBody TransformationRequest request) {

        logger.debug("Request recieved: Text: \"{}\" Transformations: {}", request.getText(), request.getTransformation());

        try {
            TransformerService pipeline = pipelineFactory.buildPipeline(request.getTransformation());
            String result = pipeline.transform(request.getText());

            return new ResponseEntity<>(new TransformationResponse(result), HttpStatus.OK);
        } catch (IllegalArgumentException ie) {
            TransformationResponse resp = new TransformationResponse("");
            resp.setError("illegal transformation passed");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
    }
}
