package thingproducer.model;

public class Thing {

    private String id;
    private String name;

    public Thing(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
