package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service responsible for expanding specific acronyms into their full phrases.
 * It extends {@link TransformerService} and overrides the {@code transform} method
 * to identify acronyms like "prof." or "e.g." and replace them with their full
 * word equivalents while preserving the original case formatting.
 */
@Service
public class AcronymExpanderTransformerService extends TransformerService {

    private final Map<String, String> acronymMap;

    /**
     * Constructs a new AcronymExpanderTransformerService and initializes the acronym-to-phrase map.
     */
    public AcronymExpanderTransformerService() {
        this.acronymMap = new HashMap<>();
        acronymMap.put("prof.", "professor");
        acronymMap.put("dr", "doctor");
        acronymMap.put("e.g.", "for example");
        acronymMap.put("aso", "and so on");
    }

    /**
     * Retrieves the specific name of this transformer.
     *
     * @return the name of the transformer ("expand")
     */
    @Override
    public String getName() {
        return "expand";
    }

    /**
     * Transforms the given text by expanding known acronyms into full phrases.
     * Preserves capitalization (e.g., "Prof." -> "Professor", "PROF." -> "PROFESSOR").
     *
     * @param text the input string to be transformed
     * @return the transformed string with acronyms expanded
     */
    @Override
    public String transform(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String result = text;

        for (Map.Entry<String, String> entry : acronymMap.entrySet()) {
            String acronym = entry.getKey();
            String expansion = entry.getValue();

            String patternStr;
            if (acronym.contains(".")) {
                patternStr = "\\b" + Pattern.quote(acronym) + "(?=\\s|$)";
            } else {
                patternStr = "\\b" + Pattern.quote(acronym) + "\\b";
            }
            Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);

            Matcher matcher = pattern.matcher(result);
            StringBuffer sb = new StringBuffer();

            while (matcher.find()) {
                String matchedAcronym = matcher.group();
                String replacement = preserveCase(matchedAcronym, expansion);
                matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
            }
            matcher.appendTail(sb);
            result = sb.toString();
        }

        logger.info("Expanded acronyms in text");
        return result;
    }

    private String preserveCase(String original, String expansion) {
        if (original == null || original.isEmpty() || expansion == null || expansion.isEmpty()) {
            return expansion;
        }

        String originalLettersOnly = original.replaceAll("[^a-zA-Z]", "");

        if (originalLettersOnly.isEmpty()) {
            return expansion;
        }

        boolean allUpperCase = originalLettersOnly.chars()
                .filter(Character::isLetter)
                .allMatch(Character::isUpperCase);

        boolean firstUpperCase = Character.isUpperCase(originalLettersOnly.charAt(0));

        if (allUpperCase) {
            return expansion.toUpperCase();
        } else if (firstUpperCase) {
            return Character.toUpperCase(expansion.charAt(0)) + expansion.substring(1);
        } else {
            return expansion.toLowerCase();
        }
    }
}


