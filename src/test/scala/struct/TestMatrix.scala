//> using lib "org.scalameta::munit::0.7.29"
import struct.Matrix

class TestMatrix extends munit.FunSuite:
  test("Create empty matrix") {
    val matrix: Matrix[Int] = Matrix(20, 10)
    assertEquals(matrix.area, 10 * 20)
    assertEquals(matrix.isEmpty, true)
    assertEquals(matrix.isFull, false)
  }

  test("Get and set matrix element") {
    val matrix: Matrix[Int] = Matrix(20, 10)
    matrix.update(6, 8, 43153)
    assertEquals(matrix.get(6, 8), Some(43153))
    assertEquals(matrix(6, 8), Some(43153))
    assertEquals(matrix(8, 6), None)
    intercept[java.lang.IndexOutOfBoundsException]{matrix(20, 10)}
    intercept[java.lang.IndexOutOfBoundsException]{matrix.update(20, 10, 64367)}
    intercept[java.lang.IndexOutOfBoundsException]{matrix(-1, -1)}
    intercept[java.lang.IndexOutOfBoundsException]{matrix.update(-1, -1, 64367)}
  }

  test("Pop from matrix") {
    val matrix: Matrix[Int] = Matrix(20, 10)
    matrix.update(6, 8, 43153)
    assertEquals(matrix.pop(6, 8), Some(43153))
    assertEquals(matrix(6, 8), None)
    assertEquals(matrix.pop(6, 8), None)
  }
end TestMatrix

