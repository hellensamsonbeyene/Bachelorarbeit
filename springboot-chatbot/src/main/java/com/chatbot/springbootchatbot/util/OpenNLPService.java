package com.chatbot.springbootchatbot.util;

/*import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.io.InputStream;

@Service
public class OpenNLPService {
    private static TokenizerME tokenizer;
    private NameFinderME nameFinder;
    @PostConstruct
    public void initializeModels() {
        try {
            // Lade das Tokenizer-Modell
            InputStream modelIn = getClass().getResourceAsStream("/opennlp-de-ud-gsd-tokens-1.0-1.9.3.bin");
            assert modelIn != null;
            TokenizerModel model = new TokenizerModel(modelIn);
            modelIn.close();
            tokenizer = new TokenizerME(model);
            System.out.println("HELLO"+modelIn);
            // Lade das NER-Modell
            InputStream nerModelIn = getClass().getResourceAsStream("/opennlp-de-ner.bin");
            assert nerModelIn != null;
            TokenNameFinderModel nerModel = new TokenNameFinderModel(nerModelIn);
            nerModelIn.close();
            nameFinder = new NameFinderME(nerModel);
            System.out.println("HELLO"+nerModel);

        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
        }
    }

    public static String[] tokenize(String text) {
        return tokenizer.tokenize(text);
    }

    public Span[] findEntities(String[] tokens) {
        return nameFinder.find(tokens);
    }
}
*/
