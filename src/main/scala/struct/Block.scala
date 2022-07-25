package struct

import util.Flow

/**
  * Represents a single codel block
  *
  * @param id unique block ID
  * @param size number of codels in this block
  * @param colorType either [[util.Flow]] or tuple containing position on colour grid
  * @param edges information about farthest edges in main four directions
  */
case class Block(
  id: Int,
  size: Int,
  colorType: Either[Flow, (Int, Int)],
  edges: Compass[Sides]
)

object Block:
  /**
    * Alternative, easier-to-use constructor for `Block`
    *
    * @param id unique block ID
    * @param size number of codels in this block
    * @param colorType either [[util.Flow]] or tuple containing position on colour grid
    * @param northEdge block's edge which is farthest up (north) 
    * @param eastEdge block's edge which is farthest right (east)
    * @param southEdge block's edge which is farthest down (south)
    * @param westEdge block's edge which is farthest left (west)
    * 
    * @note `....Edge` values might be given as `(left: Int, right: Int)` tuples
    * instead of [[package.Sides]] case class instance
    */
  def apply(
    id: Int,
    size: Int,
    colorType: Either[Flow, (Int, Int)],
    northEdge: Sides,
    eastEdge: Sides,
    southEdge: Sides,
    westEdge: Sides
  ): Unit =
    Block(id, size, colorType, Compass(northEdge, eastEdge, southEdge, westEdge))
