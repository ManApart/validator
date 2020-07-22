import org.junit.Assert
import org.junit.Test

class PathPiecesToolsTest {

    @Test
    fun dotPathToCamelCase() {
        val input = "grandParent.parent.child.name"
        val actual = dotPathToCamelCase(input)
        Assert.assertEquals("GrandParentParentChildName", actual)
    }

    @Test
    fun highlightNullPiece() {
        val input = "grandParent.parent.child.name".split(".")
        val actual = highlightNullPiece(input, 0)
        Assert.assertEquals("{grandParent}.parent.child.name", actual)
    }

    @Test
    fun highlightNullPiece2() {
        val input = "grandParent.parent.child.name".split(".")
        val actual = highlightNullPiece(input, 1)
        Assert.assertEquals("grandParent.{parent}.child.name", actual)
    }
}