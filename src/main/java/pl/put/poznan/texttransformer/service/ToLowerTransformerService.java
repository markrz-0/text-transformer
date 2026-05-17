package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

@Service
public class ToLowerTransformerService extends TransformerService {

    @Override
    public String getName() {
        return "lower";
    }

    @Override
    public String transform(String text) {
        return text.toLowerCase();
    }
}
