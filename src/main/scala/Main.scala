import cats.effect._
import fs2._
import TriangleMinPath._

import scala.io.StdIn

object Main extends IOApp{

  def readMultiLines(): List[String] = {
    val line = StdIn.readLine()
    if (line.isEmpty) Nil
    else line :: readMultiLines()
  }

  override def run(args: List[String]): IO[ExitCode] = {
    Stream
      .eval(IO(readMultiLines()))
      .flatMap(lines => Stream.emits(lines))
      .compile
      .toList
      .flatMap { lines =>
        parseTriangle(lines) match {
          case Right(triangle) =>
            val (path, sum) = minimalPath(triangle)
            IO(println(path.mkString(" + ") + " = " + sum))

          case Left(e) =>
            IO(println("Error parsing triangle: " + e.getMessage))
        }
      }
      .as(ExitCode.Success)
  }

}
