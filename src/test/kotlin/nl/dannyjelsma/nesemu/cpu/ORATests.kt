package nl.dannyjelsma.nesemu.cpu

import nl.dannyjelsma.nesemu.EmuTest
import nl.dannyjelsma.nesemu.EmuTestBuilder
import nl.dannyjelsma.nesemu.NES
import org.junit.jupiter.api.Test

class ORATests {

    private val defaultTestNES: NES = EmuTestBuilder().build().nes

    @Test
    fun test_ORA_INDIRECT_X() {
        val targetAddress = defaultTestNES.ram.getIndirectXAddress(0x12u)
        val instruction = Instruction(Opcode.ORA_INDIRECT_X, 0x12u, 0x1291u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withMemory(mapOf(targetAddress to 0x04u))
            .withRegisters(mapOf("A" to 0x02u))
            .withProgramCounter(0x0026u)
            .expectProgramCounter(0x0028u)
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
            .withProgramCounter(0x2811u)
            .expectInstructionSize(2)
            .expectProgramCounter(0x2813u)
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
            .withProgramCounter(0x4293u)
            .expectInstructionSize(2)
            .expectCycles(6)
            .expectProgramCounter(0x4295u)
            .expectRegisters(mapOf("A" to 0x0u))
            .expectNegativeFlag(false)
            .expectZeroFlag(true)
            .build()

        test.runTest()
    }

    @Test
    fun test_ORA_ZERO_PAGE() {
        val instruction = Instruction(Opcode.ORA_ZERO_PAGE, 0x36u, 0x3691u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withMemory(mapOf(0x36u.toUShort() to 0x04u))
            .withRegisters(mapOf("A" to 0x02u))
            .withProgramCounter(0x0026u)
            .expectProgramCounter(0x0028u)
            .expectInstructionSize(2)
            .expectCycles(6)
            .expectRegisters(mapOf("A" to (0x04u or 0x02u).toUByte()))
            .expectNegativeFlag(false)
            .expectZeroFlag(false)
            .build()

        test.runTest()
    }

    @Test
    fun testNegative_ORA_ZERO_PAGE() {
        val instruction = Instruction(Opcode.ORA_ZERO_PAGE, 0x12u, 0x1211u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withMemory(mapOf(0x12u.toUShort() to 0x24u))
            .withRegisters(mapOf("A" to 0x19u))
            .withProgramCounter(0x2811u)
            .expectInstructionSize(2)
            .expectProgramCounter(0x2813u)
            .expectCycles(6)
            .expectRegisters(mapOf("A" to (0x24u or 0x19u).toUByte()))
            .expectNegativeFlag(true)
            .expectZeroFlag(false)
            .build()

        test.runTest()
    }

    @Test
    fun testZero_ORA_ZERO_PAGE() {
        val instruction = Instruction(Opcode.ORA_ZERO_PAGE, 0x48u, 0x4811u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withZeroedMemory()
            .withRegisters(mapOf("A" to 0x0u))
            .withProgramCounter(0x4293u)
            .expectInstructionSize(2)
            .expectCycles(6)
            .expectProgramCounter(0x4295u)
            .expectRegisters(mapOf("A" to 0x0u))
            .expectNegativeFlag(false)
            .expectZeroFlag(true)
            .build()

        test.runTest()
    }

    @Test
    fun test_ORA_IMMEDIATE() {
        val instruction = Instruction(Opcode.ORA_IMMEDIATE, 0x18u, 0x1811u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withRegisters(mapOf("A" to 0x02u))
            .withProgramCounter(0x4293u)
            .expectInstructionSize(2)
            .expectCycles(2)
            .expectProgramCounter(0x4295u)
            .expectRegisters(mapOf("A" to (0x02u or 0x18u).toUByte()))
            .expectNegativeFlag(false)
            .expectZeroFlag(false)
            .build()

        test.runTest()
    }

    @Test
    fun testNegative_ORA_IMMEDIATE() {
        val instruction = Instruction(Opcode.ORA_IMMEDIATE, 0x18u, 0x1811u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withRegisters(mapOf("A" to 0x02u))
            .withProgramCounter(0x4293u)
            .expectInstructionSize(2)
            .expectCycles(2)
            .expectProgramCounter(0x4295u)
            .expectRegisters(mapOf("A" to (0x02u or 0x18u).toUByte()))
            .expectNegativeFlag(true)
            .expectZeroFlag(false)
            .build()

        test.runTest()
    }
    @Test
    fun testZero_ORA_IMMEDIATE() {
        val instruction = Instruction(Opcode.ORA_IMMEDIATE, 0x00u, 0x0012u)
        val test: EmuTest = EmuTestBuilder()
            .withInstruction(instruction)
            .withRegisters(mapOf("A" to 0x00u))
            .withProgramCounter(0x4293u)
            .expectInstructionSize(2)
            .expectCycles(2)
            .expectProgramCounter(0x4295u)
            .expectRegisters(mapOf("A" to 0x0u))
            .expectNegativeFlag(false)
            .expectZeroFlag(true)
            .build()

        test.runTest()
    }
}