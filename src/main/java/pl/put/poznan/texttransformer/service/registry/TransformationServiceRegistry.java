package pl.put.poznan.texttransformer.service.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.texttransformer.service.TransformerService;

import java.util.Map;

public class TransformationServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(TransformationServiceRegistry.class);

    private static final Map<String, TransformerService> registry = Map.ofEntries(
            // Example: name of the service (must be lowercase), instance of this service
            // Map.entry("toupper", new ToUpperTransformerService()),
            // Map.entry("tolower", new ToLowerTransformerService())
    );


    /**
     * @param name name of the service to retrieve
     * @return instance of the appropriate TransformService service
     * @throws IllegalArgumentException if name doesn't exist in the registry
     */
    public TransformerService getService(String name) throws IllegalArgumentException {
        String lowercaseInput = name.toLowerCase();
        logger.info("Retrieving transformer service with name \"{}\"", lowercaseInput);
        TransformerService service = registry.get(lowercaseInput);
        if (service == null) {
            logger.debug("Service with name \"{}\" doesn't exist in the registry", lowercaseInput);
            throw new IllegalArgumentException("No service with name " + lowercaseInput + " exists");
        }
        return service;
    }
}
