package example.java;

public class GrandParent {
    private final Parent parent;

    public GrandParent(Parent parent) {
        this.parent = parent;
    }

    public Parent getParent() {
        return parent;
    }
}
