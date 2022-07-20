//> using lib "org.scalameta::munit::0.7.29"
import cases.RGB

class TestImageReader extends munit.FunSuite {
  test("Read pixels from PNG") {
    val reader = new ImageReader("src/test/resources/simple_matrix.png")
    for (x <- (0 until 5); y <- (0 until 5)) do
      assertEquals(reader.getPixel(x * 2, y * 2), correctRGB(y)(x))
  }

  test("Read pixels from BMP") {
    val reader = new ImageReader("src/test/resources/simple_matrix.bmp")
    for (x <- (0 until 5); y <- (0 until 5)) do
      assertEquals(reader.getPixel(x * 2, y * 2), correctRGB(y)(x))
  }

  test("Read pixels from GIF") {
    val reader = new ImageReader("src/test/resources/simple_matrix.gif")
    for (x <- (0 until 5); y <- (0 until 5)) do
      assertEquals(reader.getPixel(x * 2, y * 2), correctRGB(y)(x))
  }

  val correctRGB: Seq[Seq[RGB]] = List(
    List(
      RGB(0x00, 0x00, 0x00),
      RGB(0x44, 0x44, 0x44),
      RGB(0x88, 0x88, 0x88),
      RGB(0xcc, 0xcc, 0xcc),
      RGB(0xff, 0xff, 0xff)
    ),
    List(
      RGB(0x55, 0x00, 0x00),
      RGB(0xaa, 0x00, 0x00),
      RGB(0xff, 0x00, 0x00),
      RGB(0xff, 0x55, 0x55),
      RGB(0xff, 0xaa, 0xaa)
    ),
    List(
      RGB(0x00, 0x55, 0x00),
      RGB(0x00, 0xaa, 0x00),
      RGB(0x00, 0xff, 0x00),
      RGB(0x55, 0xff, 0x55),
      RGB(0xaa, 0xff, 0xaa)
    ),
    List(
      RGB(0x00, 0x00, 0x55),
      RGB(0x00, 0x00, 0xaa),
      RGB(0x00, 0x00, 0xff),
      RGB(0x55, 0x55, 0xff),
      RGB(0xaa, 0xaa, 0xff)
    ),
    List(
      RGB(0xff, 0xff, 0x00),
      RGB(0xff, 0x00, 0xff),
      RGB(0x00, 0xff, 0xff),
      RGB(0xff, 0xaa, 0x00),
      RGB(0x4a, 0x00, 0x7e)
    )
  )
}
