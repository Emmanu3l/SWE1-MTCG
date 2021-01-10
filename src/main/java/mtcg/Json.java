package main.java.mtcg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.mtcg.cards.Card;
import main.java.mtcg.cards.Deck;
import main.java.mtcg.cards.Pack;
import main.java.mtcg.cards.Spell;

import java.util.ArrayList;
import java.util.List;

public class Json {

    //TODO: if content type is JSON, get the body and use an objectmapper to convert to an object
    //TODO: maybe just parse JSON first and then figure out what to do with it?
    //https://www.baeldung.com/jackson-object-mapper-tutorial
    public static User parseUser(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, User.class);
    }

    public static String serializeUser(User u) throws JsonProcessingException {
        //TODO: find out how to only print username and password
        return new ObjectMapper().writeValueAsString(u);
    }

    public static Card parseCard(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, Card.class);
    }
    public static String serializeCard(Card c) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(c);
    }

    public static Spell parseSpell(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, Spell.class);
    }

    public static Battle parseBattle(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, Battle.class);
    }

    public static Pack parsePack(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, Pack.class);
    }

    public static String serializePack(Pack pack) throws JsonProcessingException {
        //TODO: need to create a json serialize object
        return new ObjectMapper().writeValueAsString(Pack.class);
    }

    public static Deck parseDeck(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, Deck.class);
    }

    public static Trade parseTrade(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, Trade.class);
    }

}
