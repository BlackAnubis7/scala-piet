package util

sealed trait Flow

object Flow:
  /** Represents an obstacle the interpreter can't move through */
  object BLOCK extends Flow

  /** Alias for `BLOCK` */
  val BLACK: Flow = BLOCK

  /** Represents a colourless area, through which the interpreter can move unobstructed */
  object FREE extends Flow

  /** Alias for `WHITE` */
  val WHITE: Flow = WHITE
