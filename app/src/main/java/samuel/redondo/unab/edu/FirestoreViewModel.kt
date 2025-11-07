package samuel.redondo.unab.edu


import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



class FirestoreViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val productosRef = db.collection("productos")

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        productosRef.get()
            .addOnSuccessListener { result ->
                val lista = result.map { doc ->
                    doc.toObject(Producto::class.java).copy(id = doc.id)
                }
                _productos.value = lista
            }
            .addOnFailureListener {
                // Puedes manejar el error aquí
            }
    }

    fun agregarProducto(nombre: String, descripcion: String, precio: Double) {
        val nuevo = Producto(nombre = nombre, descripcion = descripcion, precio = precio)
        productosRef.add(nuevo)
            .addOnSuccessListener {
                cargarProductos()
            }
    }

    fun eliminarProducto(id: String) {
        productosRef.document(id).delete()
            .addOnSuccessListener {
                cargarProductos()
            }
    }
}

