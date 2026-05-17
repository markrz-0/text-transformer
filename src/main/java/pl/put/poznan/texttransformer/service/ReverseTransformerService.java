package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

@Service
public class ReverseTransformerService extends TransformerService {

    @Override
    public String getName() {
        return "reverse";
    }

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
