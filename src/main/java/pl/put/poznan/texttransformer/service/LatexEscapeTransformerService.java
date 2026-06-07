package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service responsible for escaping special characters for LaTeX format.
 * It extends {@link TransformerService} and overrides the {@code transform} method
 * to replace characters like {@code &} and {@code $} with their LaTeX escaped equivalents.
 */
@Service
public class LatexEscapeTransformerService extends TransformerService {

    private final Map<String, String> escapeMap;

    /**
     * Constructs a new LatexEscapeTransformerService and initializes the escape map.
     */
    public LatexEscapeTransformerService() {
        this.escapeMap = new LinkedHashMap<>();
        escapeMap.put("&", "\\&");
        escapeMap.put("$", "\\$");
    }

    /**
     * Retrieves the specific name of this transformer.
     *
     * @return the name of the transformer ("latex")
     */
    @Override
    public String getName() {
        return "latex";
    }

    /**
     * Transforms the given text by escaping characters reserved in LaTeX.
     *
     * @param text the input string to be transformed
     * @return the transformed string with escaped characters
     */
    @Override
    public String transform(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String result = text;

        for (Map.Entry<String, String> entry : escapeMap.entrySet()) {
            String character = entry.getKey();
            String escaped = entry.getValue();
            result = result.replace(character, escaped);
        }

        logger.info("Escaped special characters for LaTeX format");
        return result;
    }
}

