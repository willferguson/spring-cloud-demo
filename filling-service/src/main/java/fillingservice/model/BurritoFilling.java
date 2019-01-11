// Copyright (c) 2019 Travelex Ltd

package fillingservice.model;

import fillingservice.model.meat.Meat;
import fillingservice.model.salad.Salad;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BurritoFilling {

    private List<Meat> meat;
    private List<Salad> salad;

    public BurritoFilling() {
        this.meat = new ArrayList<>();
        this.salad = new ArrayList<>();
    }

    public BurritoFilling(List<Meat> meat, List<Salad> salad) {
        this.meat = meat;
        this.salad = salad;
    }

    public List<Meat> getMeat() {
        return meat;
    }

    public void setMeat(List<Meat> meat) {
        this.meat = meat;
    }

    public List<Salad> getSalad() {
        return salad;
    }

    public void setSalad(List<Salad> salad) {
        this.salad = salad;
    }

    public void addMeat(Meat meat) {
        this.meat.add(meat);
    }

    public void addSalad(Salad salad) {
        this.salad.add(salad);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BurritoFilling that = (BurritoFilling) o;
        return Objects.equals(meat, that.meat) && Objects.equals(salad, that.salad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meat, salad);
    }
}
