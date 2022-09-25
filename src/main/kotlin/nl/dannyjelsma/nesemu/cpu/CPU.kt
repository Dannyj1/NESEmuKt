package nl.dannyjelsma.nesemu.cpu

import nl.dannyjelsma.nesemu.NES
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class CPU(private val nes: NES) {
    private val logger: Logger = LogManager.getLogger(CPU::class.java)

    val a = Register8("A")
    val x = Register8("X")
    val y = Register8("Y")
    val pc = Register16("PC")
    val s = Register8("S")
    val p = FlagsRegister("P")

    fun start() {
        p.value = 0x34u
        s.value = 0xFDu
    }

    fun tick() {

    }

    fun executeInstruction(ins: Instruction) {
        logger.debug("Executing instruction: $ins")

        when (ins.opcode) {
            else -> {
                logger.error("Found unimplemented opcode: $ins")
            }
        }
    }

    override fun toString(): String {
        return "CPU(a=$a, x=$x, y=$y, pc=$pc, s=$s, p=$p)"
    }

    fun getRegisterValueByName(register: String): UByte {
        return when (register.lowercase()) {
            "a" -> a.value
            "x" -> x.value
            "y" -> y.value
            "s" -> s.value
            "p" -> p.value
            else -> throw IllegalArgumentException("Invalid register name: $register")
        }
    }
}