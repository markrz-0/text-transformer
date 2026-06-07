package pl.put.poznan.texttransformer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseTransformerServiceTest {

    private ReverseTransformerService transformer;

    @BeforeEach
    void setUp() {
        transformer = new ReverseTransformerService();
    }

    @Test
    void testTransform_preservesCase() {
        String result = transformer.transform("MirEk");
        assertEquals("KerIm", result);
    }

    @Test
    void testTransform_allLowerCase() {
        String result = transformer.transform("hello");
        assertEquals("olleh", result);
    }
}
