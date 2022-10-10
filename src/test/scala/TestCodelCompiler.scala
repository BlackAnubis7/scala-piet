//> using lib "org.scalameta::munit::0.7.29"
import config.DefaultConfig
import struct.Compass
import struct.Edge

class TestCodelCompiler extends munit.FunSuite:
  test("Analyse complex codel block") {
    val reader = ImageReader("src/test/resources/hello-world.png")
    val compiler = CodelCompiler(reader, new DefaultConfig)
    val blocks = compiler.generateAllBlocks()
    val firstBlock = blocks.head

    assertEquals(firstBlock.size, 72)
    assertEquals(
      firstBlock.edges,
      Compass(Edge(0, 0, 10), Edge(10, 0, 0), Edge(10, 9, 5), Edge(0, 9, 0))
    )
  }
