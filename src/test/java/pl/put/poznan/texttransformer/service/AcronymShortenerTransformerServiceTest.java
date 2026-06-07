package pl.put.poznan.texttransformer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AcronymShortenerTransformerServiceTest {

    private AcronymShortenerTransformerService transformer;

    @BeforeEach
    void setUp() {
        transformer = new AcronymShortenerTransformerService();
    }

    @Test
    void testTransform_shortenForExample() {
        String result = transformer.transform("This is for example a test.");
        assertEquals("This is e.g. a test.", result);
    }

    @Test
    void testTransform_preservesCase() {
        String result = transformer.transform("FOR EXAMPLE it is capitalized.");
        assertEquals("E.G. it is capitalized.", result);
    }
}
