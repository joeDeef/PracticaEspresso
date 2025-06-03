package com.example.practicaespresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.Intents.intended
import org.junit.After
import org.junit.Before

/**
 * Clase de pruebas de UI para la MainActivity (pantalla de Login).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    //Prueba para validar que se carguen todos los elementos
    @Test
    fun testLoginScreenElementsAreDisplayed() {
        // Verifica que el campo de email esté visible
        onView(withId(R.id.inputEmailLogin)).check(matches(isDisplayed()))

        // Verifica que el campo de contraseña esté visible
        onView(withId(R.id.inputPasswordLogin)).check(matches(isDisplayed()))

        // Verifica que el botón de login esté visible
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))

        // Verifica que el botón de registro esté visible
        onView(withId(R.id.btnSignup)).check(matches(isDisplayed()))
    }

    //Prueba para validar que no se permita valores vacios en el campo email
    @Test
    fun testLoginShowsErrorIfFieldsEmpty() {
        // Intenta hacer click en el botón Login sin llenar los campos
        onView(withId(R.id.btnLogin)).perform(click())

        // Opcional: comprueba el error en el email también
        onView(withId(R.id.inputEmailLogin)).check(matches(hasErrorText("El correo es requerido")))
    }

    //Prueba para validar que no se deje en blanco la contraseña
    @Test
    fun testLoginShowsErrorIfPasswordEmpty() {
        // Ingresa un correo válido en el EditText del email
        onView(withId(R.id.inputEmailLogin))
            .perform(typeText("usuario@example.com"), closeSoftKeyboard())

        // Click en el botón Login
        onView(withId(R.id.btnLogin)).perform(click())

        // Verifica que el email NO tiene error
        onView(withId(R.id.inputEmailLogin)).check(matches(not(hasErrorText("El correo es requerido"))))

        // Verifica que la contraseña SÍ muestra el error esperado
        onView(withId(R.id.inputPasswordLogin)).check(matches(hasErrorText("La contraseña es requerida")))
    }

    //Prueba de navegavilidad entre pantallas de la login a la home
    @Test
    fun testLoginSuccessNavigatesToHome() {
        // Suponiendo que DataManager.users contiene un usuario así:
        val validEmail = "jane@example.com"
        val validPassword = "securepass"

        // Escribe email y contraseña válidos
        onView(withId(R.id.inputEmailLogin))
            .perform(typeText(validEmail), closeSoftKeyboard())
        onView(withId(R.id.inputPasswordLogin))
            .perform(typeText(validPassword), closeSoftKeyboard())

        // Click en login
        onView(withId(R.id.btnLogin)).perform(click())

        // Verificar que la siguiente activity se abre
        // Para esto puedes usar IntentsTestRule o Intents.init() para interceptar intents

        // Inicializa la captura de intents
        Intents.init()

        // Verifica que se lanzó un Intent a HomeActivity
        intended(hasComponent(HomeActivity::class.java.name))

        // Libera los recursos de Intents
        Intents.release()
    }
}