package pl.put.poznan.texttransformer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public abstract class TransformerService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    public abstract String getName();
    public abstract String transform(String text);
}
