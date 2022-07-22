package struct

import scala.collection.mutable.{Buffer, ListBuffer}

/**
  * Represents a 2D grid of data
  *
  * @param width width of the matrix
  * @param height height of the matrix
  */
class Matrix[A](val width: Int, val height: Int):
  /** Number of elements in this matrix */
  val area: Int = width * height

  /** Flat list of this matrix elements */
  private val elems: Buffer[Option[A]] = ListBuffer.fill(this.area)(None)
  
  /**
    * Changes 2D coordinates into flat 1D index. Used for accessing `elems` elements
    *
    * @param x column index
    * @param y row index
    * @return flat list index
    */
  private def flatCoords(x: Int, y: Int): Int = y * width + x

  /**
    * Checks if given coordinates are valid (are not outside of the matrix)
    *
    * @param x column index
    * @param y row index
    * @return `true` if coorginates are valid, `false` otherwise
    */
  def validCoords(x: Int, y: Int): Boolean =
    0 <= x && x < this.width && 0 <= y && y < this.height
  
  /**
    * Returns matrix element at given position
    *
    * @param x column index
    * @param y row index
    * @return element at given coordinates
    */
  def get(x: Int, y: Int): Option[A] = elems(flatCoords(x, y))

  /** Alias for `get` */
  def apply(x: Int, y: Int): Option[A] = get(x, y)

  /**
    * Updates an element of this matrix
    *
    * @param x column index
    * @param y row index
    * @param elem new value for chosen element
    */
  def update(x: Int, y: Int, elem: A): Unit =
    elems.update(flatCoords(x, y), Some(elem))

  /**
    * Removes an element from this matrix. Matrix size does not change,
    * the element at given coordinates is changed to undefined
    *
    * @param x column index
    * @param y row index
    * @return element removed from given coordinates
    */
  def pop(x: Int, y: Int): Option[A] =
    val elem: Option[A] = get(x, y)
    elems.update(flatCoords(x, y), None)
    elem
  
  /**
    * Checks if all elements of this matrix are defined
    *
    * @return `true` if every element is defined, `false` otherwise
    */
  def isFull: Boolean = elems.forall(_.isDefined)

  /**
    * Checks if all elements of this matrix are undefined
    *
    * @return `true` if every element is undefined, `false` otherwise
    */
  def isEmpty: Boolean = elems.forall(_.isEmpty)
