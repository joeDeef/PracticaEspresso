package com.example.practicaespresso

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

        // Set the welcome message using the retrieved user name.
        if (userName != null) {
            // If the user name is available, display a personalized welcome message.
            labelBienvenida.text = getString(R.string.txtLabelBienvenido, userName)
        } else {
            // If the user name is not available (e.g., due to an unexpected state),
            // display a generic welcome message.
            labelBienvenida.text = getString(R.string.txtLabelBienvenido, "")
        }
    }
}