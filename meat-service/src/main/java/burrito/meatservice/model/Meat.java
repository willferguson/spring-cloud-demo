package burrito.meatservice.model;

import java.util.Objects;

public class Meat {

    private MeatType meatType;
    private MeatSize meatSize;

    public Meat() {
    }

    public Meat(MeatType meatType, MeatSize meatSize) {
        this.meatType = meatType;
        this.meatSize = meatSize;
    }

    public MeatType getMeatType() {
        return meatType;
    }

    public void setMeatType(MeatType meatType) {
        this.meatType = meatType;
    }

    public MeatSize getMeatSize() {
        return meatSize;
    }

    public void setMeatSize(MeatSize meatSize) {
        this.meatSize = meatSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meat meat = (Meat) o;
        return meatType == meat.meatType &&
                meatSize == meat.meatSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(meatType, meatSize);
    }

    @Override
    public String toString() {
        return "Meat{" + "meatType=" + meatType + ", meatSize=" + meatSize + '}';
    }
}
