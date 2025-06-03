package com.example.practicaespresso

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * The main activity of the application, responsible for user login and navigation to signup.
 */
class MainActivity : AppCompatActivity() {

    // Late-initialized variables for UI elements.
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonNewUser: Button

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in [onSaveInstanceState]. Otherwise it is null.
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enables edge-to-edge display for a more immersive experience.
        setContentView(R.layout.activity_main)

        // Apply window insets to adjust padding for system bars (status bar, navigation bar).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI elements by finding their IDs in the layout.
        editTextEmail = findViewById(R.id.inputEmailLogin)
        editTextPassword = findViewById(R.id.inputPasswordLogin)
        buttonLogin = findViewById(R.id.btnLogin)
        buttonNewUser = findViewById(R.id.btnSignup)

        var isPasswordVisible = false

        editTextPassword.setOnTouchListener { v, event ->
            if (event.rawX >= (editTextPassword.right - editTextPassword.compoundDrawables[2].bounds.width())) {
                isPasswordVisible = !isPasswordVisible

                if (isPasswordVisible) {
                    editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    editTextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0)
                } else {
                    editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    editTextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_closed, 0)
                }

                // Mueve el cursor al final
                editTextPassword.setSelection(editTextPassword.text.length)

                true // Evento manejado
            } else {
                false // Deja que el sistema maneje otros toques
            }
        }

        // Set up click listeners for the buttons.
        setupClickListeners()
    }

    /**
     * Sets up the click listeners for the login and new user buttons.
     */
    private fun setupClickListeners() {
        // Handle login button click.
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // Validate if email and password fields are filled.
            if (!validateRequiredData()) {
                return@setOnClickListener // If validation fails, stop further execution.
            }

            // Attempt to find and authenticate a user with the provided credentials.
            val authenticatedUser = findUser(email, password)
            if (authenticatedUser != null) {
                // If a user is found, navigate to the HomeActivity.
                val intent = Intent(this, HomeActivity::class.java)
                // Pass the authenticated user's name to the next activity.
                intent.putExtra(NAME_USER, authenticatedUser.name)
                startActivity(intent)
                finish() // Finish MainActivity so the user cannot go back to it from HomeActivity.
            } else {
                // If no user is found, display an invalid credentials toast message.
                Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle new user (signup) button click.
        buttonNewUser.setOnClickListener {
            // Navigate to the SignupActivity.
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            // Do not call finish() here, allowing the user to return to the login screen
            // after completing or canceling the signup process.
        }
    }

    /**
     * Validates if the email and password input fields are not empty.
     * Sets an error message on the respective EditText if a field is empty.
     *
     * @return `true` if both fields are filled, `false` otherwise.
     */
    private fun validateRequiredData(): Boolean {
        if (editTextEmail.text.isNullOrEmpty()) {
            editTextEmail.error = "El correo es requerido"
            return false
        }
        if (editTextPassword.text.isNullOrEmpty()) {
            editTextPassword.error = "La contraseña es requerida"
            return false
        }
        return true
    }

    /**
     * Finds a user in the [DataManager.users] list based on the provided email and password.
     *
     * @param email The email of the user to find.
     * @param password The password of the user to find.
     * @return The [User] object if a match is found, otherwise `null`.
     */
    private fun findUser(email: String, password: String): User? {
        return DataManager.users.find { user -> user.email == email && user.password == password }
    }

    companion object {
        // Constant for the extra key used to pass the user's name to other activities.
        const val NAME_USER = "name_user"
    }
}