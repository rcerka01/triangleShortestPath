import cats.effect._
import fs2._

import scala.io.StdIn

object TriangleMinPath {

  def parseTriangle(lines: List[String]): Either[Throwable, List[List[Int]]] = {
    val parsedLines = lines.map { line =>
      line
        .split(" ")
        .toList
        .map(_.toInt)
    }
    Right(parsedLines)
  }

  def minimalPath(triangle: List[List[Int]]): (List[Int], Int) = {
    val pathWithSum =
      triangle
        .foldRight(List((List[Int](), 0): (List[Int], Int))) {
          (currentRow, accumulated) =>
            currentRow.indices.toList.map { index =>

              val leftOption = accumulated.lift(index)
              val rightOption = accumulated.lift(index + 1)

              (leftOption, rightOption) match {
                case (Some((leftPath, leftSum)), Some((_, rightSum))) if leftSum <= rightSum =>
                  (currentRow(index) :: leftPath, currentRow(index) + leftSum)
                case (Some((_, leftSum)), Some((rightPath, rightSum))) if leftSum > rightSum =>
                  (currentRow(index) :: rightPath, currentRow(index) + rightSum)

                case (None, Some((rightPath, rightSum))) =>
                  (currentRow(index) :: rightPath, currentRow(index) + rightSum)
                case (Some((leftPath, leftSum)), None) =>
                  (currentRow(index) :: leftPath, currentRow(index) + leftSum)

                case _ => (List(currentRow(index)), currentRow(index))
              }
            }
        }
    pathWithSum.minBy(_._2)
  }

}
