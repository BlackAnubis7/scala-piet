package struct

given Conversion[(Int, Int), Sides] with
  def apply(sides: (Int, Int)): Sides = 
    Sides(left = sides._1, right = sides._2)

/**
  * Represents farthest codels on both sides. Left and right are
  * relative to the direction where the interpreter is ''looking''
  *
  * @param left codel farthest to the left
  * @param right codel farthest to the right
  */
final case class Sides(left: Int, right: Int)
