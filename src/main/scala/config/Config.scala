import struct.*
import util.Flow

abstract class Config:
  /**
    * Returns either the position corresponding to the given colour or a `Flow` information
    */
  def colorPosition(color: RGB): Either[Flow, (Int, Int)]
