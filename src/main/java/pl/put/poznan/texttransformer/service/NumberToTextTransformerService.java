package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NumberToTextTransformerService extends TransformerService {

    private static final String[] ONES = {
        "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
        "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
        "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = {
        "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    private static final String[] HUNDREDS = {
        "", "one hundred", "two hundred", "three hundred", "four hundred",
        "five hundred", "six hundred", "seven hundred", "eight hundred", "nine hundred"
    };

    @Override
    public String getName() {
        return "number-to-text";
    }

    @Override
    public String transform(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        Pattern pattern = Pattern.compile("\\b(\\d+(?:\\.\\d{1,2})?)\\b");
        Matcher matcher = pattern.matcher(text);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String numberStr = matcher.group(1);
            String textRepresentation = convertNumberToText(numberStr);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(textRepresentation));
        }
        matcher.appendTail(sb);

        logger.info("Converted numbers to text representation");
        return sb.toString();
    }

    private String convertNumberToText(String numberStr) {
        try {
            if (numberStr.contains(".")) {
                return convertDecimalToText(numberStr);
            } else {
                int number = Integer.parseInt(numberStr);
                if (number < 0 || number > 1000) {
                    return numberStr;
                }
                String result = convertIntegerToText(number);
                return result.isEmpty() ? "zero" : result;
            }
        } catch (NumberFormatException e) {
            return numberStr;
        }
    }

    private String convertDecimalToText(String decimalStr) {
        String[] parts = decimalStr.split("\\.");
        int integerPart = Integer.parseInt(parts[0]);
        String decimalPart = parts[1];

        if (integerPart < 0 || integerPart > 1000) {
            return decimalStr;
        }

        StringBuilder result = new StringBuilder();

        String integerText = convertIntegerToText(integerPart);
        if (integerText.isEmpty()) {
            result.append("zero");
        } else {
            result.append(integerText);
        }

        result.append(" point ");

        for (char digit : decimalPart.toCharArray()) {
            int digitValue = Character.getNumericValue(digit);
            if (digitValue >= 0 && digitValue <= 9) {
                if (result.charAt(result.length() - 1) != ' ') {
                    result.append(" ");
                }
                String digitText = ONES[digitValue];
                result.append(digitText.isEmpty() ? "zero" : digitText);
            }
        }

        return result.toString().trim();
    }

    private String convertIntegerToText(int number) {
        if (number == 0) {
            return "zero";
        }

        if (number == 1000) {
            return "one thousand";
        }

        StringBuilder result = new StringBuilder();

        int hundreds = number / 100;
        if (hundreds > 0) {
            result.append(HUNDREDS[hundreds]);
            number %= 100;
            if (number > 0) {
                result.append(" ");
            }
        }

        if (number >= 20) {
            int tens = number / 10;
            result.append(TENS[tens]);
            number %= 10;
            if (number > 0) {
                result.append("-");
                result.append(ONES[number]);
            }
        } else if (number > 0) {
            result.append(ONES[number]);
        }

        return result.toString();
    }
}

