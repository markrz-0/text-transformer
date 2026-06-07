package pl.put.poznan.texttransformer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AcronymExpanderTransformerServiceTest {

    private AcronymExpanderTransformerService transformer;

    @BeforeEach
    void setUp() {
        transformer = new AcronymExpanderTransformerService();
    }

    @Test
    void testTransform_expandProf() {
        String result = transformer.transform("He is a prof. of physics.");
        assertEquals("He is a professor of physics.", result);
    }

    @Test
    void testTransform_preservesCase() {
        String result = transformer.transform("PROF. Smith is here.");
        assertEquals("PROFESSOR Smith is here.", result);
    }
}
