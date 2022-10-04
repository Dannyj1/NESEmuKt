package nl.dannyjelsma.nesemu.cpu

// TODO: Implement BRK, PHP
// TODO: implement and test illegal opcodes
enum class Opcode(val opcode: UByte, val instructionSize: Int) {

    BRK(0x00u, 1),
    ORA_INDIRECT_X(0x01u, 2),
    ORA_ZERO_PAGE(0x05u, 2),
    ASL_ZERO_PAGE(0x06u, 2),
    PHP(0x08u, 1),
    ORA_IMMEDIATE(0x09u, 2),
    ASL_ACCUMULATOR(0x0Au, 1);

    companion object {
        // Faster, but only works when everything is implemented
        // fun fromValue(value: UByte) = Opcode.values()[value.toInt()]
        fun fromValue(value: UByte) = values().first { it.opcode == value }
    }
}