//> using lib "org.scalameta::munit::0.7.29"
import struct.RGB

class TestRGB extends munit.FunSuite:
  test("RGB equals") {
    val a = RGB(23, 54, 111)
    val b = RGB(23, 54, 111)
    val c = RGB(23, 55, 111)
    val d = (23,54, 111)
    assert(a == b)
    assert(a != c)
    assert(!a.equals(d))
  }

  test("RGB does not allow invalid values") {
    val a = RGB(23, 54, 111)
    val b = RGB(256 + 23, 256 + 54, 256 + 111)
    val c = RGB(23 - 256, 54 - 256, 111 - 256)
    assertEquals(b, a)
    assertEquals(c, a)
  }

  test("RGB from hex string") {
    val goal1: Option[RGB] = Some(RGB(172, 54, 111))
    val goal2: Option[RGB] = None
    val a = RGB.fromHex("ac366f")
    val b = RGB.fromHex("##$%a3rac366fy;")
    val c = RGB.fromHex("#AC366F")
    val d = RGB.fromHex("ac36f")
    assertEquals(goal1, a)
    assertEquals(goal1, b)
    assertEquals(goal1, c)
    assertEquals(goal2, d)
  }
end TestRGB

