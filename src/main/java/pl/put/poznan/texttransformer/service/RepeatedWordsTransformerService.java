package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

@Service
public class RepeatedWordsTransformerService extends TransformerService {

    @Override
    public String getName() {
        return "repeated-words";
    }

    @Override
    public String transform(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String regex = "(?i)\\b(\\w+)(\\s+\\1\\b)+";
        
        return text.replaceAll(regex, "$1");
    }
}
