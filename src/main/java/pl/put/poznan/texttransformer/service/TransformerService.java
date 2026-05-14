package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

@Service
public abstract class TransformerService {
    public abstract String getName();
    public abstract String transform(String text);
}
