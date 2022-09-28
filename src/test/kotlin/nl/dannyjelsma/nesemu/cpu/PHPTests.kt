package nl.dannyjelsma.nesemu.cpu

import nl.dannyjelsma.nesemu.EmuTest
import nl.dannyjelsma.nesemu.EmuTestBuilder
import org.junit.jupiter.api.Test

class PHPTests {

    @Test
    fun testPHP() {
        val instruction = Instruction(Opcode.PHP, 0x12u, 0x1291u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withProgramCounter(0x0276u)
            .withRegisters(mapOf("S" to 0xFDu, "P" to 0x66u))
            .expectProgramCounter(0x0277u)
            .expectInstructionSize(1)
            .expectCycles(3)
            .expectMemory(mapOf(0x12u.toUShort() to (0x74u shl 1).toUByte()))
            .expectNegativeFlag(true)
            .expectZeroFlag(false)
            .expectCarryFlag(false)
            .build()

        test.runTest()
    }
}