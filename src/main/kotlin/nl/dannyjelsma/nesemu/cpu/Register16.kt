package nl.dannyjelsma.nesemu.cpu

class Register16(val name: String) {

    var value: UShort = 0u

    override fun toString(): String {
        return "Register16(name='$name', value=$value)"
    }
}