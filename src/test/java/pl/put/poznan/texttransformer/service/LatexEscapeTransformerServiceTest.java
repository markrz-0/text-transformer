package pl.put.poznan.texttransformer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LatexEscapeTransformerServiceTest {

    private LatexEscapeTransformerService transformer;

    @BeforeEach
    void setUp() {
        transformer = new LatexEscapeTransformerService();
    }

    @Test
    void testTransform_escapeAmpersand() {
        String result = transformer.transform("Me & You");
        assertEquals("Me \\& You", result);
    }

    @Test
    void testTransform_escapeDollarSign() {
        String result = transformer.transform("Cost is 50$");
        assertEquals("Cost is 50\\$", result);
    }
}
