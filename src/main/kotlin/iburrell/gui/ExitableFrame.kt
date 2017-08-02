// Author: Ian Burrell  <iburrell@leland.stanford.edu>
// Created: 1996/12/09
// Modified: 1996/12/09

// Copyright 1996 Ian Burrell

package iburrell.gui

import java.awt.*

open class ExitableFrame : Frame {

    constructor() : super() {}

    constructor(t: String) : super(t) {}

    public override fun processEvent(e: AWTEvent?) {
        if (e!!.id == Event.WINDOW_DESTROY)
            System.exit(0)
        super.processEvent(e)
    }

}

