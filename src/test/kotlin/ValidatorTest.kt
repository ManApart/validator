import example.java.Child
import example.java.GrandParent
import example.java.GreatGrandParent
import example.java.Parent
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
//    @Test
//    fun doThing2() {
//        val nested = GrandParent(null)
//        createValidator(nested, "parent.child")
//    }

    @Test
    fun nullSafeGetterThroughReflectionNull() {
        val nested = GreatGrandParent(null)
        val result = getNullSafeValue(nested, "grandParent.parent.child")
        assertNull(result)
    }

    @Test
    fun nullSafeGetterThroughReflectionNotNull() {
        val nested = GreatGrandParent(GrandParent(Parent(Child(null))))
        val result = getNullSafeValue(nested, "grandParent.parent.child")
        assertEquals(nested.grandParent.parent.child, result)
    }

    @Test
    fun nullSafeGetterThroughReflectionNotNullWithName() {
        val nested = GreatGrandParent(GrandParent(Parent(Child("Child"))))
        val result = getNullSafeValue(nested, "grandParent.parent.child")
        assertEquals("Child", result)
    }
}