package forum

import org.jetbrains.exposed.sql.Database
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
open class App

fun main() {
    try {
        Database.connect("jdbc:postgresql://localhost:5432/forum", driver = "org.postgresql.Driver",
                user = "postgres", password = "root")
        println("Connection successful!")
    }
    catch (e: Exception) {
        println("Connection failed!")
    }
    runApplication<App>()
}
