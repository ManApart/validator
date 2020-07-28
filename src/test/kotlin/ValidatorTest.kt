import example.java.source.GreatGrandParent
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidatorTest {


    @Test
    fun generateSingleFieldValidationCode() {
        val result = createValidator(GreatGrandParent::class.java, "grandParent.parent.child.name", "grandParent.parent.child.age").replace("  ", "")

        assertEquals(singleFieldValidation, result)
    }


}