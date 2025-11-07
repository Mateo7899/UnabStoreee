package samuel.redondo.unab.edu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import samuel.redondo.unab.edu.ui.theme.UnabStoreeeTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Inicializa Firebase antes de usarlo
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            UnabStoreeeTheme {
                // ✅ Se crea el NavController dentro del contexto composable
                val navController = rememberNavController()

                // ✅ Autenticación Firebase
                val auth = Firebase.auth
                val currentUser = auth.currentUser

                // ✅ Definir pantalla inicial según sesión
                val startDestination = if (currentUser != null) "home" else "login"

                // ✅ Estructura de navegación
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // 🔹 Pantalla de Login
                    composable("login") {
                        LoginScreen(
                            onClickRegister = {
                                navController.navigate("register")
                            },
                            onSuccessfullLogin = {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 🔹 Pantalla de Registro
                    composable("register") {
                        RegisterScreen(
                            onClickBack = {
                                navController.popBackStack()
                            },
                            onSuccessfulRegister = {
                                navController.navigate("home") {
                                    popUpTo(0)
                                }
                            }
                        )
                    }

                    // 🔹 Pantalla Principal (Home)
                    composable("home") {
                        HomeScreen(
                            onClickLogout = {
                                // Cerrar sesión y volver a login
                                Firebase.auth.signOut()
                                navController.navigate("login") {
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


