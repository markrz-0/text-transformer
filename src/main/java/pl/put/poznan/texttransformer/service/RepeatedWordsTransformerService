package pl.put.poznan.texttransformer.service;

import org.springframework.stereotype.Service;

@Service
public class RepeatedWordsTransformerService extends TransformerService {

    @Override
    public String getName() {
        // Ta nazwa (np. "repeated-words" lub "remove-repeats") będzie używana 
        // w JSONie podczas wysyłania zapytania do API
        return "repeated-words"; 
    }

    @Override
    public String transform(String text) {
        // Zabezpieczenie przed pustym tekstem
        if (text == null || text.isEmpty()) {
            return text;
        }

        // Regex wyszukujący powtarzające się słowa (niezależnie od wielkości liter)
        String regex = "(?i)\\b(\\w+)(\\s+\\1\\b)+";
        
        // Podmiana na pojedyncze słowo (pierwszą znalezioną grupę)
        return text.replaceAll(regex, "$1");
    }
}
