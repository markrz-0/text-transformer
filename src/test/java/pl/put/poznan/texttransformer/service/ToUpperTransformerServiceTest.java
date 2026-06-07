package pl.put.poznan.texttransformer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToUpperTransformerServiceTest {

    private ToUpperTransformerService transformer;

    @BeforeEach
    void setUp() {
        transformer = new ToUpperTransformerService();
    }

    @Test
    void testTransform_allLowerCase() {
        String result = transformer.transform("hello world");
        assertEquals("HELLO WORLD", result);
    }

    @Test
    void testTransform_mixedCase() {
        String result = transformer.transform("HeLlO WoRlD");
        assertEquals("HELLO WORLD", result);
    }
}
