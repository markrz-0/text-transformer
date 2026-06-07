package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

/**
 * Service responsible for transforming text to uppercase.
 * It extends {@link TransformerService} and overrides the {@code transform} method
 * to convert all characters in the given text to their uppercase equivalents.
 */
@Service
public class ToUpperTransformerService extends TransformerService {

    /**
     * Retrieves the specific name of this transformer.
     *
     * @return the name of the transformer ("upper")
     */
    @Override
    public String getName() {
        return "upper";
    }

    /**
     * Transforms the given text by converting all characters to uppercase.
     *
     * @param text the input string to be transformed
     * @return the transformed string with all uppercase characters
     */
    @Override
    public String transform(String text) {
        return text.toUpperCase();
    }
}
