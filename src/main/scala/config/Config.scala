package config

import struct.*
import util.Flow

abstract class Config:
  /**
    * Decides if codels in given colours should be included in one block
    *
    * @param a first colour
    * @param b second colour
    * @return `true` if neighbouring codels in given two colours should be in one
    * codel block, `false` if they should be separated
    * @note Neighbouring codels of same colour will always be in one block, and codels
    * with different `colorPosition` will always be separated. This behaviour is necessary
    * for correct program flow, and so is non-overridable. In other cases, `oneBlock` method
    * will be used
    * @note this method should not be used with special flow colours (blocking and free-flow,
    * in original Piet black and white respectively) - method behaviour in this case is undefined
    */
  final def oneBlockSafe(a: RGB, b: RGB): Boolean =
    a == b ||
    colorPosition(a) == colorPosition(b) && oneBlock(a, b)

  /**
    * Returns either the position corresponding to the given colour or a [[util.Flow]] information
    */
  def colorPosition(color: RGB): Either[Flow, (Int, Int)]

  /**
    * Decides if codels in given colours should be included in one block when the colours are different,
    * but have similar `colorPosition`
    *
    * @param a first colour
    * @param b second colour
    * @return `true` if neighbouring codels in given two colours should be in one
    * codel block, `false` if they should be separated
    * @note [[CodelCompiler]] uses this method only through non-overridable `oneBlockSafe` method
    * @note [[CodelCompiler]] does not use this method with special flow colours (blocking and free-flow,
    * in original Piet black and white respectively)
    */
  def oneBlock(a: RGB, b: RGB): Boolean
