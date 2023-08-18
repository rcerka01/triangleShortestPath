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
      assert((List(7, 6, 3, 2), 18) == result)
    }).unsafeRunSync()
  }

  test("Test minimal path for advance example") {
    val data = List("1", "1 2", "2 1 3", "2 2 1 2", "2 2 2 1 2")
    (for {
      triangleService <- TriangleService.make[IO]()
      triangle <- triangleService.parseTriangle(data)
      result <- triangleService.minimalPath(triangle)
    } yield {
      assert((List(1, 1, 1, 1, 1), 5) == result)
    }).unsafeRunSync()
  }

  test("Test minimal path with single node") {
    val data = List("1")
    (for {
      triangleService <- TriangleService.make[IO]()
      triangle <- triangleService.parseTriangle(data)
      result <- triangleService.minimalPath(triangle)
    } yield {
      assert((List(1), 1) == result)
    }).unsafeRunSync()
  }

  test("Test minimal path with no node") {
    val data = Nil
    (for {
      triangleService <- TriangleService.make[IO]()
      triangle <- triangleService.parseTriangle(data)
      result <- triangleService.minimalPath(triangle)
    } yield {
      assert((List(), 0) == result)
    }).unsafeRunSync()
  }

  test("Test minimal path with no core") {
    val data = List("6 3", "3 8 5", "11 2 10 9")

    (for {
      triangleService <- TriangleService.make[IO]()
      triangle <- triangleService.parseTriangle(data)
      result <- triangleService.minimalPath(triangle)
    } yield {
      assert((List(6, 3, 2), 11) == result)
    }).unsafeRunSync()
  }
}
