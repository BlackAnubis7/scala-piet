import struct.*
import config.Config
import util.Flow
import scala.collection.mutable.ListBuffer

class CodelCompiler(val reader: ImageReader, val config: Config):
  val width: Int = reader.width
  val height: Int = reader.height
  val blockMap: Matrix[Int] = Matrix(width, height)

  /** Merges two [[struct.Compass]] instances into one, reaching farthest in
    * each direction.
    *
    * @example
    *   {{{ val a = Compass[Int](3, 18, 7, 11) val b = Compass[Int](1, 9, 8, 5)
    *   val c = mergeFar(a, b)
    *
    * // c == Compass[Int](1, 18, 8, 5) }}}
    * @param comp1
    *   first `Compass`
    * @param comp2
    *   second `Compass`
    * @return
    *   `Compass`, in which the reach in every direction is maximised
    */
  private val mergeFar =
    Compass.mergeWithComparator[Int, Int]((x: Int, y: Int) => x - y)

  /** Generates four sets of coordinates, to all four sides from given starting
    * position
    *
    * @param coords
    *   starting coordinates
    * @return
    *   `List` of four coordinates - Up, Right, Down and Left from the given
    *   `coords`
    */
  private def fourWays(coords: (Int, Int)): Seq[(Int, Int)] =
    val (x, y) = coords
    List((x + 1, y), (x - 1, y), (x, y + 1), (x, y - 1))

  /** Recursively finds codels belonging to one block, counts them and finds
    * farthest members to each direction
    *
    * @param coords
    *   coordinates of the codel processed currently
    * @param blockId
    *   ID of the block being created
    * @param blockColor
    *   colour used to decide on which codels to include in this block
    * @param prevCoordCompass
    *   `Compass` containing single previous codel coordinates
    * @return
    *   a tuple - (number of codels in the block, farthest coordinates found)
    */
  private def recBlockFlood(
      coords: (Int, Int),
      blockId: Int,
      blockColor: RGB,
      prevCoordCompass: Compass[Int]
  ): (Int, Compass[Int]) =
    val (x, y) = coords
    val currentCoordCompass: Compass[Int] = Compass.singleBlockCompass(x, y)
    if !this.blockMap.validCoords(x, y) || this.blockMap(x, y).isDefined then
      (0, prevCoordCompass)
    else
      val codel: RGB = reader.getPixel(x, y)
      val codelClassification: Either[Flow, (Int, Int)] =
        config.colorPosition(codel)
      codelClassification match
        case Left(flow) =>
          this.blockMap.update(x, y, flow.negId)
          (0, prevCoordCompass)
        case Right(colorPos) if config.oneBlockSafe(codel, blockColor) =>
          this.blockMap.update(x, y, blockId)
          fourWays(coords)
            .map(c =>
              recBlockFlood(c, blockId, blockColor, currentCoordCompass)
            )
            .foldLeft((1, currentCoordCompass))((a, b) =>
              (a._1 + b._1, mergeFar(a._2, b._2))
            )
        case _ => (0, prevCoordCompass)
  end recBlockFlood

  private def generateEdges(
      blockId: Int,
      farthest: Compass[Int]
  ): Compass[Edge] =
    val rowN: List[Option[Int]] = blockMap.getRow(farthest.n)
    val colE: List[Option[Int]] = blockMap.getColumn(farthest.e)
    val rowS: List[Option[Int]] = blockMap.getRow(farthest.s)
    val colW: List[Option[Int]] = blockMap.getColumn(farthest.w)
    Compass(
      Edge(
        farthest.n,
        rowN.indexOf(Some(blockId)),
        rowN.lastIndexOf(Some(blockId))
      ),
      Edge(
        farthest.e,
        colE.indexOf(Some(blockId)),
        colE.lastIndexOf(Some(blockId))
      ),
      Edge(
        farthest.s,
        rowS.lastIndexOf(Some(blockId)),
        rowS.indexOf(Some(blockId))
      ),
      Edge(
        farthest.w,
        colW.lastIndexOf(Some(blockId)),
        colW.indexOf(Some(blockId))
      )
    )

  def generateAllBlocks(): List[Block] =
    val blockList = new ListBuffer[Block]
    for x <- 0 until width do
      for y <- 0 until height do
        if !blockMap(x, y).isDefined then
          val blockId: Int = blockList.size + 1
          val codelColor: RGB = reader.getPixel(x, y)
          val floodResult: (Int, Compass[Int]) = recBlockFlood(
            (x, y),
            blockId,
            codelColor,
            Compass.singleBlockCompass(x, y)
          )
          blockList.addOne(
            Block(
              blockId,
              floodResult._1,
              config.colorPosition(codelColor),
              generateEdges(blockId, floodResult._2)
            )
          )
    blockList.toList

object CodelCompiler
