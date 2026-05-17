package pl.put.poznan.texttransformer.service.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.put.poznan.texttransformer.service.TransformerService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * implementing the Registry pattern to dynamically look up text transformation strategies
 */
@Service
public class TransformationServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(TransformationServiceRegistry.class);

    /**
     * Maps lowercase service names to instance of the TransformerService
     */
    private final Map<String, TransformerService> registry;

    /**
     * @param services list of TransforerService objects. Their names must be unique (case-insensitive)
     */
    public TransformationServiceRegistry(List<TransformerService> services) {
        this.registry = services.stream()
                .collect(Collectors.toMap(
                        service -> service.getName().toLowerCase(),
                        Function.identity()
                ));

        for (String key : this.registry.keySet()) {
            logger.info("Loaded \"{}\" service", key);
        }
        logger.info("Total loaded {} transformer service(s)", this.registry.size());
    }

    /**
     * Retrieves a specific transformation service by its name.
     * @param name name of the service to retrieve
     * @return instance of the appropriate TransformService service
     * @throws IllegalArgumentException if name doesn't exist in the registry
     */
    public TransformerService getService(String name) throws IllegalArgumentException {
        String lowercaseInput = name.toLowerCase();
        logger.info("Retrieving transformer service with name \"{}\"", lowercaseInput);
        TransformerService service = registry.get(lowercaseInput);
        if (service == null) {
            logger.info("Service with name \"{}\" doesn't exist in the registry", lowercaseInput);
            throw new IllegalArgumentException("No service with name " + lowercaseInput + " exists");
        }
        return service;
    }
}
