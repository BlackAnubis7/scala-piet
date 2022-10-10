package struct

import scala.util.matching.Regex

/** Represents an RGB colour
  *
  * @param r
  *   red - `Int` from 0 to 255
  * @param g
  *   green - `Int` from 0 to 255
  * @param b
  *   blue - `Int` from 0 to 255
  */
final class RGB(r: Int, g: Int, b: Int):
  private val mod: Int => Int =
    x => if x < 0 then 256 + x % 256 else x % 256
  val red: Int = mod(r)
  val green: Int = mod(g)
  val blue: Int = mod(b)
  override def toString(): String = s"RGB($red, $green, $blue)"
  override def equals(x: Any): Boolean =
    x match
      case rgb: RGB =>
        this.red == rgb.red &&
        this.green == rgb.green &&
        this.blue == rgb.blue
      case _ => false

object RGB:
  def fromHex(hex: String): Option[RGB] =
    val reghex: Regex = "[0-9a-fA-F]{6}".r
    reghex.findFirstIn(hex) match
      case Some(hex6) =>
        Some(
          RGB(
            Integer.parseInt(hex6.slice(0, 2), 16),
            Integer.parseInt(hex6.slice(2, 4), 16),
            Integer.parseInt(hex6.slice(4, 6), 16)
          )
        )
      case None => None
