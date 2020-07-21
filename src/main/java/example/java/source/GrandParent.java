package example.java.source;

public class GrandParent {
    private final Parent parent;

    public GrandParent(Parent parent) {
        this.parent = parent;
    }

    public Parent getParent() {
        return parent;
    }
}
