import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GUIofShopping {
    private JFrame frame;
    private JComboBox<String> productTypeComboBox;
    private JTable productTable;
    private JTextArea productDetailsTextArea;
    private JButton addToCartButton;
    private JButton viewShoppingCartButton;
    private static ShoppingCart shoppingCart;
    private static ShoppingManager shoppingManager;

    private final ArrayList<Product> allProducts = new ArrayList<>();

    private final ArrayList<Product> selectedProducts = new ArrayList<>();

    public GUIofShopping(ShoppingManager shoppingManager) {
        this.shoppingManager = shoppingManager;
        frame = new JFrame("Westminster Shopping Center");
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        productTable = new JTable();
        productDetailsTextArea = new JTextArea();
        addToCartButton = new JButton("Add to Shopping Cart");                    // add to shopping cart button
        viewShoppingCartButton = new JButton("Shopping Cart");                    // button to view the shopping cart
        updatePTable("All");

        initializeGUI();
    }

    private void initializeGUI() {
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();                                               // category selection drop down box
        topPanel.add(new JLabel("Select Product Category "));
        topPanel.add(productTypeComboBox);
        topPanel.add(viewShoppingCartButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        centerPanel.add(productDetailsTextArea, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addToCartButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        addToCartButton.addActionListener(e -> addPToCart());
        viewShoppingCartButton.addActionListener(e -> viewShoppingCart());

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        productTypeComboBox.addActionListener(e -> {
            String selectedCategory = (String) productTypeComboBox.getSelectedItem();
            updatePTable(selectedCategory);
        });

        updatePTable("All");

        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    displayProductDetails(selectedRow);
                }
            }
        });
    }

    private void addPToCart() {
        int selectedRow = productTable.getSelectedRow();

        if (selectedRow != -1) {
            try {
                Product selectedProduct = allProducts.get(selectedRow);

                selectedProducts.add(selectedProduct);

                JOptionPane.showMessageDialog(frame, "Product is in your cart!");
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Invalid!");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a product!");
        }
    }

    private void viewShoppingCart() {
        ArrayList<Product> cartProducts = selectedProducts;

        StringBuilder cartDetails = new StringBuilder("Selected Products:\n");

        for (Product product : cartProducts) {
            cartDetails.append(String.format("%s - %s - %.2f\n", product.getProductId(), product.getProductName(), product.getItemPrice()));
        }

        new ShoppingCartGUI(cartProducts, 0.0).setVisible(true);
    }

    private void updatePTable(String selectedCategory) {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Category");
        model.addColumn("Info");

        ArrayList<Product> productDisplay;

        switch (selectedCategory) {
            case "All":
                productDisplay = shoppingManager.getAllProducts();
                break;
            case "Electronics":
                productDisplay = shoppingManager.getElectronicsProducts();
                break;
            case "Clothing":
                productDisplay = shoppingManager.getClothingProducts();
                break;
            default:
                productDisplay = new ArrayList<>();
        }

        for (Product product : productDisplay) {
            String category = (product instanceof Electronics) ? "Electronics" : "Clothing";
            String info = (product instanceof Electronics)
                    ? ((Electronics) product).getBrand() + " - Warranty: " + ((Electronics) product).getWarrPeriod() + " months"
                    : ((Clothing) product).getSize() + " - Color: " + ((Clothing) product).getColour();

            model.addRow(new Object[]{product.getProductId(), product.getProductName(), category, info});
            allProducts.add(product);
        }

        productTable.setModel(model);
    }


    private void displayProductDetails(int selectedRow) {
        try {
            Object productIdObj = productTable.getValueAt(selectedRow, 0);
            Object productNameObj = productTable.getValueAt(selectedRow, 1);
            Object categoryObj = productTable.getValueAt(selectedRow, 2);

            if (productIdObj != null && productNameObj != null && categoryObj != null) {
                String productId = productIdObj.toString();
                String productName = productNameObj.toString();
                String category = categoryObj.toString();

                String additionalInfo = "";

                if (selectedRow < allProducts.size()) {
                    Product selectedProduct = allProducts.get(selectedRow);

                    if (selectedProduct instanceof Electronics) {
                        Electronics electronicsProduct = (Electronics) selectedProduct;
                        additionalInfo = String.format("Brand: %s, Warranty: %d months",
                                electronicsProduct.getBrand(), electronicsProduct.getWarrPeriod());
                    } else if (selectedProduct instanceof Clothing) {
                        Clothing clothingProduct = (Clothing) selectedProduct;
                        additionalInfo = String.format("Size: %s, Color: %s",
                                clothingProduct.getSize(), clothingProduct.getColour());
                    }
                }
                productDetailsTextArea.setText(String.format("Product ID: %s\nProduct Name: %s\nCategory: %s\n%s",
                        productId, productName, category, additionalInfo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUIofShopping(shoppingManager);
            }
        });
    }
}