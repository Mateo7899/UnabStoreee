package samuel.redondo.unab.edu

import android.util.Patterns
import androidx.collection.emptyLongSet

fun validateEmail(email: String): Pair<Boolean, String> {
    return when {
        email.isEmpty() -> Pair(false, "El correo es requerido.")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair(false, "El correo es invalido")
        !email.endsWith("@test.com") -> Pair(false, "Ese email no es corporativo")
        else -> Pair(true, "")
    }
}

fun validatePassword(password:String): Pair<Boolean, String>{
    return when{
        password.isEmpty() -> Pair(false, "La contraseña es requerida. ")
        password.length < 8 -> Pair(false, "La contraseña debe tener al menos 8 caracteres. ")
        !password.any {it.isDigit()} -> Pair(false, "La contraseña debe tener al menos un numero. ")
            else -> Pair(true,"")
    }
}

fun validateName (name : String): Pair<Boolean, String>{
    return when{
        name.isEmpty() -> Pair(false, "El nombre es requerido")
        name.length < 3 -> Pair(false, "El nombre debe tener al menso 3 caracteres.")
        else -> Pair(true,"")
    }
}