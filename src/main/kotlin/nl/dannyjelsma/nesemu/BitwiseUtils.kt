package nl.dannyjelsma.nesemu

fun getBit(value: UByte, index: Int): UByte {
    return ((value.toUInt() shr index) and 1u).toUByte()
}

fun getBit(value: UShort, index: Int): UShort {
    return ((value.toUInt() shr index) and 1u).toUShort()
}