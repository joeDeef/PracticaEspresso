package com.example.practicaespresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.RootMatchers.withDecorView

/**
 * Clase de pruebas de UI para la SignupActivity (pantalla de Registro).
 */
@RunWith(AndroidJUnit4::class)
class SignupActivityEspressoTest {

    // Esta regla lanza la SignupActivity antes de cada método de prueba.
    // Usamos MainActivity.class para la regla principal si queremos probar la navegación desde ella
    // pero para probar solo SignupActivity aisladamente, lanzamos SignupActivity directamente.
    @get:Rule
    val activityRule = ActivityScenarioRule(SignupActivity::class.java)

    /**
     * Resetea el DataManager antes de cada prueba para asegurar un estado inicial limpio.
     */
    @Before
    fun setup() {
        // Limpiamos los usuarios existentes antes de cada prueba de registro.
        // Esto es crucial para asegurar que el registro sea siempre de un nuevo usuario.
        DataManager.users.clear()
        // Opcional: Puedes añadir usuarios existentes si necesitas probar casos donde un email ya está registrado,
        // pero tu lógica actual no lo prohíbe, así que no es necesario aquí.
    }

    // --- Pruebas de verificación de elementos iniciales de la UI ---

    @Test
    fun testSignupUIElementsAreDisplayed() {
        // Verifica que el título "Registrarse" es visible.
        onView(withId(R.id.labelCrearCuenta)).check(matches(withText(R.string.textBtnSignup)))
        onView(withId(R.id.labelCrearCuenta)).check(matches(isDisplayed()))

        // Verifica que los campos de Nombre, Edad, Email y Contraseña y sus etiquetas son visibles.
        onView(withId(R.id.labelNameSignup)).check(matches(withText(R.string.txtLabelName)))
        onView(withId(R.id.inputNameSingup)).check(matches(isDisplayed()))

        onView(withId(R.id.labelAgeSignup)).check(matches(withText(R.string.txtLabelAge)))
        onView(withId(R.id.inputAgeSignup)).check(matches(isDisplayed()))

        onView(withId(R.id.labelEmailSignup)).check(matches(withText(R.string.textLabelEmail)))
        onView(withId(R.id.inputEmailSignup)).check(matches(isDisplayed()))

        onView(withId(R.id.labelPasswordSignup)).check(matches(withText(R.string.textLabelPassword)))
        onView(withId(R.id.inputPasswordSignup)).check(matches(isDisplayed()))

        // Verifica que el botón "Crear Cuenta" es visible y tiene el texto correcto.
        onView(withId(R.id.btnCreateAccount)).check(matches(withText(R.string.textBtnSignup)))
        onView(withId(R.id.btnCreateAccount)).check(matches(isDisplayed()))
    }
}