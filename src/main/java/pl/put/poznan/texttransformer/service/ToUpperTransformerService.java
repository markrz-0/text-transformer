package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

@Service
public class ToUpperTransformerService extends TransformerService {

    @Override
    public String getName() {
        return "upper";
    }

    @Override
    public String transform(String text) {
        return text.toUpperCase();
    }
}
