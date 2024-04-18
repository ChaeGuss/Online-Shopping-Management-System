public class Electronics extends Product {
    private String brandName;
    private int warrPeriod;

    public Electronics(String pId, String pName, int noOfAvailItems, double itemPrice, String brandName, int warrPeriod) {
        super(pId, pName, noOfAvailItems, itemPrice);
        this.brandName = brandName;
        this.warrPeriod = warrPeriod;
    }

    public String getBrand() {
        return brandName;
    }

    public void setBrand(String brand) {
        this.brandName = brand;
    }

    public int getWarrPeriod() {
        return warrPeriod;
    }

    public void setWarrPeriod(int warrPeriod) {
        this.warrPeriod = warrPeriod;
    }

    @Override
    public String toFileString() {
        return String.format("%s,%s,%d,%.2f,Electronics,%s,%d",
                getProductId(), getProductName(), getNoOfAvailItems(), getItemPrice(), getBrand(), getWarrPeriod());
    }

}
