package com.example.apptrabalhoandroid

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.Date
import java.util.Locale

class TransacaoReceiver : BroadcastReceiver() {
    private companion object{
        private const val CHANNEL_ID = "channel01"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        val valorPago = intent?.getFloatExtra("valor_pago", 0F)
        val valorTotal = intent?.getFloatExtra("valor_total", 0F)

        // Processa os valores recebidos
        val troco = valorTotal?.let { valorPago?.minus(it) }
        if (context != null) {
            showNotification(context, troco.toString())
        }
    }

    private fun showNotification(context: Context, troco: String){

        val date = Date()
        val notificationId = SimpleDateFormat("ddHHss", Locale.US).format(date).toInt()

        val mainIntent = Intent(context, TelaTroco::class.java)
        mainIntent.putExtra("Resultado", troco)
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val mainPendingIntent = PendingIntent.getActivity(context, 1, mainIntent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)

        notificationBuilder.setContentTitle("Aqui Esta A Quantidade de Troco")

        notificationBuilder.setContentText("A Quantidade de Troco é $troco")

        notificationBuilder.priority = NotificationCompat.PRIORITY_DEFAULT
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setContentIntent(mainPendingIntent)

        val notficationMannagerCompact = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notficationMannagerCompact.notify(notificationId, notificationBuilder.build())
    }


}
