package pl.put.poznan.texttransformer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepeatedWordsTransformerServiceTest {

    private RepeatedWordsTransformerService transformer;

    @BeforeEach
    void setUp() {
        transformer = new RepeatedWordsTransformerService();
    }

    @Test
    void testTransform_removesRepeatedWords() {
        String result = transformer.transform("This is is a test test");
        assertEquals("This is a test", result);
    }

    @Test
    void testTransform_preservesCaseOfFirstWord() {
        String result = transformer.transform("The THE the sky");
        assertEquals("The sky", result);
    }
}
