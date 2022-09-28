package nl.dannyjelsma.nesemu.cpu

import nl.dannyjelsma.nesemu.EmuTest
import nl.dannyjelsma.nesemu.EmuTestBuilder
import nl.dannyjelsma.nesemu.NES
import org.junit.jupiter.api.Test

class ASLTests {

    private val defaultTestNES: NES = EmuTestBuilder().build().nes

    @Test
    fun test_ASL_ZERO_PAGE() {
        val instruction = Instruction(Opcode.ASL_ZERO_PAGE, 0x12u, 0x1291u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withMemory(mapOf(0x12u.toUShort() to 0x34u))
            .withProgramCounter(0x0276u)
            .expectProgramCounter(0x0278u)
            .expectInstructionSize(2)
            .expectCycles(5)
            .expectMemory(mapOf(0x12u.toUShort() to (0x34u shl 1).toUByte()))
            .expectNegativeFlag(false)
            .expectZeroFlag(false)
            .expectCarryFlag(false)
            .build()

        test.runTest()
    }

    @Test
    fun testZero_ASL_ZERO_PAGE() {
        val instruction = Instruction(Opcode.ASL_ZERO_PAGE, 0x12u, 0x1291u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withZeroedMemory()
            .withProgramCounter(0x0276u)
            .expectProgramCounter(0x0278u)
            .expectInstructionSize(2)
            .expectCycles(5)
            .expectMemory(mapOf(0x12u.toUShort() to 0x0u))
            .expectNegativeFlag(false)
            .expectZeroFlag(true)
            .expectCarryFlag(false)
            .build()

        test.runTest()
    }

    @Test
    fun testCarry_ASL_ZERO_PAGE() {
        val instruction = Instruction(Opcode.ASL_ZERO_PAGE, 0x12u, 0x1291u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withMemory(mapOf(0x12u.toUShort() to 0xB4u))
            .withProgramCounter(0x0276u)
            .expectProgramCounter(0x0278u)
            .expectInstructionSize(2)
            .expectCycles(5)
            .expectMemory(mapOf(0x12u.toUShort() to (0xB4u shl 1).toUByte()))
            .expectNegativeFlag(false)
            .expectZeroFlag(false)
            .expectCarryFlag(true)
            .build()

        test.runTest()
    }

    @Test
    fun testNegative_ASL_ZERO_PAGE() {
        val instruction = Instruction(Opcode.ASL_ZERO_PAGE, 0x12u, 0x1291u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withMemory(mapOf(0x12u.toUShort() to 0x74u))
            .withProgramCounter(0x0276u)
            .expectProgramCounter(0x0278u)
            .expectInstructionSize(2)
            .expectCycles(5)
            .expectMemory(mapOf(0x12u.toUShort() to (0x74u shl 1).toUByte()))
            .expectNegativeFlag(true)
            .expectZeroFlag(false)
            .expectCarryFlag(false)
            .build()

        test.runTest()
    }
}