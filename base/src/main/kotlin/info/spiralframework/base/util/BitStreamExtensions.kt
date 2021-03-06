package info.spiralframework.base.util

import info.spiralframework.base.CountingInputStream
import java.io.InputStream
import java.io.OutputStream

fun InputStream.readInt64LE(): Long {
    val a = read().toLong()
    val b = read().toLong()
    val c = read().toLong()
    val d = read().toLong()
    val e = read().toLong()
    val f = read().toLong()
    val g = read().toLong()
    val h = read().toLong()

    return (h shl 56) or (g shl 48) or (f shl 40) or (e shl 32) or
            (d shl 24) or (c shl 16) or (b shl 8) or a
}

fun InputStream.readInt64BE(): Long {
    val a = read().toLong()
    val b = read().toLong()
    val c = read().toLong()
    val d = read().toLong()
    val e = read().toLong()
    val f = read().toLong()
    val g = read().toLong()
    val h = read().toLong()

    return (a shl 56) or (b shl 48) or (c shl 40) or (d shl 32) or
            (e shl 24) or (f shl 16) or (g shl 8) or h
}

fun InputStream.readUInt64LE(): ULong {
    val a = read().toLong()
    val b = read().toLong()
    val c = read().toLong()
    val d = read().toLong()
    val e = read().toLong()
    val f = read().toLong()
    val g = read().toLong()
    val h = read().toLong()

    return ((h shl 56) or (g shl 48) or (f shl 40) or (e shl 32) or
            (d shl 24) or (c shl 16) or (b shl 8) or a).toULong()
}

fun InputStream.readUInt64BE(): ULong {
    val a = read().toLong()
    val b = read().toLong()
    val c = read().toLong()
    val d = read().toLong()
    val e = read().toLong()
    val f = read().toLong()
    val g = read().toLong()
    val h = read().toLong()

    return ((a shl 56) or (b shl 48) or (c shl 40) or (d shl 32) or
            (e shl 24) or (f shl 16) or (g shl 8) or h).toULong()
}

fun InputStream.readInt32LE(): Int {
    val a = read()
    val b = read()
    val c = read()
    val d = read()

    return (d shl 24) or (c shl 16) or (b shl 8) or a
}

fun InputStream.readInt32BE(): Int {
    val a = read()
    val b = read()
    val c = read()
    val d = read()

    return (a shl 24) or (b shl 16) or (c shl 8) or d
}

fun InputStream.readUInt32LE(): UInt {
    val a = read()
    val b = read()
    val c = read()
    val d = read()

    return ((d shl 24) or (c shl 16) or (b shl 8) or a).toUInt()
}

fun InputStream.readUInt32BE(): UInt {
    val a = read()
    val b = read()
    val c = read()
    val d = read()

    return ((a shl 24) or (b shl 16) or (c shl 8) or d).toUInt()
}

fun InputStream.readInt16LE(): Int {
    val a = read()
    val b = read()

    return (b shl 8) or a
}

fun InputStream.readInt16BE(): Int {
    val a = read()
    val b = read()

    return (a shl 8) or b
}

fun InputStream.readFloatBE(): Float = java.lang.Float.intBitsToFloat(this.readInt32BE())
fun InputStream.readFloatLE(): Float = java.lang.Float.intBitsToFloat(this.readInt32LE())

fun <T> InputStream.read(serialise: (InputStream) -> T?): T? = serialise(this)
fun <T> CountingInputStream.readSource(source: () -> InputStream, serialise: (() -> InputStream) -> T?): T? = serialise(source.from(streamOffset))

fun OutputStream.writeFloatBE(float: Float) = writeInt32BE(java.lang.Float.floatToIntBits(float))
fun OutputStream.writeFloatLE(float: Float) = writeInt32LE(java.lang.Float.floatToIntBits(float))

fun OutputStream.writeInt64LE(num: Number) {
    val long = num.toLong()

    write(long.toInt() and 0xFF)
    write((long shr 8).toInt() and 0xFF)
    write((long shr 16).toInt() and 0xFF)
    write((long shr 24).toInt() and 0xFF)
    write((long shr 32).toInt() and 0xFF)
    write((long shr 40).toInt() and 0xFF)
    write((long shr 48).toInt() and 0xFF)
    write((long shr 56).toInt() and 0xFF)
}

fun OutputStream.writeInt64BE(num: Number) {
    val long = num.toLong()

    write((long shr 56).toInt() and 0xFF)
    write((long shr 48).toInt() and 0xFF)
    write((long shr 40).toInt() and 0xFF)
    write((long shr 32).toInt() and 0xFF)
    write((long shr 24).toInt() and 0xFF)
    write((long shr 16).toInt() and 0xFF)
    write((long shr 8).toInt() and 0xFF)
    write(long.toInt() and 0xFF)
}

fun OutputStream.writeInt32LE(num: Number) {
    val int = num.toInt()

    write(int and 0xFF)
    write((int shr 8) and 0xFF)
    write((int shr 16) and 0xFF)
    write((int shr 24) and 0xFF)
}

fun OutputStream.writeInt32BE(num: Number) {
    val int = num.toInt()

    write((int shr 24) and 0xFF)
    write((int shr 16) and 0xFF)
    write((int shr 8) and 0xFF)
    write(int and 0xFF)
}

fun OutputStream.writeUInt32BE(num: Number) {
    val int = num.toInt()

    write((int ushr 24) and 0xFF)
    write((int ushr 16) and 0xFF)
    write((int ushr 8) and 0xFF)
    write(int and 0xFF)
}

fun OutputStream.writeInt16LE(num: Number) {
    val int = num.toShort().toInt()

    write(int and 0xFF)
    write((int shr 8) and 0xFF)
}

fun OutputStream.writeInt16BE(num: Number) {
    val int = num.toShort().toInt()

    write((int shr 8) and 0xFF)
    write(int and 0xFF)
}

/**
 * Only supports 1, 2, 4, or 8
 */
inline fun InputStream.readIntXLE(x: Int): Number =
        when (x) {
            1 -> read()
            2 -> readInt16LE()
            4 -> readInt32LE()
            8 -> readInt64LE()
            else -> throw IllegalArgumentException("$x is not 1, 2, 4, or 8")
        }

inline fun OutputStream.writeIntXLE(num: Number, x: Int) =
        when (x) {
            1 -> write(num.toInt())
            2 -> writeInt16LE(num)
            4 -> writeInt32LE(num)
            8 -> writeInt64LE(num)
            else -> throw IllegalArgumentException("$x is not 1, 2, 4, or 8")
        }

fun makeMask(vararg bits: Int): Int {
    var mask = 0
    for (bit in bits)
        mask = mask or ((1 shl (bit + 1)) - 1)

    return mask
}