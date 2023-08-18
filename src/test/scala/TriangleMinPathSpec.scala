import cats.effect.IO
import org.scalatest.funsuite.AnyFunSuite
import cats.effect.unsafe.implicits.global

class TriangleMinPathSpec extends AnyFunSuite {

  test("Test minimal path for given example") {
    val data = List("7", "6 3", "3 8 5", "11 2 10 9")

    (for {
      triangleService <- TriangleService.make[IO]()
      triangle <- triangleService.parseTriangle(data)
      result <- triangleService.minimalPath(triangle)
    } yield {
      assert((List(7,6,3,2), 18) == result)
    }).unsafeRunSync()
  }
}
