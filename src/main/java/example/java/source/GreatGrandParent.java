package example.java.source;

public class GreatGrandParent {
    private final GrandParent parent;

    public GreatGrandParent(GrandParent grandParent) {
        this.parent = grandParent;
    }

    public GrandParent getGrandParent() {
        return parent;
    }
}
