package stuffproducer.model;

public class Stuff {

    private String id;
    private int size;

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
}
