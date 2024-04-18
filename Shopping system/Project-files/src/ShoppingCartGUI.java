import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ShoppingCartGUI extends JFrame {
    private JTable productsTable;
    private JTextArea cartDetailsDisplay;

    public ShoppingCartGUI(ArrayList<Product> cartProducts, double v) {
        setTitle("Shopping Cart");
        setSize(600, 400);
        setLocationRelativeTo(null);

        DefaultTableModel tableModel = new DefaultTableModel();       // Table to display added products
        tableModel.addColumn("Product");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");

        productsTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productsTable);

        cartDetailsDisplay = new JTextArea();
        cartDetailsDisplay.setEditable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(cartDetailsDisplay, BorderLayout.SOUTH);

        add(mainPanel);

        displayProductsTable(cartProducts);
        calAndDisCartDetails(cartProducts);
    }

    private void displayProductsTable(ArrayList<Product> cartProducts) {
        DefaultTableModel tableModel = (DefaultTableModel) productsTable.getModel();
        tableModel.setRowCount(0);

        for (Product product : cartProducts) {
            tableModel.addRow(new Object[]{product.getProductName(), 1, product.getItemPrice()});
        }
    }

    private void calAndDisCartDetails(ArrayList<Product> cartProducts) {
        double totalPrice = calTotalPPrice(cartProducts);
        double firstPurchaseDiscount = calFirstPurchDiscount(cartProducts);
        double categoryDiscount = calThreePurchDiscount(cartProducts);
        double finalPrice = calFinPrice(totalPrice, firstPurchaseDiscount, categoryDiscount);

        StringBuilder cartDetails = new StringBuilder();

        cartDetails.append("Total Price: ").append(totalPrice).append("\n");
        cartDetails.append("First Purchase Discount (%10): ").append(firstPurchaseDiscount).append("\n");
        cartDetails.append("Three Items In Same Category Discount (%20): ").append(categoryDiscount).append("\n");
        cartDetails.append("Final Price: ").append(finalPrice).append("\n");

        cartDetailsDisplay.setText(cartDetails.toString());
    }

    private double calTotalPPrice(ArrayList<Product> cartProducts) {
        double totalPrice = 0;
        for (Product product : cartProducts) {
            totalPrice += product.getItemPrice();
        }
        return totalPrice;
    }

    private double calFirstPurchDiscount(ArrayList<Product> cartProducts) {
        if (!cartProducts.isEmpty()) {
            String clientId = cartProducts.get(0).getClientId();
            int purchaseCount = ShoppingCart.getUserPurchCount(clientId);

            if (purchaseCount == 0) {
                return 0.10 * calTotalPPrice(cartProducts);
            }
        }
        return 0.0;
    }

    private double calThreePurchDiscount(ArrayList<Product> cartProducts) {
        int electronicsCount = 0;
        int clothingCount = 0;

        for (Product product : cartProducts) {
            if (product instanceof Electronics) {
                electronicsCount++;
            } else if (product instanceof Clothing) {
                clothingCount++;
            }
        }

        if (electronicsCount >= 3 || clothingCount >= 3) {
            return 0.20 * calTotalPPrice(cartProducts);
        }

        return 0.0;
    }

    private double calFinPrice(double totalPrice, double firstPurchaseDiscount, double categoryDiscount) {
        double discountedPrice = totalPrice - firstPurchaseDiscount - categoryDiscount;
        return Math.max(discountedPrice, 0);
    }

    public static void main(String[] args) {
        ArrayList<Product> cartProducts = new ArrayList<>();

        SwingUtilities.invokeLater(() -> {
            new ShoppingCartGUI(cartProducts, 0.0).setVisible(true);
        });
    }
}
