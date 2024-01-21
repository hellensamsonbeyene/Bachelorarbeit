package com.chatbot.springbootchatbot.logic;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;


public class newChatbotLogic {
    public static void main(String[] args) {


        StanfordCoreNLP pipeline = new StanfordCoreNLP("german");

        String text = "Angela Merkel ist die Bundeskanzlerin von Deutschland. Olaf Scholz auch.";

        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        System.out.println("analyzed Text" + document.toShorterString());


    }
}
