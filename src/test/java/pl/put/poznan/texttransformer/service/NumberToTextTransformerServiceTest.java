package pl.put.poznan.texttransformer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberToTextTransformerServiceTest {

    private NumberToTextTransformerService transformer;

    @BeforeEach
    void setUp() {
        transformer = new NumberToTextTransformerService();
    }

    @Test
    void testTransform_integer() {
        String result = transformer.transform("I have 2 apples");
        assertEquals("I have two apples", result);
    }

    @Test
    void testTransform_decimal() {
        String result = transformer.transform("The price is 2.5 dollars");
        assertEquals("The price is two point five dollars", result);
    }
}
