public class Clothing extends Product{
    private String size;
    private String colour;

    public Clothing(String pId, String pName, int noOfAvailItems, double itemPrice, String size, String colour) {
        super(pId, pName, noOfAvailItems, itemPrice);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toFileString() {
        return String.format("%s,%s,%d,%.2f,Clothing,%s,%s",
                getProductId(), getProductName(), getNoOfAvailItems(), getItemPrice(), getSize(), getColour());
    }
}
