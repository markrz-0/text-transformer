package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

/**
 * Service responsible for capitalizing text.
 * It extends {@link TransformerService} and overrides the {@code transform} method
 * to convert the first character of each word to uppercase and the rest to lowercase.
 */
@Service
public class CapitalizeTransformerService extends TransformerService {

    /**
     * Retrieves the specific name of this transformer.
     *
     * @return the name of the transformer ("capitalize")
     */
    @Override
    public String getName() {
        return "capitalize";
    }

    /**
     * Transforms the given text by capitalizing the first letter of each word
     * and changing all subsequent letters in the word to lowercase.
     *
     * @param text the input string to be transformed
     * @return the transformed string with capitalized words
     */
    @Override
    public String transform(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        char[] chars = text.toCharArray();
        boolean capitalizeNext = true;

        for (int i = 0; i < chars.length; i++) {
            if (Character.isWhitespace(chars[i])) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                chars[i] = Character.toUpperCase(chars[i]);
                capitalizeNext = false;
            } else {
                chars[i] = Character.toLowerCase(chars[i]);
            }
        }

        return new String(chars);
    }
}
