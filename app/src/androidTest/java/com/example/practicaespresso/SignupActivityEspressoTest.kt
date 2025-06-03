package com.example.practicaespresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Clase de pruebas de UI para la SignupActivity (pantalla de Registro).
 */
@RunWith(AndroidJUnit4::class)
class SignupActivityEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(SignupActivity::class.java)

    //Pruena para verificar que el campo edad acepte solo números
    //NOTA: Actualmente esta como text por lo que fallara la prueba
    @Test
    fun testAgeInputOnlyAcceptsNumbers() {
        onView(withId(R.id.inputAgeSignup)).perform(typeText("abc123"))
        onView(withId(R.id.inputAgeSignup)).check(matches(withText("123")))
    }

    @Test
    fun validDataRegistersUser() {
        onView(withId(R.id.inputNameSignup)).perform(typeText("Joel"), closeSoftKeyboard())
        onView(withId(R.id.inputAgeSignup)).perform(typeText("25"), closeSoftKeyboard())
        onView(withId(R.id.inputEmailSignup)).perform(typeText("joel@example.com"), closeSoftKeyboard())
        onView(withId(R.id.inputPasswordSignup)).perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.btnCreateAccount)).perform(click())

        // Comprueba que el usuario fue agregado al DataManager
        assert(DataManager.users.any { it.email == "joel@example.com" })
    }
}