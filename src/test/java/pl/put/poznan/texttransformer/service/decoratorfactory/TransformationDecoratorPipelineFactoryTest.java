package pl.put.poznan.texttransformer.service.decoratorfactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.put.poznan.texttransformer.service.IdentityTransformerService;
import pl.put.poznan.texttransformer.service.TransformerService;
import pl.put.poznan.texttransformer.service.registry.TransformationServiceRegistry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransformationDecoratorPipelineFactoryTest {

    @Mock
    private TransformationServiceRegistry registry;

    private TransformationDecoratorPipelineFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new TransformationDecoratorPipelineFactory(registry);
    }

    @Test
    public void testBuildPipelineWithEmptyTransformationsReturnsIdentity() {
        TransformerService pipeline = factory.buildPipeline(new String[]{});
        assertNotNull(pipeline);
        assertTrue(pipeline instanceof IdentityTransformerService);
        assertEquals("input text", pipeline.transform("input text"));
    }

    @Test
    public void testBuildPipelineWithEmptyTransformationsDoesNotInteractWithRegistry() {
        factory.buildPipeline(new String[]{});
        verifyNoInteractions(registry);
    }

    @Test
    public void testBuildPipelineWithSingleTransformationReturnsWrappedService() {
        TransformerService upperMock = mock(TransformerService.class);
        when(registry.getService("upper")).thenReturn(upperMock);
        when(upperMock.transform("hello")).thenReturn("HELLO");

        TransformerService pipeline = factory.buildPipeline(new String[]{"upper"});
        assertNotNull(pipeline);
        assertEquals("HELLO", pipeline.transform("hello"));

        verify(registry, times(1)).getService("upper");
        verify(upperMock, times(1)).transform("hello");
    }

    @Test
    public void testBuildPipelineWithMultipleTransformationsAppliesThemInOrder() {
        TransformerService upperMock = mock(TransformerService.class);
        TransformerService reverseMock = mock(TransformerService.class);

        when(registry.getService("upper")).thenReturn(upperMock);
        when(registry.getService("reverse")).thenReturn(reverseMock);

        when(upperMock.transform("hello")).thenReturn("HELLO");
        when(reverseMock.transform("HELLO")).thenReturn("OLLEH");

        TransformerService pipeline = factory.buildPipeline(new String[]{"upper", "reverse"});
        assertNotNull(pipeline);
        assertEquals("OLLEH", pipeline.transform("hello"));

        InOrder inOrder = inOrder(upperMock, reverseMock);
        inOrder.verify(upperMock).transform("hello");
        inOrder.verify(reverseMock).transform("HELLO");
    }

    @Test
    public void testBuildPipelineWithInvalidTransformationThrowsException() {
        when(registry.getService("invalid")).thenThrow(new IllegalArgumentException("No service with name invalid exists"));

        assertThrows(IllegalArgumentException.class, () -> {
            factory.buildPipeline(new String[]{"invalid"});
        });

        verify(registry, times(1)).getService("invalid");
    }

    @Test
    public void testBuildPipelineWithMixedValidAndInvalidTransformationsThrowsException() {
        TransformerService upperMock = mock(TransformerService.class);
        when(registry.getService("upper")).thenReturn(upperMock);
        when(registry.getService("invalid")).thenThrow(new IllegalArgumentException("No service with name invalid exists"));

        assertThrows(IllegalArgumentException.class, () -> {
            factory.buildPipeline(new String[]{"upper", "invalid"});
        });

        verify(registry, times(1)).getService("upper");
        verify(registry, times(1)).getService("invalid");
    }

    @Test
    public void testBuildPipelineWithDuplicateTransformationsReusesService() {
        TransformerService upperMock = mock(TransformerService.class);
        when(registry.getService("upper")).thenReturn(upperMock);

        when(upperMock.transform("hello")).thenReturn("HELLO");
        when(upperMock.transform("HELLO")).thenReturn("HELLO_AGAIN");

        TransformerService pipeline = factory.buildPipeline(new String[]{"upper", "upper"});
        assertNotNull(pipeline);
        assertEquals("HELLO_AGAIN", pipeline.transform("hello"));

        verify(registry, times(2)).getService("upper");
        verify(upperMock, times(1)).transform("hello");
        verify(upperMock, times(1)).transform("HELLO");
    }

    @Test
    public void testBuildPipelineWithNullTransformationThrowsException() {
        when(registry.getService(null)).thenThrow(new IllegalArgumentException("Null service name"));

        assertThrows(IllegalArgumentException.class, () -> {
            factory.buildPipeline(new String[]{null});
        });

        verify(registry, times(1)).getService(null);
    }

    @Test
    public void testBuildPipelinePreservesExactTransformationNamesToRegistry() {
        TransformerService upperMock = mock(TransformerService.class);
        when(registry.getService("UpPeR")).thenReturn(upperMock);

        factory.buildPipeline(new String[]{"UpPeR"});

        verify(registry, times(1)).getService("UpPeR");
    }

    @Test
    public void testBuildPipelineWithThreeTransformationsChainsAllCorrectly() {
        TransformerService t1 = mock(TransformerService.class);
        TransformerService t2 = mock(TransformerService.class);
        TransformerService t3 = mock(TransformerService.class);

        when(registry.getService("t1")).thenReturn(t1);
        when(registry.getService("t2")).thenReturn(t2);
        when(registry.getService("t3")).thenReturn(t3);

        when(t1.transform("start")).thenReturn("start1");
        when(t2.transform("start1")).thenReturn("start2");
        when(t3.transform("start2")).thenReturn("start3");

        TransformerService pipeline = factory.buildPipeline(new String[]{"t1", "t2", "t3"});
        assertNotNull(pipeline);
        assertEquals("start3", pipeline.transform("start"));

        InOrder inOrder = inOrder(t1, t2, t3);
        inOrder.verify(t1).transform("start");
        inOrder.verify(t2).transform("start1");
        inOrder.verify(t3).transform("start2");
    }
}
