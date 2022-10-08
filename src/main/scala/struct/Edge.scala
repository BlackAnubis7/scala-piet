package struct

given Conversion[(Int, Int, Int), Edge] with
  def apply(sides: (Int, Int, Int)): Edge =
    Edge(front = sides._1, left = sides._2, right = sides._3)

/** Represents an edge of a block - one coordinate and farthest codels on both
  * sides. Left and right are relative to the direction where the interpreter is
  * ''looking''
  *
  * @param front
  *   position of the edge (i.e. if this is the northern block edge, front is
  *   this edge's position on the North-South axis)
  * @param left
  *   codel farthest to the left
  * @param right
  *   codel farthest to the right
  */
final case class Edge(front: Int, left: Int, right: Int)
