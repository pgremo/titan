package iburrell

// Author: Ian Burrell  <iburrell@leland.stanford.edu>
// Created: 1997/01/17
// Modified: 1997/02/09

// Copyright 1997 Ian Burrell

import iburrell.accrete.Accrete
import iburrell.accrete.Planetismal
import iburrell.gui.AppletFrame
import java.applet.Applet
import java.awt.Color
import java.awt.Graphics
import java.awt.event.MouseEvent

class AccreteApplet : Applet(), Runnable {

    private var standalone: Boolean = false

    private var gen: Accrete? = null
    private var planets: List<Planetismal>? = null

    override fun init() {
        background = Color.white
        foreground = Color.black
        gen = Accrete()
    }

    override fun start() {
        run()
    }

    override fun run() {
        planets = gen!!.DistributePlanets()
        repaint()
    }

    override fun paint(g: Graphics) {
        DrawGrid(g)
        if (planets != null)
            DrawPlanets(g)
    }

    public override fun processMouseEvent(evt: MouseEvent) {
        if (evt.clickCount >= 2) {
            start()
            repaint()
        }
    }


    private fun DrawGrid(g: Graphics) {
        val hscale = hscale()
        val vscale = vscale()
        val width = hscale * 3
        val height = vscale * 2

        g.color = Color.black
        g.drawRect(0, 0, width, height)
        g.drawLine(0, vscale, width, vscale)

        val ticklen = vscale / 10
        val ytick = height - ticklen
        g.drawLine(1 * hscale, height, 1 * hscale, height - 2 * ticklen)
        g.drawLine(2 * hscale, height, 2 * hscale, height - 2 * ticklen)

        for (i in 2..9) {
            val offset = (hscale.toDouble() * log10(i.toDouble())).toInt()
            g.drawLine(offset, height, offset, ytick)
            g.drawLine(offset + hscale, height, offset + hscale, ytick)
            g.drawLine(offset + 2 * hscale, height, offset + 2 * hscale, ytick)
        }

    }

    private fun DrawPlanets(g: Graphics) {
        val hscale = hscale()
        val vscale = vscale()
        val rscale = hscale / 30

        planets!!.forEach { curr ->
            val au = log10(curr.orbitalAxis)
            val rad = Math.pow(curr.massEarth, 1.0 / 3.0)
            val r = (rad * rscale.toDouble()).toInt()
            val x0 = (au * hscale.toDouble()).toInt()
            val x = x0 + hscale - r
            val y = vscale - r
            if (curr.isGasGiant)
                g.drawOval(x, y, 2 * r, 2 * r)
            else
                g.fillOval(x, y, 2 * r, 2 * r)
        }
    }

    private fun rscale(hscale: Int = hscale(), vscale: Int = vscale()): Int {
        return if (hscale < vscale) hscale else vscale
    }

    private fun hscale(): Int {
        var width = size.width
        if (width % 3 == 0)
            width--
        return width / 3
    }

    private fun vscale(): Int {
        var height = size.height
        if (height % 2 == 0)
            height--
        return height / 2
    }

    companion object {


        private fun log10(a: Double): Double {
            return Math.log(a) / Math.log(10.0)
        }


        @JvmStatic fun main(args: Array<String>) {
            val app = AccreteApplet()
            app.standalone = true

            val frame = AppletFrame(app, 721, 241, "Accrete")

        }
    }

}

