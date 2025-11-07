package samuel.redondo.unab.edu

import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.auth
import samuel.redondo.unab.edu.ui.theme.UnabStoreeeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Inicializa Firebase antes de usarlo
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            UnabStoreeeTheme {
                val navController = rememberNavController()

                // ✅ Detecta si el usuario está logueado
                val currentUser = Firebase.auth.currentUser
                val startDestination = if (currentUser != null) "home" else "login"

                // ✅ Configura las rutas principales
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(route = "login") {
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

                    composable(route = "register") {
                        RegisterScreen(
                            onClickBack = { navController.popBackStack() },
                            onSuccessfulRegister = {
                                navController.navigate("home") {
                                    popUpTo(0)
                                }
                            }
                        )
                    }

                    composable(route = "home") {
                        HomeScreen(
                            onClickLogout = {
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

