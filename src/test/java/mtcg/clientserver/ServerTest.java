package java.mtcg.clientserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.java.mtcg.Json;
import main.java.mtcg.User;
import main.java.mtcg.cards.Card;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void parseUser() throws JsonProcessingException {
        User user1 = new User("kienboec", "daniel");
        User user2 = Json.parseUser("{\"Username\":\"kienboec\", \"Password\":\"daniel\"}");
        assertEquals(user1, user2);
    }

    @Test
    void parseCard() throws JsonProcessingException {
        Card card1 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c08", "WaterGoblin", 10.0);
        Card card2 = Json.parseCard("{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}");
        assertEquals(card1, card2);
    }

    @Test
    void parsePackage() throws JsonProcessingException {
        List<Card> pack1 = Json.parsePackage("[{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"FireSpell\",    \"Damage\": 25.0}]");
        //TODO: manually create a pack and compare it
    }
}