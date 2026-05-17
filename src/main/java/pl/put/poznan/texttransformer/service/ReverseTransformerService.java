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
        return new StringBuilder(text).reverse().toString();
    }
}
