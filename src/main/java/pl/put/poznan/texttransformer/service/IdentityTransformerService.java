package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

@Service
public class IdentityTransformerService extends TransformerService {
    @Override
    public String getName() {
        return "identity";
    }

    @Override
    public String transform(String text) {
        return text;
    }
}
