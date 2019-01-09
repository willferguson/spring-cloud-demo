package consumer.model;

public class StuffNThing {

    private String id;
    private int size;
    private String name;

    public StuffNThing(String id, int size, String name) {
        this.id = id;
        this.size = size;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
