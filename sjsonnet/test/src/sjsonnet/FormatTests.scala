package sjsonnet

import utest._

object FormatTests extends TestSuite{
  def check(fmt: String, jsonStr: String, expected: String) = {
    val json = ujson.read(jsonStr)
    val formatted = Format.format(fmt, Materializer.reverse(json), ammonite.ops.pwd / "(unknown)", ammonite.ops.pwd , -1, Map(), ammonite.ops.pwd)
    assert(formatted == expected)

  }
  def tests = Tests{
    // #
    check("No format chars\n", """[]""", "No format chars\n")
    check("", """[]""", "")
    check("%#%", """[]""", "%")
    check("%# +05.3%", """[]""", "    %")
    check("%# -+05.3%", """[]""", "%    ")
    check("%##--#      +05.3%", """[]""", "%    ")

    // %
    check("%%", """[]""", "%")
    check("%%%%", """[]""", "%%")
    check("%s%%", """["foo"]""", "foo%")
    check("%%%s", """["foo"]""", "%foo")

    // s
    check("%s", """["test"]""", "test")
    check("%s", """[true]""", "true")
    check("%5s", """["test"]""", " test")

    // c
    check("%c", """["a"]""", "a")
    check("%# +05.3c", """["a"]""", "    a")
//    check("%c", """[std.codepoint("a")]""", "a")

    // d (also a quick test of i and u)
    check("thing-%d", """[10]""", "thing-10")
//    check("thing-%#ld", """[10]""", "thing-10")
    check("thing-%d", """[-10]""", "thing--10")
    check("thing-%4d", """[10]""", "thing-  10")
    check("thing-%04d", """[10]""", "thing-0010")
    check("thing-% d", """[10]""", "thing- 10")
    check("thing-%- 4d", """[10]""", "thing- 10 ")
    check("thing-% d", """[-10]""", "thing--10")
    check("thing-%5.3d", """[10.3]""", "thing-  010")
    check("thing-%+5.3d", """[10.3]""", "thing- +010")
    check("thing-%+-5.3d", """[10.3]""", "thing-+010 ")
    check("thing-%-5.3d", """[10.3]""", "thing-010  ")
    check("thing-%#-5.3d", """[10.3]""", "thing-010  ")
    check("thing-%#-5.3i", """[10.3]""", "thing-010  ")
    check("thing-%#-5.3u", """[10.3]""", "thing-010  ")

    // o
    check("thing-%o", """[10]""", "thing-12")
    check("thing-%lo", """[10]""", "thing-12")
    check("thing-%o", """[-10]""", "thing--12")
    check("thing-%4o", """[10]""", "thing-  12")
    check("thing-%04o", """[10]""", "thing-0012")
    check("thing-% o", """[10]""", "thing- 12")
    check("thing-%- 4o", """[10]""", "thing- 12 ")
    check("thing-% o", """[-10]""", "thing--12")
    check("thing-%5.3o", """[10.3]""", "thing-  012")
    check("thing-%+5.3o", """[10.3]""", "thing- +012")
    check("thing-%+-5.3o", """[10.3]""", "thing-+012 ")
    check("thing-%-5.3o", """[10.3]""", "thing-012  ")
    check("thing-%#o", """[10]""", "thing-012")
    check("thing-%#lo", """[10]""", "thing-012")
    check("thing-%#o", """[-10]""", "thing--012")
    check("thing-%#4o", """[10]""", "thing- 012")
    check("thing-%#04o", """[10]""", "thing-0012")
    check("thing-%# o", """[10]""", "thing- 012")
    check("thing-%#- 4o", """[10]""", "thing- 012")
    check("thing-%# o", """[-10]""", "thing--012")
    check("thing-%#5.3o", """[10.3]""", "thing-  012")
    check("thing-%#+5.3o", """[10.3]""", "thing- +012")
    check("thing-%#+-5.3o", """[10.3]""", "thing-+012 ")
    check("thing-%#-5.3o", """[10.3]""", "thing-012  ")

    // x
    check("thing-%x", """[910]""", "thing-38e")
    check("thing-%lx", """[910]""", "thing-38e")
    check("thing-%x", """[-910]""", "thing--38e")
    check("thing-%5x", """[910]""", "thing-  38e")
    check("thing-%05x", """[910]""", "thing-0038e")
    check("thing-% x", """[910]""", "thing- 38e")
    check("thing-%- 5x", """[910]""", "thing- 38e ")
    check("thing-% x", """[-910]""", "thing--38e")
    check("thing-%6.4x", """[910.3]""", "thing-  038e")
    check("thing-%+6.4x", """[910.3]""", "thing- +038e")
    check("thing-%+-6.4x", """[910.3]""", "thing-+038e ")
    check("thing-%-6.4x", """[910.3]""", "thing-038e  ")
    check("thing-%#x", """[910]""", "thing-0x38e")
    check("thing-%#lx", """[910]""", "thing-0x38e")
    check("thing-%#x", """[-910]""", "thing--0x38e")
    check("thing-%#7x", """[910]""", "thing-  0x38e")
    check("thing-%#07x", """[910]""", "thing-0x0038e")
    check("thing-%# x", """[910]""", "thing- 0x38e")
    check("thing-%#- 7x", """[910]""", "thing- 0x38e ")
    check("thing-%# x", """[-910]""", "thing--0x38e")
    check("thing-%#8.4x", """[910.3]""", "thing-  0x038e")
    check("thing-%#+8.4x", """[910.3]""", "thing- +0x038e")
    check("thing-%#+-8.4x", """[910.3]""", "thing-+0x038e ")
    check("thing-%#-8.4x", """[910.3]""", "thing-0x038e  ")

    // X
    check("thing-%X", """[910]""", "thing-38E")
    check("thing-%lX", """[910]""", "thing-38E")
    check("thing-%X", """[-910]""", "thing--38E")
    check("thing-%5X", """[910]""", "thing-  38E")
    check("thing-%05X", """[910]""", "thing-0038E")
    check("thing-% X", """[910]""", "thing- 38E")
    check("thing-%- 5X", """[910]""", "thing- 38E ")
    check("thing-% X", """[-910]""", "thing--38E")
    check("thing-%6.4X", """[910.3]""", "thing-  038E")
    check("thing-%+6.4X", """[910.3]""", "thing- +038E")
    check("thing-%+-6.4X", """[910.3]""", "thing-+038E ")
    check("thing-%-6.4X", """[910.3]""", "thing-038E  ")
    check("thing-%#X", """[910]""", "thing-0X38E")
    check("thing-%#lX", """[910]""", "thing-0X38E")
    check("thing-%#X", """[-910]""", "thing--0X38E")
    check("thing-%#7X", """[910]""", "thing-  0X38E")
    check("thing-%#07X", """[910]""", "thing-0X0038E")
    check("thing-%# X", """[910]""", "thing- 0X38E")
    check("thing-%#- 7X", """[910]""", "thing- 0X38E ")
    check("thing-%# X", """[-910]""", "thing--0X38E")
    check("thing-%#8.4X", """[910.3]""", "thing-  0X038E")
    check("thing-%#+8.4X", """[910.3]""", "thing- +0X038E")
    check("thing-%#+-8.4X", """[910.3]""", "thing-+0X038E ")
    check("thing-%#-8.4X", """[910.3]""", "thing-0X038E  ")

    // e
    check("%e", """[910]""", "9.100000e+02")
    check("%.0le", """[910]""", "9e+02")
    check("%#e", """[-910]""", "-9.100000e+02")
    check("%16e", """[910]""", "    9.100000e+02")
    check("%016e", """[910]""", "00009.100000e+02")
    check("%016e", """[-910]""", "-0009.100000e+02")
    check("% e", """[910]""", " 9.100000e+02")
    check("%- 16e", """[910]""", " 9.100000e+02   ")
    check("% e", """[-910]""", "-9.100000e+02")
    check("%16.4e", """[910.3]""", "      9.1030e+02")
    check("%+16.4e", """[910.3]""", "     +9.1030e+02")
    check("%+-16.4e", """[910.3]""", "+9.1030e+02     ")
    check("%-16.4e", """[910.3]""", "9.1030e+02      ")
    check("%#.0e", """[910.3]""", "9.e+02")
    check("%#.0e", """[900]""", "9.e+02")
    check("%.3e", """[1000000001]""", "1.000e+09")

    // E
    check("%E", """[910]""", "9.100000E+02")
    check("%.0lE", """[910]""", "9E+02")
    check("%#E", """[-910]""", "-9.100000E+02")
    check("%16E", """[910]""", "    9.100000E+02")
    check("%016E", """[910]""", "00009.100000E+02")
    check("% E", """[910]""", " 9.100000E+02")
    check("%- 16E", """[910]""", " 9.100000E+02   ")
    check("% E", """[-910]""", "-9.100000E+02")
    check("%16.4E", """[910.3]""", "      9.1030E+02")
    check("%+16.4E", """[910.3]""", "     +9.1030E+02")
    check("%+-16.4E", """[910.3]""", "+9.1030E+02     ")
    check("%-16.4E", """[910.3]""", "9.1030E+02      ")
    check("%#.0E", """[910.3]""", "9.E+02")
    check("%#.0E", """[900]""", "9.E+02")
    check("%.3E", """[1000000001]""", "1.000E+09")

    // f
    check("%f", """[910]""", "910.000000")
    check("%f", """0""", "0.000000")
    check("%.0lf", """[910]""", "910")
    check("%#f", """[-910]""", "-910.000000")
    check("%12f", """[910]""", "  910.000000")
    check("%012f", """[910]""", "00910.000000")
    check("%012f", """[-910]""", "-0910.000000")
    check("% f", """[910]""", " 910.000000")
    check("%- 12f", """[910]""", " 910.000000 ")
    check("% f", """[-910]""", "-910.000000")
    check("%12.4f", """[910.3]""", "    910.3000")
    check("%+12.4f", """[910.3]""", "   +910.3000")
    check("%+-12.4f", """[910.3]""", "+910.3000   ")
    check("%-12.4f", """[910.3]""", "910.3000    ")
    check("%#.0f", """[910.3]""", "910.")
    check("%#.0f", """[910]""", "910.")
    check("%.3f", """[1000000001]""", "1000000001.000")

    // g
    check("%#.3g", """[1000000001]""", "1.00e+09")
    check("%#.3g", """[1100]""", "1.10e+03")
    check("%#.3g", """[1.1]""", "1.10")
    check("%#.5g", """[1000000001]""", "1.0000e+09")
    check("%#.5g", """[1100]""", "1100.0")
    check("%#.5g", """[110]""", "110.00")
    check("%#.5g", """[1.1]""", "1.1000")
    check("%#10.3g", """[1000000001]""", "  1.00e+09")
    check("%#10.3g", """[1100]""", "  1.10e+03")
    check("%#10.3g", """[1.1]""", "      1.10")
    check("%#10.5g", """[1000000001]""", "1.0000e+09")
    check("%#10.5g", """[1100]""", "    1100.0")
    check("%#10.5g", """[110]""", "    110.00")
    check("%#10.5g", """[1.1]""", "    1.1000")
    check("%.3g", """[1000000001]""", "1e+09")
    check("%.3g", """[1100]""", "1.1e+03")
    check("%.3g", """[1.1]""", "1.1")
    check("%.5g", """[1000000001]""", "1e+09")
    check("%.5g", """[1100]""", "1100")
    check("%.5g", """[110]""", "110")
    check("%.5g", """[1.1]""", "1.1")
    check("%10.3g", """[1000000001]""", "     1e+09")
    check("%10.3g", """[1100]""", "   1.1e+03")
    check("%10.3g", """[1.1]""", "       1.1")
    check("%10.5g", """[1000000001]""", "     1e+09")
    check("%10.5g", """[1100]""", "      1100")
    check("%10.5g", """[110]""", "       110")
    check("%10.5g", """[1.1]""", "       1.1")

    // G
    check("%#.3G", """[1000000001]""", "1.00E+09")
    check("%#.3G", """[1100]""", "1.10E+03")
    check("%#.3G", """[1.1]""", "1.10")
    check("%#.5G", """[1000000001]""", "1.0000E+09")
    check("%#.5G", """[1100]""", "1100.0")
    check("%#.5G", """[110]""", "110.00")
    check("%#.5G", """[1.1]""", "1.1000")
    check("%#10.3G", """[1000000001]""", "  1.00E+09")
    check("%#10.3G", """[1100]""", "  1.10E+03")
    check("%#10.3G", """[1.1]""", "      1.10")
    check("%#10.5G", """[1000000001]""", "1.0000E+09")
    check("%#10.5G", """[1100]""", "    1100.0")
    check("%#10.5G", """[110]""", "    110.00")
    check("%#10.5G", """[1.1]""", "    1.1000")
    check("%.3G", """[1000000001]""", "1E+09")
    check("%.3G", """[1100]""", "1.1E+03")
    check("%.3G", """[1.1]""", "1.1")
    check("%.5G", """[1000000001]""", "1E+09")
    check("%.5G", """[1100]""", "1100")
    check("%.5G", """[110]""", "110")
    check("%.5G", """[1.1]""", "1.1")
    check("%10.3G", """[1000000001]""", "     1E+09")
    check("%10.3G", """[1100]""", "   1.1E+03")
    check("%10.3G", """[1.1]""", "       1.1")
    check("%10.5G", """[1000000001]""", "     1E+09")
    check("%10.5G", """[1100]""", "      1100")
    check("%10.5G", """[110]""", "       110")
    check("%10.5G", """[1.1]""", "       1.1")

    // lots together, also test % operator
    check("%s[%05d]-%2x%2x%2x%c", """["foo", 3991, 17, 18, 17, 100]""", "foo[03991]-111211d")

    // use of *
//    check("%*d", """[10, 8]""", "%10d" % [8])
//    check("%*.*f", """[10, 3, 1 / 3]""", "%10.3f" % [1 / 3])

    // Test mappings
    check(
      "%(name)s[%(id)05d]-%(a)2x%(b)2x%(c)2x%(x)c",
      """{ "name": "foo", "id": 3991, "a": 17, "b": 18, "c": 17, "x": 100 }""",
      "foo[03991]-111211d"
    )

    // apparently you can pass in positional parameters to named interpolations
    check("XXX%(ignored_lols)sXXX %s", """[1.1, 2]""", "XXX1.1XXX 2")
  }
}
