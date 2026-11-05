package exercises

import scala.List

object ch05_PointsInsideCircles  extends App {
  case class Point(x: Int, y: Int)

  val points = List(Point(5, 2), Point(1, 1))
  val riskyRadiuses = List(-10, 0, 2)

  def isInside(point: Point, radius: Int): Boolean = {
    radius * radius >= point.x * point.x + point.y * point.y
  }

  {
    val result = for {
      radius <- riskyRadiuses
      point <- points.filter(p => isInside(p, radius))
    } yield s"$point within radius of $radius"

    println(result)
  }

  {
    val result = for {
      radius <- riskyRadiuses.filter(r => r > 0)
      point <- points.filter(p => isInside(p, radius))
    } yield s"$point within radius of $radius"

    println(result)
  }

  {
    val result = for {
      radius <- riskyRadiuses
      if radius > 0
      point <- points.filter(p => isInside(p, radius))
    } yield s"$point within radius of $radius"

    println(result)
  }

  {
    def insideFilter(point: Point, r: Int): List[Point] =
      if (isInside(point, r)) List(point) else List.empty


    def positiveRadius(radius: Int): List[Int] =
      if (radius > 0) List(radius) else List.empty

    val result = for {
      radius <- riskyRadiuses
      validRadius <- positiveRadius(radius)
      point <- points
      inPoints <- insideFilter(point, validRadius)
    } yield s"$point within radius of $radius"

    println(result)
  }

}
