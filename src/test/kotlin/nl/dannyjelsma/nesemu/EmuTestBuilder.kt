package nl.dannyjelsma.nesemu

import nl.dannyjelsma.nesemu.cpu.FlagsRegister
import nl.dannyjelsma.nesemu.cpu.Instruction
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.SplittableRandom

class EmuTestBuilder {

    private val logger: Logger = LogManager.getLogger(EmuTestBuilder::class.java)
    private val nes = NES()

    private var expectedCycles: Int? = null
    private var expectedInstructionSize: Int? = null
    private var instruction: Instruction? = null
    private var expectedRegisters: Map<String, UByte>? = null
    private var expectedProgramCounter: UShort? = null
    private var expectedMemory: Map<UShort, UByte>? = null
    private var expectedCarryFlag: Boolean? = null
    private var expectedZeroFlag: Boolean? = null
    private var expectedInterruptDisableFlag: Boolean? = null
    private var expectedDecimalFlag: Boolean? = null
    private var expectedOverflowFlag: Boolean? = null
    private var expectedNegativeFlag: Boolean? = null

    init {
        val random = SplittableRandom(42)

        for (i in 0u .. 0x07FFu) {
            val memoryValue = random.nextInt(UByte.MIN_VALUE.toInt(), UByte.MAX_VALUE.toInt()).toUByte()

            nes.ram.write(i.toUShort(), memoryValue)
        }
    }

    fun withMemory(memory: Map<UShort, UByte>): EmuTestBuilder {
        for ((address, value) in memory) {
            nes.ram.write(address, value)
        }

        return this
    }

    fun withZeroedMemory(): EmuTestBuilder {
        for (i in 0u .. 0x07FFu) {
            nes.ram.write(i.toUShort(), 0u)
        }

        return this
    }

    fun withRegisters(values: Map<String, UByte>): EmuTestBuilder {
        for ((register, value) in values) {
            when (register.lowercase()) {
                "a" -> nes.cpu.a.value = value
                "x" -> nes.cpu.x.value = value
                "y" -> nes.cpu.y.value = value
                "p" -> nes.cpu.p.value = value
                "s" -> nes.cpu.s.value = value
                else -> logger.warn("Unknown register: $register")
            }
        }

        return this
    }

    fun withFlagsRegister(register: FlagsRegister): EmuTestBuilder {
        nes.cpu.p.value = register.value
        return this
    }

    fun withProgramCounter(value: UShort): EmuTestBuilder {
        nes.cpu.pc.value = value
        return this
    }

    fun withInstruction(instruction: Instruction): EmuTestBuilder {
        this.instruction = instruction
        return this
    }

    fun expectCycles(cycles: Int): EmuTestBuilder {
        this.expectedCycles = cycles
        return this
    }

    fun expectInstructionSize(expectedInstructionSize: Int): EmuTestBuilder {
        this.expectedInstructionSize = expectedInstructionSize
        return this
    }

    fun expectRegisters(values: Map<String, UByte>): EmuTestBuilder {
        this.expectedRegisters = values
        return this
    }

    fun expectCarryFlag(value: Boolean): EmuTestBuilder {
        this.expectedCarryFlag = value
        return this
    }

    fun expectZeroFlag(value: Boolean): EmuTestBuilder {
        this.expectedZeroFlag = value
        return this
    }

    fun expectInterruptDisableFlag(value: Boolean): EmuTestBuilder {
        this.expectedInterruptDisableFlag = value
        return this
    }

    fun expectDecimalFlag(value: Boolean): EmuTestBuilder {
        this.expectedDecimalFlag = value
        return this
    }

    fun expectOverflowFlag(value: Boolean): EmuTestBuilder {
        this.expectedOverflowFlag = value
        return this
    }

    fun expectNegativeFlag(value: Boolean): EmuTestBuilder {
        this.expectedNegativeFlag = value
        return this
    }

    fun expectProgramCounter(counter: UShort): EmuTestBuilder {
        this.expectedProgramCounter = counter
        return this
    }

    fun expectMemory(values: Map<UShort, UByte>): EmuTestBuilder {
        this.expectedMemory = values
        return this
    }

    fun build(): EmuTest {
        return EmuTest(
            nes,
            instruction,
            expectedCycles,
            expectedInstructionSize,
            expectedRegisters,
            expectedProgramCounter,
            expectedMemory,
            expectedCarryFlag,
            expectedZeroFlag,
            expectedInterruptDisableFlag,
            expectedDecimalFlag,
            expectedOverflowFlag,
            expectedNegativeFlag
        )
    }
}
