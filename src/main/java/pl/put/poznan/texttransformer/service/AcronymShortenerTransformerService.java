package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AcronymShortenerTransformerService extends TransformerService {

    private final Map<String, String> phraseToAcronymMap;

    public AcronymShortenerTransformerService() {
        this.phraseToAcronymMap = new LinkedHashMap<>();
        phraseToAcronymMap.put("for example", "e.g.");
        phraseToAcronymMap.put("among others", "i.a.");
        phraseToAcronymMap.put("and so on", "aso");
    }

    @Override
    public String getName() {
        return "shorten";
    }

    @Override
    public String transform(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String result = text;

        for (Map.Entry<String, String> entry : phraseToAcronymMap.entrySet()) {
            String phrase = entry.getKey();
            String acronym = entry.getValue();

            String patternStr = "\\b" + Pattern.quote(phrase) + "\\b";
            Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);

            Matcher matcher = pattern.matcher(result);
            StringBuffer sb = new StringBuffer();

            while (matcher.find()) {
                String matchedPhrase = matcher.group();
                String replacement = preserveCase(matchedPhrase, acronym);
                matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
            }
            matcher.appendTail(sb);
            result = sb.toString();
        }

        logger.info("Shortened phrases to acronyms in text");
        return result;
    }

    private String preserveCase(String original, String acronym) {
        if (original == null || original.isEmpty() || acronym == null || acronym.isEmpty()) {
            return acronym;
        }

        String originalLettersOnly = original.replaceAll("[^a-zA-Z]", "");

        if (originalLettersOnly.isEmpty()) {
            return acronym;
        }

        boolean allUpperCase = originalLettersOnly.chars()
                .filter(Character::isLetter)
                .allMatch(Character::isUpperCase);

        boolean firstUpperCase = Character.isUpperCase(originalLettersOnly.charAt(0));

        if (allUpperCase) {
            return acronym.toUpperCase();
        } else if (firstUpperCase) {
            if (acronym.length() > 0) {
                return Character.toUpperCase(acronym.charAt(0)) + acronym.substring(1);
            }
            return acronym;
        } else {
            return acronym.toLowerCase();
        }
    }
}

