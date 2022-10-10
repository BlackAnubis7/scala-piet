package util

sealed trait Flow:
  /** Common `blockId` of the codels representing this `Flow`. Has to be unique
    * and negative, to avoid collision with IDs of existing codel blocks.
    */
  val negId: Int

object Flow:
  /** Represents an obstacle the interpreter can't move through */
  object BLOCK extends Flow:
    override val negId: Int = -2

  /** Alias for `BLOCK` */
  val BLACK: Flow = BLOCK

  /** Represents a colourless area, through which the interpreter can move
    * unobstructed
    */
  object FREE extends Flow:
    override val negId: Int = -1

  /** Alias for `WHITE` */
  val WHITE: Flow = WHITE
