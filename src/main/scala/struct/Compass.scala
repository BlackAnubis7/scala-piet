package struct

/** Represents four directions on a grid (where north = up, east = right etc.)
  *
  * @tparam A
  *   type of the elements this Compass will hold
  * @param n
  *   North
  * @param e
  *   East
  * @param s
  *   South
  * @param w
  *   West
  */
case class Compass[+A](n: A, e: A, s: A, w: A)

object Compass:
  def mergeWithComparator[A, B](
      comparator: (A, B) => Int
  )(a: Compass[A], b: Compass[B]): Compass[A | B] =
    Compass[A | B](
      if comparator(a.n, b.n) < 0 then a.n else b.n,
      if comparator(a.e, b.e) > 0 then a.e else b.e,
      if comparator(a.s, b.s) > 0 then a.s else b.s,
      if comparator(a.w, b.w) < 0 then a.w else b.w
    )

  def singleBlockCompass[A](x: A, y: A): Compass[A] =
    Compass(y, x, y, x)
