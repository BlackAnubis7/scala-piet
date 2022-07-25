package config

import struct.*
import util.Flow

class DefaultConfig extends Config:
  /** Original Piet colour table */
  private val grid: Matrix[RGB] = initColorGrid()

  /**
    * Returns the original Piet colour table, formed as a 6x3 [[struct.Matrix]]
    */
  private def initColorGrid(): Matrix[RGB] =
    val matrix: Matrix[RGB] = new Matrix(6, 3)
    val rows: Seq[Seq[String]] = List(
      List("ffc0c0", "ffffc0", "c0ffc0", "c0ffff", "c0c0ff", "ffc0ff"),
      List("ff0000", "ffff00", "00ff00", "00ffff", "0000ff", "ff00ff"),
      List("c00000", "c0c000", "00c000", "00c0c0", "0000c0", "c000c0")
    )
    for y <- 0 until 3 do
      matrix.updateRow(y, rows(y).map(RGB.fromHex(_).get))
    matrix

  /**
    * Returns colour's corresponding position on the original Piet's colour table.
    * Colours that are absent in the table are treated like white, thus allowing the
    * interpreter to move freely
    *
    * @param color colour to be identified
    * @return either colour position or [[util.Flow.FREE]]
    */
  override def colorPosition(color: RGB): Either[Flow, (Int, Int)] =
    grid.coordsOf(color) match
      case Some(coords) => Right(coords)
      case None         => Left(Flow.FREE)

  /**
    * @inheritdoc
    */
  override def oneBlock(a: RGB, b: RGB): Boolean = false
