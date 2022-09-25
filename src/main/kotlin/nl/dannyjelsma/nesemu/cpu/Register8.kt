package nl.dannyjelsma.nesemu.cpu

open class Register8(val name: String) {

    var value: UByte = 0u

    override fun toString(): String {
        return "Register8(name='$name', value=$value)"
    }
}