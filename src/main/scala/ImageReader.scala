import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

case class RGB(r: Int, g: Int, b: Int)

extension (img: BufferedImage)
    def getPixel(x: Int, y: Int): RGB = 
        val argb: Int = img.getRGB(x, y)
        RGB (
            (argb >> 16) & 0xff,
            (argb >> 8)  & 0xff,
            (argb)       & 0xff
        )

object ImageReader:
    def main(args: Array[String]): Unit =
        val file: File = new File("src/main/resources/artistic-hello-world.gif")
        val image: BufferedImage = ImageIO.read(file)

        println(image.getPixel(3, 2))
end ImageReader