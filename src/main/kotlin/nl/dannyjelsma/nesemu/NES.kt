package nl.dannyjelsma.nesemu

import nl.dannyjelsma.nesemu.cpu.CPU
import nl.dannyjelsma.nesemu.ram.RAM

class NES {

    val ram = RAM(this)
    val cpu = CPU(this)

    fun start() {

    }
}