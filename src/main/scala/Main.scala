import cats.effect._
import scala.io.StdIn

object Main extends IOApp.Simple {

  private def readMultiLines(): IO[List[String]] = {
    def loop(acc: List[String]): IO[List[String]] = {
      IO(StdIn.readLine()).flatMap { line =>
        if (line.isEmpty) IO.pure(acc.reverse)
        else loop(line :: acc)
      }
    }
    loop(Nil)
  }

  override def run: IO[Unit] = {
    for {
      triangleService <- TriangleService.make[IO]()
      lines           <- readMultiLines()
      triangle        <- triangleService.parseTriangle(lines)
      result          <- triangleService.minimalPath(triangle)
      _ <- IO(println(result._1.mkString(" + ") + " = " + result._2))
    } yield ()
  }.handleErrorWith(error => IO(println(s"Error: ${error.getMessage}")))
}
