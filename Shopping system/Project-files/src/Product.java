public abstract class Product {
    private String pId;
    private String pName;
    private int noOfAvailItems;
    private double itemPrice;
    private String clientId;

    public Product(String pId, String pName, int noOfAvailItems, double itemPrice) {
        this.pId = pId;
        this.pName = pName;
        this.noOfAvailItems = noOfAvailItems;
        this.itemPrice = itemPrice;
        this.clientId = clientId;
    }

    public String getProductId() {
        return pId;
    }

    public void setProductId(String pId) {
        this.pId = pId;
    }

    public String getProductName() {
        return pName;
    }

    public void setProductName(String pName) {
        this.pName = pName;
    }

    public int getNoOfAvailItems() {
        return noOfAvailItems;
    }

    public void setNoOfAvailItems(int noOfAvailItems) {
        this.noOfAvailItems = noOfAvailItems;
    }


    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public abstract String toFileString();

    public String getClientId() {
        return clientId;
    }
}
