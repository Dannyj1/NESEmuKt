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
            .expectRegisters(mapOf("S" to 0xFCu))
            .expectMemory(mapOf(0x01FDu.toUShort() to 0x66u))
            .expectNegativeFlag(false)
            .expectZeroFlag(false)
            .expectCarryFlag(false)
            .build()

        test.runTest()
    }
}