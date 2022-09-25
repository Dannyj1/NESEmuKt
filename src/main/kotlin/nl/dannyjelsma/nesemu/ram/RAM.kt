package nl.dannyjelsma.nesemu.ram

import nl.dannyjelsma.nesemu.NES

@OptIn(ExperimentalUnsignedTypes::class)
class RAM(private val nes: NES) {

    private val internalRam = UByteArray(0x0800)

    fun read(address: UShort): UByte {
        if (address <= 0x07FFu) {
            return internalRam[address.toInt()]
        } else if (address <= 0x0FFFu) {
            return internalRam[(address - 0x0800u).toInt()]
        } else if (address <= 0x17FFu) {
            return internalRam[(address - 0x1000u).toInt()]
        } else if (address <= 0x1FFFu) {
            return internalRam[(address - 0x1800u).toInt()]
        }

        // TODO: other stuff: https://www.nesdev.org/wiki/CPU_memory_map

        return 0u
    }

    fun read16(address: UShort): UShort {
        val high: UShort = (read(address).toUInt() shl 8).toUShort()
        val low: UShort = read((address + 0x1u).toUShort()).toUShort()

        return high or low
    }

    fun write(address: UShort, value: UByte) {
        if (address <= 0x07FFu) {
            internalRam[address.toInt()] = value
        } else if (address <= 0x0FFFu) {
            internalRam[(address - 0x0800u).toInt()] = value
        } else if (address <= 0x17FFu) {
            internalRam[(address - 0x1000u).toInt()] = value
        } else if (address <= 0x1FFFu) {
            internalRam[(address - 0x1800u).toInt()] = value
        }

        // TODO: other stuff: https://www.nesdev.org/wiki/CPU_memory_map
    }

    fun write16(address: UShort, value: UShort) {
        write(address, (value.toUInt() shr 8).toUByte())
        write((address + 0x1u).toUShort(), value.toUByte())
    }

    fun getZeroPageXAddress(arg: UByte): UByte {
        val zeroPageAddr: UByte = ((arg + nes.cpu.x.value) % 256u).toUByte()
        val zeroPageValue: UByte = read(zeroPageAddr.toUShort())

        return read(zeroPageValue.toUShort())
    }

    fun getZeroPageYAddress(arg: UByte): UByte {
        val zeroPageAddr: UByte = ((arg + nes.cpu.y.value) % 256u).toUByte()
        val zeroPageValue: UByte = read(zeroPageAddr.toUShort())

        return read(zeroPageValue.toUShort())
    }

    fun getAbsoluteXAddress(arg: UShort): UShort {
        return (arg + nes.cpu.x.value.toUShort()).toUShort()
    }

    fun getAbsoluteYAddress(arg: UShort): UShort {
        return (arg + nes.cpu.y.value.toUShort()).toUShort()
    }

    // (d,x)
    fun getIndirectXAddress(arg: UShort): UShort {
        val zeroPageAddr: UByte = ((arg + nes.cpu.x.value) % 256u).toUByte()
        val low: UByte = read((zeroPageAddr.toUShort() % 256u).toUShort())
        val high: UByte = read(((zeroPageAddr + 0x1u) % 256u).toUShort())

        return ((high.toUInt() shl 8) or low.toUInt()).toUShort()
    }

    // (d),y
    fun getIndirectYAddress(arg: UShort): UShort {
        val low = read((arg % 256u).toUShort())
        val high = read(((arg + 0x1u) % 256u).toUShort())
        val address = ((high.toUInt() shl 8) or low.toUInt()).toUShort()

        return (address + nes.cpu.y.value.toUShort()).toUShort()
    }

    // https://bugzmanov.github.io/nes_ebook/chapter_3_2.html
}