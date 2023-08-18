import TriangleMinPath._
import org.scalatest.funsuite.AnyFunSuite

class TriangleMinPathSpec extends AnyFunSuite {

  test("Test minimal path for given example") {
    val data = List("7", "6 3", "3 8 5", "11 2 10 9")
    val result = parseTriangle(data).map(minimalPath)
    assert((List(7,6,3,2), 18) == result.toOption.get)
  }
}
