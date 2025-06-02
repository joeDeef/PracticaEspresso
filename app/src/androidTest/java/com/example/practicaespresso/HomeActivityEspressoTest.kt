package com.example.practicaespresso

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Clase de pruebas de UI para la HomeActivity.
 */
@RunWith(AndroidJUnit4::class)
class HomeActivityEspressoTest {

    // No usamos DataManager.users.clear() aquí porque HomeActivity solo lee datos pasados por Intent,
    // no interactúa con DataManager directamente.

    @Test
    fun testWelcomeMessage_withUserName() {
        // 1. Crear un Intent para lanzar HomeActivity y pasar el nombre de usuario.
        // Simulamos el Intent que MainActivity crearía al iniciar sesión.
        val intent = Intent(ApplicationProvider.getApplicationContext(), HomeActivity::class.java).apply {
            putExtra(MainActivity.NAME_USER, "Alice") // Pasamos el nombre de usuario "Alice"
        }

        // 2. Lanzar HomeActivity con el Intent preparado.
        // Usamos una ActivityScenarioRule específica para este test con el Intent.
        val activityRule = ActivityScenarioRule<HomeActivity>(intent)
        activityRule.scenario // Necesario para que la regla se inicialice

        // 3. Verificar que el TextView de bienvenida muestra el nombre correcto.
        onView(withId(R.id.labelBienvenida))
            .check(matches(withText("Bienvenido, Alice!"))) // Verifica el texto esperado
            .check(matches(isDisplayed())) // Verifica que es visible
    }

    @Test
    fun testWelcomeMessage_withoutUserName() {
        // 1. Crear un Intent para lanzar HomeActivity SIN pasar un nombre de usuario.
        val intent = Intent(ApplicationProvider.getApplicationContext(), HomeActivity::class.java)

        // 2. Lanzar HomeActivity con el Intent (sin nombre).
        val activityRule = ActivityScenarioRule<HomeActivity>(intent)
        activityRule.scenario // Necesario para que la regla se inicialice

        // 3. Verificar que el TextView de bienvenida muestra el mensaje genérico (sin nombre).
        // Se espera "Bienvenido, !" porque así lo define getString(R.string.txtLabelBienvenido, "")
        onView(withId(R.id.labelBienvenida))
            .check(matches(withText("Bienvenido, !")))
            .check(matches(isDisplayed()))
    }
}