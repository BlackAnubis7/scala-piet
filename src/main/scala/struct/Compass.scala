package struct

/**
  * Represents four directions on a grid (wherenorth = up, east = right etc.)
  *
  * @tparam A type of the elements this Compass will hold
  * @param n North
  * @param e East
  * @param s South
  * @param w West
  */
case class Compass[A](n: A, e: A, s: A, w: A)