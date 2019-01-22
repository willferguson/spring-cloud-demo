package burrito.saladservice.model;

public class Salad {

    private SaladType saladType;
    private Integer quantity;

    public SaladType getSaladType() {
        return saladType;
    }

    public void setSaladType(SaladType saladType) {
        this.saladType = saladType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Salad{" + "saladType=" + saladType + ", quantity=" + quantity + '}';
    }
}
