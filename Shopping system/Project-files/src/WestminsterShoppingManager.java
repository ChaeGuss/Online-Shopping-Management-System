import java.util.ArrayList;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {

    private static ShoppingCart shoppingCart;

    private static final int maximumProducts = 50;

    public WestminsterShoppingManager() {
        shoppingCart = new ShoppingCart();
    }

    public static void main(String[] args) {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        ShoppingManager shoppingManager = manager;
        GUIofShopping gui = null;

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            ConMenu();
            String option = scanner.nextLine();

            if (option.equals("1")) {
                manager.addNewProduct();
            } else if (option.equals("2")) {
                manager.deleteAProduct();
            } else if (option.equals("3")) {
                manager.printProductList();
            } else if (option.equals("4")) {
                manager.saveInFile("testOutput.txt");
            } else if (option.equals("5")) {
                System.out.println("Exiting console menu");
                exit = true;
            } else if (option.equals("6")) {
                if (gui == null) {
                    gui = new GUIofShopping(shoppingManager);
                } else {
                    System.out.println("Shopping GUI is open.");
                }
            } else {
                System.out.println("Invalid! Enter again");
            }
        }
    }

    public static void ConMenu() {
        System.out.println("1: Add a new product");
        System.out.println("2: Delete a product");
        System.out.println("3: Print the list of the products");
        System.out.println("4: Save in a file");
        System.out.println("5: Exit the console menu");
        System.out.println("6: Open Shopping GUI");
        System.out.print("Enter your choice: ");
    }

    public void addNewProduct() {
        Scanner scanner = new Scanner(System.in);

        int typeOfProduct;

        do {
            System.out.println("Product type: ");
            System.out.println("1: Electronics ");
            System.out.println("2: Clothing");
            typeOfProduct = scanner.nextInt();
            scanner.nextLine();

            if (typeOfProduct != 1 && typeOfProduct != 2) {
                System.out.println("Invalid! Enter 1 or 2: ");
            }

        } while (typeOfProduct != 1 && typeOfProduct != 2);

        String pId;
        boolean isIdValid;

        do {
            System.out.println("Enter the product ID: ");
            pId = scanner.nextLine();

            isIdValid = isPIdValid(pId);               // checks whether the ID already exists in the system

            if (!isIdValid) {
                System.out.println("This ID already exists! Please enter a new ID: ");
            }

        } while (!isIdValid);

        System.out.println("Enter the product name: ");
        String pName = scanner.nextLine();

        int numOfAvailItems;

        do {
            System.out.println("Enter the number of available products: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid! Please enter a valid amount");
                scanner.next();
            }
            numOfAvailItems = scanner.nextInt();
            scanner.nextLine();

        } while (numOfAvailItems <= 0);

        double itemPrice;

        do {
            System.out.println("Enter the product price: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid! Please enter a valid price");
                scanner.next();
            }
            itemPrice = scanner.nextDouble();
            scanner.nextLine();

        } while (itemPrice <= 0);

        if (typeOfProduct == 1) {
            System.out.println("Enter the brand of the product: ");
            String brandName = scanner.nextLine();

            System.out.println("Enter the warranty period: ");
            int warrPeriod = scanner.nextInt();
            scanner.nextLine();

            Electronics nElectronics = new Electronics(pId, pName, numOfAvailItems, itemPrice, brandName, warrPeriod);
            shoppingCart.addAProduct(nElectronics);
        } else if (typeOfProduct == 2) {
            System.out.println("Enter the size of the product: ");
            String size = scanner.nextLine();

            System.out.println("Enter the colour of the product: ");
            String colour = scanner.nextLine();

            Clothing nClothing = new Clothing(pId, pName, numOfAvailItems, itemPrice, size, colour);
            shoppingCart.addAProduct(nClothing);
        }
    }

    private boolean isPIdValid(String productId) {                         // check whether the product id already exists or not
        for (Product product : shoppingCart.getListOfProducts()) {
            if (product.getProductId().equals(productId)) {
                return false;
            }
        }
        return true;
    }

    public void deleteAProduct(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the product ID to delete: ");
        String productIdToDelete = scanner.nextLine();

        shoppingCart.deleteAProduct(productIdToDelete);
    }

    public static void printProductList(){
        shoppingCart.printProductList();
    }

    public static void saveInFile(String file){
        shoppingCart.saveInFile("ShoppingCartData.txt");
    }

    @Override
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return shoppingCart.getListOfProducts();
    }

    @Override
    public ArrayList<Product> getElectronicsProducts() {
        return filterByPCategory("Electronics");
    }

    @Override
    public ArrayList<Product> getClothingProducts() {
        return filterByPCategory("Clothing");
    }

    private ArrayList<Product> filterByPCategory(String type) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : shoppingCart.getListOfProducts()) {
            if (product instanceof Electronics && type.equals("Electronics")) {
                filteredProducts.add(product);
            } else if (product instanceof Clothing && type.equals("Clothing")) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }


}