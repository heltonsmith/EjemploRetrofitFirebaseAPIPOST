package com.heltonbustos.ejemploretrofitfirebaseapi01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.heltonbustos.ejemploretrofitfirebaseapi01.databinding.ActivityMainBinding
import com.heltonbustos.ejemploretrofitfirebaseapi01.retrofit.Pelicula
import com.heltonbustos.ejemploretrofitfirebaseapi01.retrofit.PeliculaAPIService
import com.heltonbustos.ejemploretrofitfirebaseapi01.retrofit.PeliculaRespuestaAPI
import com.heltonbustos.ejemploretrofitfirebaseapi01.retrofit.RestEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener {

            if( binding.txtAvaluo.text.toString() == "" ||
                binding.txtFechaLanzamiento.text.toString() == "" ||
                binding.txtLugarEstreno.text.toString() == "" ||
                binding.txtNombre.text.toString() == ""){
                Toast.makeText(applicationContext,"Complete todos los campos",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                binding.progressBar.visibility = View.VISIBLE
                var pelicula = Pelicula(
                    binding.txtAvaluo.text.toString(),
                    binding.txtFechaLanzamiento.text.toString(),
                    binding.txtLugarEstreno.text.toString(),
                    binding.txtNombre.text.toString()
                )

                guardarPelicula(pelicula)
            }

        }
    }

    private fun guardarPelicula(pelicula: Pelicula) {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada: PeliculaAPIService = RestEngine.getRestEngine().create(PeliculaAPIService::class.java)
            val resultado: Call<PeliculaRespuestaAPI> = llamada.agregarPelicula(pelicula)
            val p:PeliculaRespuestaAPI? = resultado.execute().body()

            if(p != null){
                runOnUiThread {
                    binding.txtId.text = "Id agregado correctamente: ${p.name}"
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}