package com.example.apptrabalhoandroid

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi

class ManterBroadCast : Service() {

    private lateinit var receiver: TransacaoReceiver

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate() {
        super.onCreate()
        // Inicializa o BroadcastReceiver
        receiver = TransacaoReceiver()

        // Registra o BroadcastReceiver programaticamente
        val filter = IntentFilter("com.exemplo.TRANSACAO_CONCLUIDA")
        registerReceiver(receiver, filter, RECEIVER_EXPORTED)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Desregistra o BroadcastReceiver para evitar vazamentos de memória
        unregisterReceiver(receiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        // Este serviço não suporta binding, então retornamos null
        return null
    }
}