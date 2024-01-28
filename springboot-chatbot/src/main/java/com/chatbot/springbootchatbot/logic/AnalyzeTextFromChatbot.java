package com.chatbot.springbootchatbot.logic;


import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class AnalyzeTextFromChatbot {
    static Tokenizer tokenizer;

    static {
        // Load the tokenizer model
        try (InputStream modelIn = new FileInputStream("springboot-chatbot/src/main/resources/opennlp-de-ud-gsd-tokens-1.0-1.9.3.bin")) {
            TokenizerModel model = new TokenizerModel(modelIn);
            tokenizer = new TokenizerME(model);
        } catch (IOException e) {
            throw new RuntimeException("Error initializing tokenizer", e);
        }
    }

    public static String tokenizeUserInput(String text) {
            System.out.println(Arrays.toString(tokenizer.tokenize(text)));
            return  Arrays.toString(tokenizer.tokenize(text));
        }
    }
