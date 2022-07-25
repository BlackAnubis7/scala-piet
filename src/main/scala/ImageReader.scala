import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

import struct.RGB

/**
  * Represents an existing image file and allows reading its pixel colours
  *
  * @param filePath path of the image file to be read
  */
class ImageReader(filePath: String):
  private val file: File = new File(filePath)
  private val image: BufferedImage = ImageIO.read(file)

  def height: Int = image.getHeight()
  def width: Int = image.getWidth()

  /**
    * Returns [[struct.RGB]] representation of a pixel on given coordinates
    *
    * @param x pixel column index
    * @param y pixel row index
    * @return chosen pixel in RGB form
    */
  def getPixel(x: Int, y: Int): RGB =
    val argb: Int = image.getRGB(x, y)
    RGB(
      (argb >> 16) & 0xff,
      (argb >> 8) & 0xff,
      (argb) & 0xff
    )
end ImageReader
