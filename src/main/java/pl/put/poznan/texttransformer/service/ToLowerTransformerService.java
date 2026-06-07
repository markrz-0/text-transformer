package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

/**
 * Service responsible for transforming text to lowercase.
 * It extends {@link TransformerService} and overrides the {@code transform} method
 * to convert all characters in the given text to their lowercase equivalents.
 */
@Service
public class ToLowerTransformerService extends TransformerService {

    /**
     * Retrieves the specific name of this transformer.
     *
     * @return the name of the transformer ("lower")
     */
    @Override
    public String getName() {
        return "lower";
    }

    /**
     * Transforms the given text by converting all characters to lowercase.
     *
     * @param text the input string to be transformed
     * @return the transformed string with all lowercase characters
     */
    @Override
    public String transform(String text) {
        return text.toLowerCase();
    }
}
