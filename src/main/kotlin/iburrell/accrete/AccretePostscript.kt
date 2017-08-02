package iburrell.accrete

// Author: Ian Burrell  <iburrell@leland.stanford.edu>
// Created: 1997/01/15
// Modified: 1997/02/09

// Copyright 1997 Ian Burrell

import iburrell.Postscript

import java.util.Enumeration
import java.util.Vector

class AccretePostscript internal constructor() : Postscript("accrete.ps") {

    internal var gen: Accrete

    init {
        gen = Accrete()
        window(-1.0, -1.0, 2.0, 1.0)
    }

    internal fun run() {
        begin(1)
        logscale("AU")

        val system = gen.DistributePlanets()

        val e = system.elements()
        while (e.hasMoreElements()) {
            val curr = e.nextElement() as Planetismal
            val au = log10(curr.orbitalAxis)
            val r = Math.pow(curr.massSolar, 1.0 / 3.0)
            circle(au, 0.0, r, curr.isGasGiant)
        }
        showpage()
        end()
    }

    @JvmOverloads internal fun logscale(xlabel: String = "", ylabel: String = "") {
        line(-1.0, -1.0, 3.0, -1.0)
        line(3.0, -1.0, 3.0, 1.0)
        line(3.0, 1.0, 3.0, -1.0)
        line(3.0, -1.0, -1.0, -1.0)

        line(-1.0, 1.0, 3.0, 1.0)
        for (i in 1..10) {
            val au = i.toDouble()
            line(log10(au / 10), 1.0, log10(au / 10), .95)
            line(log10(au), 1.0, log10(au), .95)
            line(log10(au * 10), 1.0, log10(au * 10), .95)
        }

        text(-1.0, 1.0, ".1")
        text(0.0, 1.0, "1")
        text(1.0, 1.0, "10")
        text(2.0, 1.0, "100")

        text(2.3, 1.0, xlabel)
        text(-1.0, .9, ylabel)

    }

    private fun log10(a: Double): Double {
        return Math.log(a) / Math.log(10.0)
    }

    companion object {


        @JvmStatic fun main(args: Array<String>) {
            val app = AccretePostscript()
            app.run()
        }
    }

}

