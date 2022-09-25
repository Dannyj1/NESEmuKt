package nl.dannyjelsma.nesemu.cpu

class Instruction(opcodeValue: UByte, val arg8: UByte, val arg16: UShort) {

    val opcode: Opcode

    constructor(opcode: Opcode, arg8: UByte, arg16: UShort) : this(opcode.opcode, arg8, arg16)

    init {
        this.opcode = Opcode.fromValue(opcodeValue)
    }

    override fun toString(): String {
        return "Instruction(opcode=$opcode, arg8=$arg8, arg16=$arg16)"
    }
}