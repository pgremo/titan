package iburrell.gui

import java.applet.Applet
import java.applet.AppletContext
import java.applet.AppletStub
import java.applet.AudioClip
import java.awt.*
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.Enumeration


class AppletFrame(applet: Applet, width: Int, height: Int, title: String) : ExitableFrame(title), AppletStub, AppletContext {

    init {

        this.setSize(width, height)
        this.add("Center", applet)

        applet.setStub(this)

        applet.init()
        this.isVisible = true
        applet.start()
    }

    constructor(a: Applet, x: Int, y: Int) : this(a, x, y, a.javaClass.name) {}

    // AppletStub methods
    override fun isActive(): Boolean {
        return true
    }

    override fun getDocumentBase(): URL? {
        return null
    }

    override fun getCodeBase(): URL? {
        return null
    }

    override fun getParameter(name: String): String {
        return ""
    }

    override fun getAppletContext(): AppletContext {
        return this
    }

    override fun appletResize(width: Int, height: Int) {}

    // AppletContext methods
    override fun getAudioClip(url: URL): AudioClip? {
        return null
    }

    override fun getImage(url: URL): Image? {
        return null
    }

    override fun getApplet(name: String): Applet? {
        return null
    }

    override fun getApplets(): Enumeration<Applet>? {
        return null
    }

    override fun showDocument(url: URL) {}
    override fun showDocument(url: URL, target: String) {}
    override fun showStatus(status: String) {}

    @Throws(IOException::class)
    override fun setStream(key: String, stream: InputStream) {

    }

    override fun getStream(key: String): InputStream? {
        return null
    }

    override fun getStreamKeys(): Iterator<String>? {
        return null
    }

}
