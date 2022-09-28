package nl.dannyjelsma.nesemu.cpu

// TODO: Implement BRK, PHP
// TODO: implement and test illegal opcodes
enum class Opcode(val opcode: UByte, val instructionSize: Int, val cycles: Int) {

    BRK(0x00u, 1, 7),
    ORA_INDIRECT_X(0x01u, 2, 6),
    ORA_ZERO_PAGE(0x05u, 2, 3),
    ASL_ZERO_PAGE(0x06u, 2, 5),
    PHP(0x08u, 1, 3),;

    companion object {
        // Faster, but only works when everything is implemented
        // fun fromValue(value: UByte) = Opcode.values()[value.toInt()]
        fun fromValue(value: UByte) = values().first { it.opcode == value }
    }
}