import java.util.ArrayList;

public interface ShoppingManager {
    ArrayList<Product> getAllProducts();
    ArrayList<Product> getElectronicsProducts();
    ArrayList<Product> getClothingProducts();

    ShoppingCart getShoppingCart();
}
