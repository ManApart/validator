val singleFieldValidation = """
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GreatGrandParentValidator {
    public static List<String> validate(GreatGrandParent object) {
        return List.of(
                validateGrandParentParentChildName(object),
                validateGrandParentParentChildAge(object)
        ).stream().filter(Objects::nonNull).collect(Collectors.toList());
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

    private static String validateGrandParentParentChildAge(GreatGrandParent object) {
        if (object == null) {
            return "{object}.grandParent.parent.child.age";
        } else if(object.getGrandParent() == null){
            return "object.{grandParent}.parent.child.age";
        } else if(object.getGrandParent().getParent() == null){
            return "object.grandParent.{parent}.child.age";
        } else if(object.getGrandParent().getParent().getChild() == null){
            return "object.grandParent.parent.{child}.age";
        } else if(object.getGrandParent().getParent().getChild().getAge() == null){
            return "object.grandParent.parent.child.{age}";
        }

        return null;
    }
    
}
""".trimIndent().replace("  ", "")