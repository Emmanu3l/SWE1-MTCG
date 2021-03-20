package main.mtcg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.mtcg.cards.*;

import java.util.ArrayList;

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
        //json will read the input as an array, therefore I have to turn it into an object afterward to avoid errors
        ArrayList<Card> cardList = new ObjectMapper().readValue(s, new TypeReference<>(){});
        return new Pack(cardList);
        //return new ObjectMapper().readValue(s, Pack.class);
    }

    public static String serializePack(Pack pack) throws JsonProcessingException {
        //TODO: need to create a json serialize object
        return new ObjectMapper().writeValueAsString(Pack.class);
    }

    public static ArrayList<String> parseIDList(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, new TypeReference<>(){});
    }

    public static CardCollection parseCardCollection(String s) throws JsonProcessingException {
        ArrayList<Card> cardList = new ObjectMapper().readValue(s, new TypeReference<>(){});
        return new CardCollection(cardList);
    }

    public static String serializeCardCollection(CardCollection cardCollection) throws JsonProcessingException {
        //TODO: need to create a json serialize object
        return new ObjectMapper().writeValueAsString(CardCollection.class);
    }


    public static Trade parseTrade(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, Trade.class);
    }

    public static String serializeTrade(Trade trade) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(Trade.class);
    }

}
