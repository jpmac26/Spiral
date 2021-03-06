package info.spiralframework.formats.scripting.lin

data class ScreenFlashEntry(val red: Int, val green: Int, val blue: Int, val fadeInDuration: Int, val holdDuration: Int, val fadeOutDuration: Int, val opacity: Int): LinScript {
    constructor(opCode: Int, args: IntArray): this(args[0], args[1], args[2], args[3], args[4], args[5], args[6])

    override val opCode: Int = 0x1F
    override val rawArguments: IntArray = intArrayOf(red, green, blue, fadeInDuration, holdDuration, fadeOutDuration, opacity)

    override fun format(): String = "Flash the screen rgb($red, $green, $blue) over $fadeInDuration frames, hold for $holdDuration frames, fade out over $fadeOutDuration, with opacity $opacity"
}