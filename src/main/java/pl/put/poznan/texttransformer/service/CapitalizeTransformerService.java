package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

@Service
public class CapitalizeTransformerService extends TransformerService {

    @Override
    public String getName() {
        // Zwracamy nazwę zgodną z wymaganiami w backlogu
        return "capitalize"; 
    }

    @Override
    public String transform(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        char[] chars = text.toCharArray();
        boolean capitalizeNext = true; // Flaga mówiąca, czy następny znak ma być wielką literą

        for (int i = 0; i < chars.length; i++) {
            // Jeśli znak to spacja (lub inny biały znak), kolejne słowo musi zacząć się z wielkiej litery
            if (Character.isWhitespace(chars[i])) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                // Zamieniamy na wielką literę i opuszczamy flagę
                chars[i] = Character.toUpperCase(chars[i]);
                capitalizeNext = false;
            } else {
                // Opcjonalnie: upewniamy się, że reszta liter w słowie jest mała 
                // (zależy od ustaleń w zespole, ale zazwyczaj "capitalize" tak działa)
                chars[i] = Character.toLowerCase(chars[i]);
            }
        }

        return new String(chars);
    }
}
