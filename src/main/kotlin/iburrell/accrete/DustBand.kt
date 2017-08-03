// Author: Ian Burrell  <iburrell@leland.stanford.edu>
// Created: 1997/02/09
// Modified:

// Copyright 1997 Ian Burrell

package iburrell.accrete

/**

 * Stores the data for a band of dust of gas.  Contains the inner and
 * outer edge, and whether it has dust or gas present.  Has a pointer
 * to maintain the list of bands.

 * The list of DustBands is maintained by the Accrete class; the
 * internals are exposed for manipulation.

 */
data class DustBand constructor(
        var inner: Double, // inner edge (in AU)
        var outer: Double, // outer edge (in AU)
        var dust: Boolean = true, // dust present
        var gas: Boolean = true, // gas present
        var next: DustBand? = null
)


