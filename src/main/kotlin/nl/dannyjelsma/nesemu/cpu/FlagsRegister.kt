package nl.dannyjelsma.nesemu.cpu

class FlagsRegister(name: String) : Register8(name) {

    fun getCarry(): Boolean {
        return getFlagBit(0u)
    }

    fun setCarry(value: Boolean) {
        setFlagBit(0u, value)
    }

    fun getZero(): Boolean {
        return getFlagBit(1u)
    }

    fun setZero(value: Boolean) {
        setFlagBit(1u, value)
    }

    fun getInterruptDisable(): Boolean {
        return getFlagBit(2u)
    }

    fun setInterruptDisable(value: Boolean) {
        setFlagBit(2u, value)
    }

    fun getDecimal(): Boolean {
        return getFlagBit(3u)
    }

    fun setDecimal(value: Boolean) {
        setFlagBit(3u, value)
    }

    fun getOverflow(): Boolean {
        return getFlagBit(6u)
    }

    fun setOverflow(value: Boolean) {
        setFlagBit(6u, value)
    }

    fun getNegative(): Boolean {
        return getFlagBit(7u)
    }

    fun setNegative(value: Boolean) {
        setFlagBit(7u, value)
    }

    fun getFlagBit(index: UInt): Boolean {
        if (index > 7u) {
            throw IllegalArgumentException("Index must be between 0 and 7")
        }

        return (value and (1 shl index.toInt()).toUByte()).toInt() != 0
    }

    fun setFlagBit(index: UInt, value: UByte) {
        if (value > 1u) {
            throw IllegalArgumentException("Value must be 0 or 1")
        }

        if (index > 7u) {
            throw IllegalArgumentException("Index must be between 0 and 7")
        }

        setFlagBit(index, value != 0.toUByte())
    }

    fun setFlagBit(index: UInt, value: Boolean) {
        if (value) {
            this.value = this.value or (1 shl index.toInt()).toUByte()
        } else {
            this.value = this.value and ((1 shl index.toInt()).toUByte().inv())
        }
    }
}