import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

import struct.RGB

extension (img: BufferedImage)
  def getPixel(x: Int, y: Int): RGB =
    val argb: Int = img.getRGB(x, y)
    RGB(
      (argb >> 16) & 0xff,
      (argb >> 8) & 0xff,
      (argb) & 0xff
    )

class ImageReader(filePath: String):
  private val file: File = new File(filePath)
  private val image: BufferedImage = ImageIO.read(file)

  def getPixel(x: Int, y: Int): RGB = image.getPixel(x, y)
end ImageReader
