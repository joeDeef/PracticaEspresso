package com.example.practicaespresso

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Activity for user registration (signup).
 * This activity allows users to create a new account by providing their name, age, email, and password.
 */
class SignupActivity : AppCompatActivity() {

    // Late-initialized variables for UI input fields and button.
    private lateinit var inputName: EditText
    private lateinit var inputAge: EditText
    private lateinit var inputEmailSignup: EditText
    private lateinit var inputPasswordSignup: EditText
    private lateinit var btnRegister: Button

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in [onSaveInstanceState]. Otherwise it is null.
     */
    @SuppressLint("MissingInflatedId") // Suppress this lint warning if IDs are correctly referenced.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enables edge-to-edge display for a more immersive experience.
        setContentView(R.layout.activity_signup) // Ensure activity_signup.xml layout exists.

        // Apply window insets to adjust padding for system bars (status bar, navigation bar).
        // The root layout ID (R.id.main or R.id.signup_main) should match your actual layout file.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI elements by finding their IDs in the layout.
        inputName = findViewById(R.id.inputNameSingup)
        inputAge = findViewById(R.id.inputAgeSignup)
        inputEmailSignup = findViewById(R.id.inputEmailSignup)
        inputPasswordSignup = findViewById(R.id.inputPasswordSignup)
        btnRegister = findViewById(R.id.btnCreateAccount)

        // Set up the click listener for the register button.
        setupRegisterButtonListener()
    }

    /**
     * Sets up the click listener for the register button.
     * This method handles user input validation and user registration.
     */
    private fun setupRegisterButtonListener() {
        btnRegister.setOnClickListener {
            // Retrieve text input from EditText fields.
            val name = inputName.text.toString()
            val ageString = inputAge.text.toString()
            val email = inputEmailSignup.text.toString()
            val password = inputPasswordSignup.text.toString()

            // Validate that all fields are filled.
            if (name.isEmpty() || ageString.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop execution if any field is empty.
            }

            // Attempt to convert age string to an integer and validate its value.
            val age = ageString.toIntOrNull()
            if (age == null || age <= 0) {
                Toast.makeText(this, "Ingrese una edad válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop execution if age is invalid.
            }

            // Create a new User object with the provided valid data.
            val newUser = User(name, age, email, password)

            // Add the newly created user to the in-memory DataManager.
            // In a real application, this would typically involve saving to a database or server.
            DataManager.users.add(newUser)
            Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()

            // After successful registration, finish this activity to return to the previous one
            // (likely the MainActivity/login screen).
            finish()
        }
    }
}