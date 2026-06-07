package pl.put.poznan.texttransformer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToLowerTransformerServiceTest {

    private ToLowerTransformerService transformer;

    @BeforeEach
    void setUp() {
        transformer = new ToLowerTransformerService();
    }

    @Test
    void testTransform_allUpperCase() {
        String result = transformer.transform("HELLO WORLD");
        assertEquals("hello world", result);
    }

    @Test
    void testTransform_mixedCase() {
        String result = transformer.transform("HeLlO WoRlD");
        assertEquals("hello world", result);
    }
}
