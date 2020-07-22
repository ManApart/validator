import example.java.source.Child
import example.java.source.GrandParent
import example.java.source.GreatGrandParent
import example.java.source.Parent
import junit.framework.Assert.assertNull
import org.junit.Test

class ReflectionExampleTest {

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
        org.junit.Assert.assertEquals(nested.grandParent.parent.child, result)
    }

    @Test
    fun nullSafeGetterThroughReflectionNotNullWithName() {
        val nested = GreatGrandParent(
            GrandParent(Parent(Child("Child")))
        )
        val result = getNullSafeValue(nested, "grandParent.parent.child.name")
        org.junit.Assert.assertEquals("Child", result)
    }

}