package burrito.pricingservice.model;

import java.util.Objects;

public class PricingResult {
    private final String value;
    private final String currencyCode;
    private final String taxComponent;

    public PricingResult(String value, String currencyCode, String taxComponent) {
        this.value = value;
        this.currencyCode = currencyCode;
        this.taxComponent = taxComponent;
    }

    public String getValue() {
        return value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getTaxComponent() {
        return taxComponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricingResult that = (PricingResult) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(currencyCode, that.currencyCode) &&
                Objects.equals(taxComponent, that.taxComponent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currencyCode, taxComponent);
    }
}
