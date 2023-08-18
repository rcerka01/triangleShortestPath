import cats.effect.Sync
import cats.implicits._

trait TriangleService[F[_]] {
  def parseTriangle(lines: List[String]): F[List[List[Int]]]
  def minimalPath(triangle: List[List[Int]]): F[(List[Int], Int)]
}

class TriangleServiceImpl[F[_]: Sync] extends TriangleService[F] {

  override def parseTriangle(lines: List[String]): F[List[List[Int]]] = {
    lines
      .map { line =>
        line
          .split(" ")
          .toList
          .map(_.toInt)
      }
      .pure[F]
  }

  override def minimalPath(triangle: List[List[Int]]): F[(List[Int], Int)] = {
    val pathWithSum =
      triangle
        .foldRight(List((List[Int](), 0): (List[Int], Int))) {
          (currentRow, accumulated) =>
            currentRow.indices.toList.map { index =>
              val leftOption = accumulated.lift(index)
              val rightOption = accumulated.lift(index + 1)

              (leftOption, rightOption) match {
                case (Some((leftPath, leftSum)), Some((_, rightSum)))
                    if leftSum <= rightSum =>
                  (currentRow(index) :: leftPath, currentRow(index) + leftSum)
                case (Some((_, leftSum)), Some((rightPath, rightSum)))
                    if leftSum > rightSum =>
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
  }.pure[F]
}

object TriangleService {
  def make[F[_]: Sync](): F[TriangleService[F]] =
    Sync[F].pure(new TriangleServiceImpl)
}
