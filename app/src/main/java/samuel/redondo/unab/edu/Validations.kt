package samuel.redondo.unab.edu

fun ValidateEmail(email: String): Pair<Boolean, String>{
return when{email.isEmpty() -> Pair(false, "El correo es requerido.")
email.endsWith("@test.com")->Pair(false, "Ese email no es corporativo."