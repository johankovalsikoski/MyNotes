package com.kovalsikoski.johan.mynotes

import org.junit.Test

import org.junit.Assert.*

class SignupActivityTest {

    @Test
    fun isValidEmailPatternReturnTrueWhenEmailPatternIsCorrect() {
        val signupActivity = SignupActivity()

        val input = "johan.kovalsikoski@gmail.com"
        val output = signupActivity.isValidEmailPattern(input)
        assertTrue(output)
    }

    @Test
    fun isValidEmailPatternReturnFalseCauseHasNoDomain(){
        val signupActivity = SignupActivity()

        val input = "johan.kovalsikoskigmail.com"
        val output = signupActivity.isValidEmailPattern(input)
        assertFalse(output)
    }
}