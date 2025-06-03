package com.example.practicaespresso

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard

@RunWith(AndroidJUnit4::class)
class FullFlowTest {

    @Before
    fun setup() {
        Intents.init()
        // Limpiamos usuarios para evitar interferencias
        DataManager.users.clear()
        ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun createAccount_thenLogin_thenEnterHome() {
        val name = "Joel"
        val age = "28"
        val email = "joel@example.com"
        val password = "admin12345"

        // 1. En Login, presionar botón Crear cuenta
        onView(withId(R.id.btnSignup)).perform(click())

        // 2. En SignupActivity, completar datos
        onView(withId(R.id.inputNameSignup))
            .perform(typeText(name), closeSoftKeyboard())

        onView(withId(R.id.inputAgeSignup))
            .perform(typeText(age), closeSoftKeyboard())

        onView(withId(R.id.inputEmailSignup))
            .perform(typeText(email), closeSoftKeyboard())

        onView(withId(R.id.inputPasswordSignup))
            .perform(typeText(password), closeSoftKeyboard())

        // 3. Presionar botón crear cuenta
        onView(withId(R.id.btnCreateAccount)).perform(click())

        // 4. Ingresar email y contraseña para login
        onView(withId(R.id.inputEmailLogin))
            .perform(typeText(email), closeSoftKeyboard())

        onView(withId(R.id.inputPasswordLogin))
            .perform(typeText(password), closeSoftKeyboard())

        // 5. Presionar botón login
        onView(withId(R.id.btnLogin)).perform(click())

        // 6. Verificar que HomeActivity fue lanzada
        Intents.intended(hasComponent(HomeActivity::class.java.name))

        // 7. Verificar que texto bienvenida contiene el nombre
        onView(withId(R.id.labelBienvenida))
            .check(matches(withText(containsString(name))))
    }
}
