package mtcg;

import main.mtcg.Battle;
import main.mtcg.User;
import main.mtcg.cards.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Test
    void returnWinner() {
        User battler1 = new User("battler1", "pass1");
        User battler2 = new User("battler2", "pass2");
        battler1.addToDeck(new Card("845f0dc7-37d0-426e-994e-43fc3ac83c08", "WaterGoblin", 10.0));
        battler2.addToDeck(new Card("99f8f8dc-e25e-4a95-aa2c-782823f36e2a", "Dragon", 50.0));
        Battle battle = new Battle(battler1, battler2);
        assertEquals(battle.returnWinner(), battler2);
    }

    @Test
    void returnWinningCard() {
        Card card1 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c08", "WaterGoblin", 10.0);
        Card card2 = new Card("99f8f8dc-e25e-4a95-aa2c-782823f36e2a", "Dragon", 50.0);
        Card winnerCard = new Battle().returnWinningCard(card1, card2);
        assertEquals(winnerCard, card2);
    }
}