import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

class WestminsterShoppingManagerTest {

    @Test
    void addNewProduct() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();

        String input = "1\nA01\nWashingMachine\n24\n321240.0\nAbans\n6\n";

        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

        manager.addNewProduct();
        System.setIn(System.in);

        ShoppingCart shoppingCart = manager.getShoppingCart();
        assertEquals(1, shoppingCart.getListOfProducts().size());
    }

    @Test
    void deleteAProduct() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        ShoppingCart shoppingCart = manager.getShoppingCart();

        String Input = "2\nA02\nTShirt\n13\n32340.0\nM\nWhite\n";
        System.setIn(new java.io.ByteArrayInputStream(Input.getBytes()));
        manager.addNewProduct();
        System.setIn(System.in);

        assertEquals(1, shoppingCart.getListOfProducts().size());

        String deleteInput = "A02\n";
        System.setIn(new java.io.ByteArrayInputStream(deleteInput.getBytes()));
        manager.deleteAProduct();
        System.setIn(System.in);

        assertEquals(0, shoppingCart.getListOfProducts().size());
    }
}