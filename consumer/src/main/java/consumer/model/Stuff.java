package consumer.model;

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
}
