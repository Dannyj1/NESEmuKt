package nl.dannyjelsma.nesemu.cpu

enum class Opcode(val opcode: UByte, val instructionSize: Int, val cycles: Int) {

    BRK(0x00u, 1, 7),
    ORA_INDIRECT_X(0x01u, 2, 6);

    companion object {
        fun fromValue(value: UByte) = Opcode.values()[value.toInt()]
    }
}