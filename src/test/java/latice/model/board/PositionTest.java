package latice.model.board;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testConstructorAndGetters() {
        Position position = new Position(3, 5);

        assertEquals(3, position.getPosX(), "getPosX() ne retourne pas la bonne valeur");
        assertEquals(5, position.getPosY(), "getPosY() ne retourne pas la bonne valeur");
    }

    @Test
    void testSetterPosX() {
        Position position = new Position(0, 0);
        position.posX(7);
        assertEquals(7, position.getPosX(), "posX() n'a pas correctement modifié posX");
    }

    @Test
    void testSetterPosY() {
        Position position = new Position(0, 0);
        position.posY(9);
        assertEquals(9, position.getPosY(), "posY() n'a pas correctement modifié posY");
    }
}