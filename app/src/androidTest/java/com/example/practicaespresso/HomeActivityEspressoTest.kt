package com.example.practicaespresso

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityIntentTest {

    //Prueba para verificar que se pasa el contexto desde el login(main activity)
    @Test
    fun testUserNameIsDisplayedFromIntent() {
        // Preparar intent con el nombre de usuario
        val intent = Intent(
            androidx.test.core.app.ApplicationProvider.getApplicationContext(),
            HomeActivity::class.java
        ).apply {
            putExtra(MainActivity.NAME_USER, "Joel")
        }

        // Lanzar la actividad con el intent
        ActivityScenario.launch<HomeActivity>(intent)

        // Verificar que el mensaje de bienvenida es correcto
        onView(withId(R.id.labelBienvenida))
            .check(matches(withText("Bienvenid@,\nJoel")))
    }

    //Prueba para evitar ingresar directamente al home sin pasar por el login
    @Test
    fun testRedirectToLoginWhenNoUsername() {
        // Lanzamos HomeActivity sin pasar extras (sin userName)
        ActivityScenario.launch(HomeActivity::class.java)

        // Comprobamos que el botón de login de MainActivity está visible,
        // lo que indica que redirigió correctamente al login
        onView(withId(R.id.btnLogin))
            .check(matches(isDisplayed()))
    }

    //Prueba para validar que no se crashee la pantalla con nombres largos
    @Test
    fun testVeryLongNameDisplayedCorrectly() {
        val longName = "Joel " + "ApellidosLargos".repeat(10)
        val intent = Intent(ApplicationProvider.getApplicationContext(), HomeActivity::class.java).apply {
            putExtra(MainActivity.NAME_USER, longName)
        }

        ActivityScenario.launch<HomeActivity>(intent)

        onView(withId(R.id.labelBienvenida))
            .check(matches(withText("Bienvenid@\n$longName")))
    }

    //Prueba para validar que todo funciona igual con la rotación de pantalla
    @Test
    fun testWelcomeMessagePersistsOnRotation() {
        val userName = "Joel"
        val intent = Intent(ApplicationProvider.getApplicationContext(), HomeActivity::class.java).apply {
            putExtra(MainActivity.NAME_USER, userName)
        }

        val scenario = ActivityScenario.launch<HomeActivity>(intent)

        onView(withId(R.id.labelBienvenida))
            .check(matches(withText("Bienvenid@\n$userName")))

        // Rota la pantalla (de portrait a landscape)
        scenario.onActivity { activity ->
            activity.requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // Espera y comprueba que el texto sigue igual
        onView(withId(R.id.labelBienvenida))
            .check(matches(withText("Bienvenid@\n$userName")))
    }

}