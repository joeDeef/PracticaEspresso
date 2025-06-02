import com.example.practicaespresso.User

/**
 * Singleton object responsible for managing user data.
 * This object holds a mutable list of [User] objects and initializes it with some dummy data.
 */
object DataManager {
    val users: MutableList<User> = mutableListOf()

    init {
        // Initialize with some dummy data for demonstration purposes.
        users.add(User("John Doe", 30, "john@example.com", "password123"))
        users.add(User("Jane Smith", 25, "jane@example.com", "securepass"))
        users.add(User("Joe Def", 21, "joe.def@gmail.com", "admin123"))
    }
}