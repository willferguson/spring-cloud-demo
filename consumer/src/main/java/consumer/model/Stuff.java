package consumer.model;

import java.util.Objects;

public class Stuff {

    private String id;
    private int size;

    public Stuff() {
    }

    public Stuff(String id, int size) {
        this.id = id;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stuff stuff = (Stuff) o;
        return size == stuff.size && Objects.equals(id, stuff.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size);
    }
}
