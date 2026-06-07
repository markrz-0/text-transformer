package pl.put.poznan.texttransformer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CapitalizeTransformerServiceTest {

    private CapitalizeTransformerService transformer;

    @BeforeEach
    void setUp() {
        transformer = new CapitalizeTransformerService();
    }

    @Test
    void testTransform_allLowerCase() {
        String result = transformer.transform("hello world");
        assertEquals("Hello World", result);
    }

    @Test
    void testTransform_mixedCase() {
        String result = transformer.transform("heLlO woRlD");
        assertEquals("Hello World", result);
    }
}
