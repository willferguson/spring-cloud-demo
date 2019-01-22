package burrito.fillingservice.model.salad;

public class Salad {

    private SaladType saladType;
    private Integer quantity;

    public Salad() {
    }

    public Salad(SaladType saladType, Integer quantity) {
        this.saladType = saladType;
        this.quantity = quantity;
    }

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
}
