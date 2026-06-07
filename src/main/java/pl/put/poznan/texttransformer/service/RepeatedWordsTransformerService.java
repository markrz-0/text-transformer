package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

/**
 * Service responsible for removing consecutive repeated words.
 * It extends {@link TransformerService} and overrides the {@code transform} method
 * to identify and eliminate duplicate adjacent words in the text.
 */
@Service
public class RepeatedWordsTransformerService extends TransformerService {

    /**
     * Retrieves the specific name of this transformer.
     *
     * @return the name of the transformer ("repeated-words")
     */
    @Override
    public String getName() {
        return "repeated-words";
    }

    /**
     * Transforms the given text by removing consecutive duplicate words.
     * Only the first occurrence of a repeated word sequence is retained.
     *
     * @param text the input string to be transformed
     * @return the transformed string with repeated words removed
     */
    @Override
    public String transform(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String regex = "(?i)\\b(\\w+)(\\s+\\1\\b)+";
        
        return text.replaceAll(regex, "$1");
    }
}
