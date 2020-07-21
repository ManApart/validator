package example.java.expectedGeneration;

import example.java.source.GreatGrandParent;

public class GreatGrandParentValidator {
    public static String validate(GreatGrandParent object) {
        return validateGrandParentParentChildName(object);
    }

    private static String validateGrandParentParentChildName(GreatGrandParent object) {
        if (object == null) {
            return "{object}.grandParent.parent.child.name";
        } else if(object.getGrandParent() == null){
            return "object.{grandParent}.parent.child.name";
        } else if(object.getGrandParent().getParent() == null){
            return "object.grandParent.{parent}.child.name";
        } else if(object.getGrandParent().getParent().getChild() == null){
            return "object.grandParent.parent.{child}.name";
        } else if(object.getGrandParent().getParent().getChild().getName() == null){
            return "object.grandParent.parent.child.{name}";
        }

        return null;
    }
}
