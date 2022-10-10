package struct

import scala.collection.mutable.{Buffer, ListBuffer}

/** @define flatOrder
  *   elements are ordered first by rows and then by columns (first row 0 column
  *   0, then row 0 column 1 etc.)
  */

/** Represents a 2D grid of data
  *
  * @param width
  *   width of the matrix
  * @param height
  *   height of the matrix
  */
class Matrix[A](
    val width: Int,
    val height: Int,
    startingElems: Iterable[Option[A]] = List()
):
  /** Number of elements in this matrix */
  val area: Int = width * height

  /** Flat list of this matrix elements.
    * @note
    *   $flatOrder
    */
  private val elems: Buffer[Option[A]] =
    val starting: List[Option[A]] = startingElems.take(area).toList
    val rest: Buffer[Option[A]] =
      if starting.size < area then ListBuffer.fill(area - starting.size)(None)
      else ListBuffer()
    starting ++: rest

  /** Changes 2D coordinates into flat 1D index. Used for accessing `elems`
    * elements
    *
    * @param x
    *   column index
    * @param y
    *   row index
    * @return
    *   flat list index
    * @note
    *   if `(x, y)` is a position outside this matrix, this method will return
    *   an index which is out of bounds of `elems`
    */
  private def flatCoords(x: Int, y: Int): Int = y * width + x

  /** Changes flat 1D index into 2D coordinates. Used for translating `elems`
    * indices into matrix element coordinates
    *
    * @param index
    *   index to be translated
    * @return
    *   tuple `(x, y)`, where `x` is column index and `y` is row index
    * @note
    *   if the `index` given is invalid (out of `elems` bounds), this method
    *   will return coordinates which are outside of this matrix
    */
  private def unflatCoords(index: Int): (Int, Int) =
    (index % width, index / width)

  /** Checks if given coordinates are valid (are not outside of the matrix)
    *
    * @param x
    *   column index
    * @param y
    *   row index
    * @return
    *   `true` if coorginates are valid, `false` otherwise
    */
  def validCoords(x: Int, y: Int): Boolean =
    0 <= x && x < this.width && 0 <= y && y < this.height

  /** Returns some matrix element at given position. Will return `None` if the
    * coordinates are outside this matrix
    *
    * @param x
    *   column index
    * @param y
    *   row index
    * @return
    *   element at given coordinates, or `None` if the element is undefined or
    *   the coordinates are outside the matrix
    * @see
    *   `validCoords`
    */
  def get(x: Int, y: Int): Option[A] =
    if validCoords(x, y) then elems(flatCoords(x, y)) else None

  def getRow(y: Int): List[Option[A]] =
    (0 until width)
      .map(get(_, y))
      .toList

  def getColumn(x: Int): List[Option[A]] =
    (0 until height)
      .map(get(x, _))
      .toList

  /** Returns some matrix element at given position. Will return given `default`
    * value if the chosen matrix element is undefined, or the coordinates are
    * outside of the matrix
    *
    * @param x
    *   column index
    * @param y
    *   row index
    * @param default
    *   fallback default value
    * @return
    *   element from this matrix, or given `default`
    */
  def getOrDefault(x: Int, y: Int, default: A): A =
    get(x, y) match
      case Some(a) => a
      case None    => default

  /** Alias for `get` */
  def apply(x: Int, y: Int): Option[A] = get(x, y)

  /** Updates an element of this matrix
    *
    * @param x
    *   column index
    * @param y
    *   row index
    * @param elem
    *   new value for chosen element
    */
  def update(x: Int, y: Int, elem: A): Unit =
    elems.update(flatCoords(x, y), Some(elem))

  /** Updates a row of elements at once
    *
    * @param y
    *   index of the row to be updated
    * @param newElems
    *   collection of new row elements
    * @note
    *   If `newElems` length is smaller than this matrix width, only a part of
    *   the row will be updated. If `newElems` length is larger than this matrix
    *   width, whole row will be updated, and some `newElems` elements will be
    *   unused
    *
    * @example
    *   {{{ val m: Matrix[Int] = Matrix(3, 3) m.updateRow(0, List(1, 2, 3)) //
    *   will put 1 2 3 into row 0 m.updateRow(1, List(1, 2)) // will put 1 2
    *   into first two columns of row 1 m.updateRow(2, List(1, 2, 3, 4)) // will
    *   put 1 2 3 into row 2 (number 4 will be unused) }}}
    */
  def updateRow(y: Int, newElems: Seq[A]): Unit =
    for x <- 0 until Math.min(this.width, newElems.length) do
      update(x, y, newElems(x))

  /** Removes an element from this matrix. Matrix size does not change, the
    * element at given coordinates is changed to undefined
    *
    * @param x
    *   column index
    * @param y
    *   row index
    * @return
    *   element removed from given coordinates
    */
  def pop(x: Int, y: Int): Option[A] =
    val elem: Option[A] = get(x, y)
    elems.update(flatCoords(x, y), None)
    elem

  /** Finds `needle` value in this matrix and returns its coordinates
    *
    * @tparam B
    *   the type of the `needle`
    * @param needle
    *   value to be found
    * @return
    *   some tuple containing coordinates, or `None` if not found
    */
  def coordsOf[B >: A](needle: B): Option[(Int, Int)] =
    val flatPos: Int = elems.indexOf(Some(needle))
    if flatPos >= 0 then Some(unflatCoords(flatPos)) else None

  def transformAll(f: A => A): Unit =
    elems.map {
      _ match
        case Some(a) => Some(f(a))
        case None    => None
    }

  def map[B](f: A => B): Matrix[B] =
    new Matrix(
      width,
      height,
      elems.map {
        _ match
          case None        => None
          case Some(value) => Some(f(value))
      }
    )

  /** Checks if all elements of this matrix are defined
    *
    * @return
    *   `true` if every element is defined, `false` otherwise
    */
  def isFull: Boolean = elems.forall(_.isDefined)

  /** Checks if all elements of this matrix are undefined
    *
    * @return
    *   `true` if every element is undefined, `false` otherwise
    */
  def isEmpty: Boolean = elems.forall(_.isEmpty)

  /** Generates all valid coordinates of this matrix
    *
    * @note
    *   $flatOrder
    * @return
    *   generated coordinates sequence
    */
  def coords: IndexedSeq[(Int, Int)] =
    for i <- 0 until this.area yield unflatCoords(i)

  def toString(elemSeparator: String, lineSeparator: String): String =
    (0 until height)
      .map(
        getRow(_)
          .map(_.getOrElse("None"))
          .mkString(elemSeparator)
      )
      .mkString(lineSeparator)

  override def toString(): String = this.toString(", ", "\n")

object Matrix
