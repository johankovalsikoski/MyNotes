package com.kovalsikoski.johan.mynotes

import org.junit.Test

import org.junit.Assert.*

class LoginActivityTest {

    @Test
    fun isValidEmailPatternReturnTrueWhenEmailPatternIsCorrect() {
        val loginActivity = LoginActivity()

        val input = "johan.kovalsikoski@gmail.com"
        val output = loginActivity.isValidEmailPattern(input)
        assertTrue(output)
    }

    @Test
    fun isValidEmailPatternReturnFalseCauseHasNoDomain(){
        val loginActivity = LoginActivity()

        val input = "johan.kovalsikoskigmail.com"
        val output = loginActivity.isValidEmailPattern(input)
        assertFalse(output)
    }
}