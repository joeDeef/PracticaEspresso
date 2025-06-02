package com.example.practicaespresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import android.view.View // ¡Importante! Importar la clase View

/**
 * Clase de pruebas de UI para la MainActivity (pantalla de Login).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    // Esta regla lanza la MainActivity antes de cada método de prueba.
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Resetea el DataManager antes de cada prueba. Esto es crucial
     * para asegurar un estado inicial conocido y que las pruebas sean independientes.
     */
    @Before
    fun setup() {
        // Limpiamos los usuarios existentes y añadimos los datos de prueba iniciales.
        DataManager.users.clear()
        DataManager.users.add(User("John Doe", 30, "john@example.com", "password123"))
        DataManager.users.add(User("Jane Smith", 25, "jane@example.com", "securepass"))
        DataManager.users.add(User("Joe Def", 21, "joe.def@gmail.com", "admin123"))
    }

    // --- Pruebas de verificación de elementos iniciales de la UI ---

    @Test
    fun testUIElementsAreDisplayed() {
        // Verifica que el título "Login" es visible.
        onView(withId(R.id.textView4)).check(matches(withText(R.string.txtLabelLogin)))
        onView(withId(R.id.textView4)).check(matches(isDisplayed()))

        // Verifica que los campos de email y contraseña y sus etiquetas son visibles.
        onView(withId(R.id.labelEmailLogin)).check(matches(withText(R.string.textLabelEmail)))
        onView(withId(R.id.inputEmailLogin)).check(matches(isDisplayed()))
        onView(withId(R.id.labelPasswordLogin)).check(matches(withText(R.string.textLabelPassword)))
        onView(withId(R.id.inputPasswordLogin)).check(matches(isDisplayed()))

        // Verifica que los botones de Login y Signup son visibles y tienen el texto correcto.
        onView(withId(R.id.btnLogin)).check(matches(withText(R.string.textBtnLogin)))
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSignup)).check(matches(withText(R.string.textBtnSignup)))
        onView(withId(R.id.btnSignup)).check(matches(isDisplayed()))
    }

    // --- Pruebas de Inicio de Sesión ---

    @Test
    fun testLoginExitoso_navegaAHomeActivityConNombreCorrecto() {
        // 1. Ingresar credenciales válidas.
        onView(withId(R.id.inputEmailLogin)).perform(typeText("john@example.com"), closeSoftKeyboard())
        onView(withId(R.id.inputPasswordLogin)).perform(typeText("password123"), closeSoftKeyboard())

        // 2. Hacer clic en el botón de login.
        onView(withId(R.id.btnLogin)).perform(click())

        // 3. Verificar que se navega a HomeActivity y el mensaje de bienvenida es correcto.
        // Se busca el TextView con el ID R.id.labelBienvenida (que está en HomeActivity)
        // y se verifica que su texto sea "Bienvenido, John Doe!".
        onView(withId(R.id.labelBienvenida))
            .check(matches(withText("Bienvenido, John Doe!")))
            .check(matches(isDisplayed()))
    }

    // --- Pruebas de Validación de Campos Vacíos ---

    @Test
    fun testLoginFallido_emailVacio_muestraErrorEnEmail() {
        // 1. Dejar el campo de email vacío y solo ingresar la contraseña.
        onView(withId(R.id.inputPasswordLogin)).perform(typeText("password123"), closeSoftKeyboard())

        // 2. Hacer clic en el botón de login.
        onView(withId(R.id.btnLogin)).perform(click())

        // 3. Verificar que se muestra un error en el campo de email.
        onView(withId(R.id.inputEmailLogin))
            .check(matches(hasErrorText("El correo es requerido")))
    }

    @Test
    fun testLoginFallido_passwordVacia_muestraErrorEnPassword() {
        // 1. Dejar el campo de password vacío y solo ingresar el email.
        onView(withId(R.id.inputEmailLogin)).perform(typeText("john@example.com"), closeSoftKeyboard())

        // 2. Hacer clic en el botón de login.
        onView(withId(R.id.btnLogin)).perform(click())

        // 3. Verificar que se muestra un error en el campo de password.
        onView(withId(R.id.inputPasswordLogin))
            .check(matches(hasErrorText("La contraseña es requerida")))
    }

    @Test
    fun testLoginFallido_ambosCamposVacios_muestraErrorEnEmail() {
        // 1. Dejar ambos campos vacíos.
        onView(withId(R.id.inputEmailLogin)).perform(clearText(), closeSoftKeyboard())
        onView(withId(R.id.inputPasswordLogin)).perform(clearText(), closeSoftKeyboard())

        // 2. Hacer clic en el botón de login.
        onView(withId(R.id.btnLogin)).perform(click())

        // 3. Verificar que se muestra el error en el campo de email (primera validación).
        onView(withId(R.id.inputEmailLogin))
            .check(matches(hasErrorText("El correo es requerido")))
        // Opcional: Asegurarse de que el campo de contraseña aún no tiene error si el email es el foco.
        onView(withId(R.id.inputPasswordLogin))
            .check(matches(not(hasErrorText("La contraseña es requerida"))))
    }

    // --- Prueba de Navegación ---

    @Test
    fun testNavegacionASignupActivity() {
        // 1. Hacer clic en el botón para crear nuevo usuario.
        onView(withId(R.id.btnSignup)).perform(click())

        // 2. Verificar que se ha navegado a SignupActivity buscando un elemento distintivo de esa pantalla.
        // En este caso, el botón de "Crear Cuenta" (btnCreateAccount) que solo existe en SignupActivity.
        onView(withId(R.id.btnCreateAccount))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.textBtnSignup))) // Verifica también su texto.
    }
}