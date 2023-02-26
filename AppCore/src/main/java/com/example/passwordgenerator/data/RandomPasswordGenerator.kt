package com.example.passwordgenerator.data

class RandomPasswordGenerator {
    private val symbols = listOf('!', '@', '#', '$', '%', '&', '*', '_', '-', '?')
    private val upperLetters = 'A'..'Z'
    private val lowerLetters = 'a'..'z'
    private val numbs = '0'..'9'
    private val baze = symbols + upperLetters + lowerLetters + numbs

    fun generatePassword(): String {
        val password = StringBuilder()

        while (!isStrong(password.toString())) {
            password.clear()
            for (i in 0 until 10) {
                password.append(baze.random())
            }
        }
        return password.toString()
    }

    private fun isStrong(pass: String): Boolean {
        var haveSymbol = false
        var haveUpper = false
        var haveLower = false
        var haveNumbs = false

        for (ch in pass) {
            when (ch) {
                in symbols -> haveSymbol = true
                in upperLetters -> haveUpper = true
                in lowerLetters -> haveLower = true
                in numbs -> haveNumbs = true
            }
        }
        return haveSymbol && haveUpper && haveLower && haveNumbs
    }
}
