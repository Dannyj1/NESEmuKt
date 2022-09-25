package nl.dannyjelsma.nesemu.cpu

import nl.dannyjelsma.nesemu.EmuTest
import nl.dannyjelsma.nesemu.EmuTestBuilder
import nl.dannyjelsma.nesemu.NES
import org.junit.jupiter.api.Test

class OpcodeTests {

    // TODO: write test BRK
    private val defaultTestNES: NES = EmuTestBuilder().build().nes

    @Test
    fun test_ORA_INDIRECT_X() {
        val targetAddress = defaultTestNES.ram.getIndirectXAddress(0x12u)
        val instruction = Instruction(Opcode.ORA_INDIRECT_X, 0x12u, 0x1291u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withMemory(mapOf(targetAddress to 0x04u))
            .withRegisters(mapOf("A" to 0x02u))
            .expectInstructionSize(2)
            .expectCycles(6)
            .expectRegisters(mapOf("A" to (0x04u or 0x02u).toUByte()))
            .expectNegativeFlag(false)
            .expectZeroFlag(false)
            .build()

        test.runTest()
    }

    @Test
    fun testNegative_ORA_INDIRECT_X() {
        val targetAddress = defaultTestNES.ram.getIndirectXAddress(0x73u)
        val instruction = Instruction(Opcode.ORA_INDIRECT_X, 0x73u, 0x7311u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withMemory(mapOf(targetAddress to 0x24u))
            .withRegisters(mapOf("A" to 0x19u))
            .expectInstructionSize(2)
            .expectCycles(6)
            .expectRegisters(mapOf("A" to (0x24u or 0x19u).toUByte()))
            .expectNegativeFlag(true)
            .expectZeroFlag(false)
            .build()

        test.runTest()
    }

    @Test
    fun testZero_ORA_INDIRECT_X() {
        val instruction = Instruction(Opcode.ORA_INDIRECT_X, 0x83u, 0x8329u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withZeroedMemory()
            .withRegisters(mapOf("A" to 0x0u))
            .expectInstructionSize(2)
            .expectCycles(6)
            .expectRegisters(mapOf("A" to 0x0u))
            .expectNegativeFlag(false)
            .expectZeroFlag(true)
            .build()

        test.runTest()
    }


}