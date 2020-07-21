package example.java.source;

public class Parent {
    private final Child child;

    public Parent(Child child) {
        this.child = child;
    }

    public Child getChild() {
        return child;
    }
}
