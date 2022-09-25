package nl.dannyjelsma.nesemu

import nl.dannyjelsma.nesemu.cpu.Instruction
import kotlin.test.assertEquals

class EmuTest(val nes: NES,
              private val instruction: Instruction? = null,
              private val expectedCycles: Int? = null,
              private val expectedInstructionSize: Int? = null,
              private val expectedRegisters: Map<String, UByte>? = null,
              private val expectedProgramCounter: UShort? = null,
              private val expectedMemory: Map<UShort, UByte>? = null,
              private val expectedCarryFlag: Boolean? = null,
              private val expectedZeroFlag: Boolean? = null,
              private val expectedInterruptDisableFlag: Boolean? = null,
              private val expectedDecimalFlag: Boolean? = null,
              private val expectedOverflowFlag: Boolean? = null,
              private val expectedNegativeFlag: Boolean? = null) {

    fun runTest() {
        if (instruction != null) {
            nes.cpu.executeInstruction(instruction)

            if (expectedCycles != null) {
                // TODO: might need to be changed after waiting has been properly implemented in the CPU
                assertEquals(expectedCycles, instruction.opcode.cycles, "Opcode ${instruction.opcode.name} " +
                        "has incorrect number of cycles")
            }

            if (expectedInstructionSize != null) {
                // TODO: might need to be changed after waiting has been properly implemented in the CPU
                assertEquals(expectedInstructionSize, instruction.opcode.instructionSize,
                    "Opcode ${instruction.opcode.name} has incorrect instruction size")
            }
        }

        expectedRegisters?.forEach { (register, value) ->
            assertEquals(value, nes.cpu.getRegisterValueByName(register), "Register $register does not contain expected value")
        }

        if (expectedProgramCounter != null) {
            assertEquals(expectedProgramCounter, nes.cpu.pc.value, "Program counter does not match")
        }

        expectedMemory?.forEach { (address, value) ->
            assertEquals(value, nes.ram.read(address), "Memory at address $address does not contain expected value")
        }

        if (expectedCarryFlag != null) {
            assertEquals(expectedCarryFlag, nes.cpu.p.getCarry(), "Carry flag does not match")
        }

        if (expectedZeroFlag != null) {
            assertEquals(expectedZeroFlag, nes.cpu.p.getZero(), "Zero flag does not match")
        }

        if (expectedInterruptDisableFlag != null) {
            assertEquals(expectedInterruptDisableFlag, nes.cpu.p.getInterruptDisable(),
                "Interrupt disable flag does not match")
        }

        if (expectedDecimalFlag != null) {
            assertEquals(expectedDecimalFlag, nes.cpu.p.getDecimal(), "Decimal flag does not match")
        }

        if (expectedOverflowFlag != null) {
            assertEquals(expectedOverflowFlag, nes.cpu.p.getOverflow(), "Overflow flag does not match")
        }

        if (expectedNegativeFlag != null) {
            assertEquals(expectedNegativeFlag, nes.cpu.p.getNegative(), "Negative flag does not match")
        }
    }
}