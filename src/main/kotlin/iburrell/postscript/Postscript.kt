package iburrell.postscript

// Author: Ian Burrell  <iburrell@leland.stanford.edu>
// Created: 1997/01/15
// Modified: 1997/02/09

// Copyright 1997 Ian Burrell

import java.io.FileOutputStream
import java.io.PrintStream

open class Postscript(psfile: String) {

    internal var page = 0
    internal var base = 50.0
    internal var xscale = 1.0
    internal var yscale = 1.0
    internal var xoff = 0.0
    internal var yoff = 0.0

    internal var out: PrintStream = PrintStream(FileOutputStream(psfile))

    protected fun begin(numpage: Int) {
        out.println("%!PS-Adobe-2.1")
        out.println("%%Pages: " + numpage)
        out.println("%%EndComments")
        out.println("/Helvetica findfont 12 scalefont setfont")
        out.println("0 setlinewidth")
        out.println("newpath")
        out.println("%%EndProlog")
        beginpage()
    }

    protected fun end() {
        out.println("%%Trailer")
        out.println("end")
    }

    @JvmOverloads protected fun beginpage(pg: Int = ++page) {
        out.println("%%Page: $pg $pg")
        out.println((xoff + base).toString() + " " + yoff + base + " translate")
        out.println(xscale.toString() + " " + yscale + " " + " scale")
        out.println("/Helvetica findfont " + 9 / xscale + " scalefont setfont")
        out.println("0 setlinewidth")
    }

    protected fun showpage() {
        out.println("showpage")
    }

    protected fun window(x1: Double, y1: Double, x2: Double, y2: Double) {
        val xspan = x2 - x1
        val yspan = y2 - y1
        xscale = width / xspan
        yscale = width / yspan
        xoff = -xscale * x1
        yoff = -yscale * y1
    }

    protected fun circle(x: Double, y: Double, radius: Double, fill: Boolean) {
        out.print(x.toString() + " " + y + " " + radius + " 0 360 arc ")
        val type = if (fill) "fill" else "stroke"
        out.println(type)
    }

    protected fun line(x1: Double, y1: Double, x2: Double, y2: Double) {
        out.print(x1.toString() + " " + y1 + " moveto ")
        out.print(x2.toString() + " " + y2 + " lineto stroke")
        out.println()
    }

    protected fun text(x: Double, y: Double, s: String) {
        out.println(x.toString() + " " + y + " moveto (" + s + ") show newpath")
    }

    companion object {
        internal val width = 450.0
    }

}
