package com.example.apptrabalhoandroid

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apptrabalhoandroid.databinding.ActivityTelaHistoricoBinding

class TelaHistorico : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var binding: ActivityTelaHistoricoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTelaHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Mudar a cor da barra de status
        window.statusBarColor = ContextCompat.getColor(this, R.color.statusBar)

        // Mudar a cor da barra de navegação
        window.navigationBarColor = ContextCompat.getColor(this, R.color.quasePreto)

        dbHelper = DBHelper(this)

        binding.btnHistoricoVoltar.setOnClickListener {
            finish()
        }

        updateUI()
    }
    private fun updateUI() {
        val historicos = dbHelper.getAllData()
        val resultado = StringBuilder()
        for (historico in historicos) {
            resultado.append("${historico.calculo} = ${historico.resposta}\n\n")
        }
        val textViewResultado = findViewById<TextView>(R.id.HistoricoResultado)
        textViewResultado.text = resultado.toString()
    }
}