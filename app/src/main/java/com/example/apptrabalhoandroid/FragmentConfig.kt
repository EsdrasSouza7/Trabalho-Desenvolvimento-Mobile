package com.example.apptrabalhoandroid

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentConfig: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout para este fragmento
        return inflater.inflate(R.layout.fragmentconfiguracoes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Exibe a configuração ou mensagem
        view.findViewById<TextView>(R.id.textViewConfig).text = "Teste Fragmento"


        // Remove o fragmento após 3 segundos (3000 milissegundos)
        Handler(Looper.getMainLooper()).postDelayed({
            // Verifica se o fragmento ainda está anexado à Activity antes de removê-lo
            if (isAdded) {
                parentFragmentManager.beginTransaction().remove(this).commit()
            }
        }, 5000) // 3000 ms = 3 segundos
    }
}