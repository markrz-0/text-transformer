package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LatexEscapeTransformerService extends TransformerService {

    private final Map<String, String> escapeMap;

    public LatexEscapeTransformerService() {
        this.escapeMap = new LinkedHashMap<>();
        escapeMap.put("&", "\\&");
        escapeMap.put("$", "\\$");
    }

    @Override
    public String getName() {
        return "latex";
    }

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

