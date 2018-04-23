// Author: Ian Burrell  <titan.iburrell@leland.stanford.edu>
// Created: 1997/02/09
// Modified:

// Copyright 1997 Ian Burrell

package titan.iburrell.accrete

data class DustBand(
        override var start: Double,
        override var endInclusive: Double,
        var dust: Boolean = true,
        var gas: Boolean = true,
        var next: DustBand? = null
) : ClosedRange<Double>


