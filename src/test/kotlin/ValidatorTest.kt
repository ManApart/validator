import example.java.source.Child
import example.java.source.GrandParent
import example.java.source.GreatGrandParent
import example.java.source.Parent
import junit.framework.Assert.assertNull
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidatorTest {

//    @Test
//    fun doThing() {
//        val nested = GrandParent(null)
//        createValidator(listOf(
//            { nested.parent },
//            { nested.parent.child }
//        ))
//    }
//

    @Test
    fun nullSafeGetterThroughReflectionNull() {
        val nested = GreatGrandParent(null)
        val result = getNullSafeValue(nested, "grandParent.parent.child")
        assertNull(result)
    }

    @Test
    fun nullSafeGetterThroughReflectionNotNull() {
        val nested = GreatGrandParent(
            GrandParent(Parent(Child(null)))
        )
        val result = getNullSafeValue(nested, "grandParent.parent.child")
        assertEquals(nested.grandParent.parent.child, result)
    }

    @Test
    fun nullSafeGetterThroughReflectionNotNullWithName() {
        val nested = GreatGrandParent(
            GrandParent(Parent(Child("Child")))
        )
        val result = getNullSafeValue(nested, "grandParent.parent.child.name")
        assertEquals("Child", result)
    }

        @Test
    fun generateSingleFieldValidationCode() {
        val nested = GreatGrandParent(null)
        val result = createValidator(GreatGrandParent::class.java, "grandParent.parent.child.name").replace("  ", "")

            assertEquals(singleFieldValidation, result)
    }

    @Test
    fun dotPathToCamelCase(){
        val input = "grandParent.parent.child.name"
        val actual = dotPathToCamelCase(input)
        assertEquals("GrandParentParentChildName", actual)
    }

    @Test
    fun highlightNullPiece(){
        val input = "grandParent.parent.child.name".split(".")
        val actual = highlightNullPiece(input, 0)
        assertEquals("{grandParent}.parent.child.name", actual)
    }

    @Test
    fun highlightNullPiece2(){
        val input = "grandParent.parent.child.name".split(".")
        val actual = highlightNullPiece(input, 1)
        assertEquals("grandParent.{parent}.child.name", actual)
    }

}