package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

/**
 * Service responsible for reversing text while preserving the original case positions.
 * It extends {@link TransformerService} and overrides the {@code transform} method
 * to reverse the characters of the text but keeping the uppercase/lowercase
 * at their exact original indices.
 */
@Service
public class ReverseTransformerService extends TransformerService {

    /**
     * Retrieves the specific name of this transformer.
     *
     * @return the name of the transformer ("reverse")
     */
    @Override
    public String getName() {
        return "reverse";
    }

    /**
     * Transforms the given text by reversing it, while preserving the case of letters
     * at their original positions.
     *
     * @param text the input string to be reversed
     * @return the reversed string with preserved case positions
     */
    @Override
    public String transform(String text) {
        boolean[] upperCase = new boolean[text.length()];
        for (int i = 0; i < text.length(); i++) {
            upperCase[i] = Character.isUpperCase(text.charAt(i));
        }

        String reversed = new StringBuilder(text).reverse().toString();

        StringBuilder result = new StringBuilder(reversed.length());
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            result.append(upperCase[i] ? Character.toUpperCase(c) : Character.toLowerCase(c));
        }

        return result.toString();
    }
}
