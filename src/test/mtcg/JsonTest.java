package mtcg;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.mtcg.Json;
import main.mtcg.User;
import main.mtcg.cards.Card;
import main.mtcg.cards.Pack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

    @Test
    void parseUser() throws JsonProcessingException {
        User user1 = new User("kienboec", "daniel");
        User user2 = Json.parseUser("{\"Username\":\"kienboec\", \"Password\":\"daniel\"}");
        assertEquals(user1, user2);
    }

    @Test
    void serializeUser() {
    }

    @Test
    void parseCard() throws JsonProcessingException {
        Card card1 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c08", "WaterGoblin", 10.0);
        Card card2 = Json.parseCard("{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}");
        assertEquals(card1, card2);
    }

    @Test
    void serializeCard() throws JsonProcessingException {
        Card card1 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c08", "WaterGoblin", 10.0);
        String card1Json = Json.serializeCard(card1);
        String card2Json = "{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}";
        assertEquals(card1Json, card2Json);
    }

    @Test
    void parseBattle() {
    }

    @Test
    void parsePack() throws JsonProcessingException {
        //Pack pack1 = Json.parsePack("[{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"FireSpell\",    \"Damage\": 25.0}]");
        //TODO: manually create a pack and compare it
    }

    @Test
    void serializePack() {
    }

    @Test
    void parseDeck() {
    }

    @Test
    void parseTrade() {
    }
}