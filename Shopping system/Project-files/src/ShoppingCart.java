import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShoppingCart {
    private ArrayList<Product> listOfProducts;
    private static shoppinghistory ShoppingHistory;

    public ShoppingCart() {
        listOfProducts = new ArrayList<>();
        ShoppingHistory = new shoppinghistory();
    }

    public void addAProduct(Product product) {
        listOfProducts.add(product);                  // add products to listOfProducts

        String clientId = product.getClientId();
        ShoppingHistory.addPurchase(clientId);
    }

    public static int getUserPurchCount(String clientId) {
        return ShoppingHistory.getUserPurchCount(clientId);
    }

    public void deleteAProduct(String productId) {
        Product deletedProduct = null;

        for (Product product : listOfProducts) {
            if (product.getProductId().equals(productId)) {
                deletedProduct = product;
                break;
            }
        }

        if (deletedProduct != null) {
            listOfProducts.remove(deletedProduct);

            System.out.println("Product " + deletedProduct.getProductId() + ": " + deletedProduct.getProductName() + " has been deleted successfully");

            if (deletedProduct instanceof Electronics) {
                System.out.println("From: Electronics");
            } else if (deletedProduct instanceof Clothing) {
                System.out.println("From: Clothing");
            }

            System.out.println("Total number of products left: " + listOfProducts.size());
        } else {
            System.out.println("Product " + productId + " does not exist.");
        }
    }

    public void printProductList() {
        Collections.sort(listOfProducts, Comparator.comparing(Product::getProductId));                     // Print the products alphabetically

        for (Product product : listOfProducts) {
            System.out.println("ID: " + product.getProductId());
            System.out.println("-Name             : " + product.getProductName());
            System.out.println("-Available Items  : " + product.getNoOfAvailItems());
            System.out.println("-Price            : " + product.getItemPrice());

            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                System.out.println("-Type             : Electronics");
                System.out.println("-Brand            : " + electronics.getBrand());
                System.out.println("-Warranty Period  : " + electronics.getWarrPeriod());
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                System.out.println("-Type             : Clothing");
                System.out.println("-Size             : " + clothing.getSize());
                System.out.println("-Color            : " + clothing.getColour());
            }

            System.out.println();
        }
    }

    public void saveInFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Product product : listOfProducts) {
                writer.println(product.toFileString());
            }
            System.out.println("List of products have been saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Product product = createProductFromLine(line);
                if (product != null) {
                    listOfProducts.add(product);
                }
            }
            System.out.println("List of products have been loaded to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Product createProductFromLine(String line) {
        String[] parts = line.split(",");
        String productId = parts[0];
        String productName = parts[1];
        int availableItems = Integer.parseInt(parts[2]);
        double price = Double.parseDouble(parts[3]);

        String productType = parts[4];
        if (productType.equals("Electronics")) {
            String brand = parts[5];
            int warrantyPeriod = Integer.parseInt(parts[6]);
            return new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
        } else if (productType.equals("Clothing")) {
            String size = parts[5];
            String color = parts[6];
            return new Clothing(productId, productName, availableItems, price, size, color);
        }

        return null;
    }

    public ArrayList<Product> getListOfProducts() {
        return new ArrayList<>(listOfProducts);
    }

}
