package com.example.apptrabalhoandroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.apptrabalhoandroid.databinding.ActivityTelaTrocoBinding

class TelaTroco : AppCompatActivity() {
    private lateinit var binding: ActivityTelaTrocoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTelaTrocoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val resultado = intent.getStringExtra("Resultado")
        binding.ResultadoAnterior.text = resultado
        verificandoResultado(resultado)

        binding.btnTrocoVoltar.setOnClickListener {
            voltar()
        }

        binding.btnConfig.setOnClickListener {
            replaceFragment(FragmentConfig())
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.FragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    @SuppressLint("SetTextI18n")
    private fun verificandoResultado(resultado: String?) {
        var (i100, i50, i20, i10, i5) = Array(5) { 0 }
        var (ic100, ic50, ic25, ic10, ic5) = Array(5) { 0 }
        var resultadofloat = resultado?.toFloat()

        while (resultadofloat!! >= 0.05) {
            if (resultadofloat >= 100) {
                binding.Nota100.visibility = View.VISIBLE
                i100++
                val texto = binding.Nota100.getChildAt(1)
                if (texto is TextView) {
                    texto.text = "X${i100}"
                }
                resultadofloat = resultadofloat.minus(100)
            } else if (resultadofloat >= 50) {
                binding.Nota50.visibility = View.VISIBLE
                i50++
                val texto = binding.Nota50.getChildAt(1)
                if (texto is TextView) {
                    texto.text = "X${i50}"
                }
                resultadofloat = resultadofloat.minus(50)
            } else if (resultadofloat >= 25) {
                binding.Nota20.visibility = View.VISIBLE
                i20++
                val texto = binding.Nota20.getChildAt(1)
                if (texto is TextView) {
                    texto.text = "X${i20}"
                }
                resultadofloat = resultadofloat.minus(20)
            } else if (resultadofloat >= 10) {
                binding.Nota10.visibility = View.VISIBLE
                i10++
                val texto = binding.Nota10.getChildAt(1)
                if (texto is TextView) {
                    texto.text = "X${i10}"
                }
                resultadofloat = resultadofloat.minus(10)
            } else if (resultadofloat >= 5) {
                binding.Nota5.visibility = View.VISIBLE
                i5++
                val texto = binding.Nota5.getChildAt(1)
                if (texto is TextView) {
                    texto.text = "X${i5}"
                }
                resultadofloat = resultadofloat.minus(5)
            } else if (resultadofloat >= 1) {
                binding.Moeda100.visibility = View.VISIBLE
                ic100++
                val texto = binding.Moeda100.getChildAt(1)
                if (texto is TextView) {
                    texto.text = "X${ic100}"
                }
                resultadofloat = resultadofloat.minus(1)
            } else if (resultadofloat >= 0.5) {
                binding.Moeda50.visibility = View.VISIBLE
                ic50++
                val texto = binding.Moeda50.getChildAt(1)
                if (texto is TextView) {
                    texto.text = "X${ic50}"
                }
                resultadofloat = resultadofloat.minus(0.5F)
            } else if (resultadofloat >= 0.25) {
                binding.Moeda25.visibility = View.VISIBLE
                ic25++
                val texto = binding.Moeda25.getChildAt(1)
                if (texto is TextView) {
                    texto.text = "X${ic25}"
                }
                resultadofloat = resultadofloat.minus(0.25F)
            } else if (resultadofloat >= 0.10) {
                binding.Moeda10.visibility = View.VISIBLE
                ic10++
                val texto = binding.Moeda10.getChildAt(1)
                if (texto is TextView) {
                    texto.text = "X${ic10}"
                }
                resultadofloat = resultadofloat.minus(0.1F)
            } else if (resultadofloat >= 0.05) {
                binding.Moeda5.visibility = View.VISIBLE
                ic5++
                val texto = binding.Moeda5.getChildAt(1)
                if (texto is TextView) {
                    texto.text = "X${ic5}"
                }
                resultadofloat = resultadofloat.minus(0.05F)
            }
        }
    }
    private fun voltar(){
        finish()
    }
}

