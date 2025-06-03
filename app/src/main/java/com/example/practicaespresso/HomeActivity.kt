package com.example.practicaespresso

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Activity displayed after a successful user login.
 * This activity welcomes the user by showing their name.
 */
class HomeActivity : AppCompatActivity() {

    // Late-initialized variable for the welcome message TextView.
    private lateinit var labelBienvenida: TextView

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in [onSaveInstanceState]. Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enables edge-to-edge display.
        setContentView(R.layout.activity_home)

        // Apply window insets to adjust padding for system bars.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the TextView by finding its ID in the layout.
        labelBienvenida = findViewById(R.id.labelBienvenida)

        // Retrieve the user's name passed from the previous activity (MainActivity).
        val userName = intent.getStringExtra(MainActivity.NAME_USER)

        if (userName.isNullOrEmpty()) {
            // Redirige al login si no hay nombre
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
        labelBienvenida.text = getString(R.string.txtLabelBienvenido, userName)
    }
}