package com.example.apptrabalhoandroid

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apptrabalhoandroid.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var binding: ActivityMainBinding
    private var calculoAtual: String = ""
    private var resultadoCalc: String = "0"
    private companion object{
        private const val CHANNEL_ID = "channel01"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val telaCalc: TextView = findViewById(R.id.Resultado)

        //Iniciando Service
        val intent = Intent(this, ManterBroadCast::class.java)
        startService(intent)

        //Iniciando Banco de Dados
        dbHelper = DBHelper(this)
        createNotificationChannel()

        // Mudar a cor da barra de status
        window.statusBarColor = ContextCompat.getColor(this, R.color.statusBar)

        // Mudar a cor da barra de navegação
        window.navigationBarColor = ContextCompat.getColor(this, R.color.quasePreto)

        fun updateTextView(){
            telaCalc.text = calculoAtual
        }
        fun mostrarResultado(){
            telaCalc.text = resultadoCalc
        }

        //Adicionando Funcionalidade aos Butões
        binding.btn1.setOnClickListener{
            calculoAtual += "1"
            updateTextView()
        }
        binding.btn2.setOnClickListener{
            calculoAtual += "2"
            updateTextView()
        }
        binding.btn3.setOnClickListener{
            calculoAtual += "3"
            updateTextView()
        }
        binding.btn4.setOnClickListener{
            calculoAtual += "4"
            updateTextView()
        }
        binding.btn5.setOnClickListener{
            calculoAtual += "5"
            updateTextView()
        }
        binding.btn6.setOnClickListener{
            calculoAtual += "6"
            updateTextView()
        }
        binding.btn7.setOnClickListener{
            calculoAtual += "7"
            updateTextView()
        }
        binding.btn8.setOnClickListener{
            calculoAtual += "8"
            updateTextView()
        }
        binding.btn9.setOnClickListener{
            calculoAtual += "9"
            updateTextView()
        }
        binding.btn0.setOnClickListener{
            calculoAtual += "0"
            updateTextView()
        }
        //Butões Calculo
        binding.btnSoma.setOnClickListener{
            if (calculoAtual.isNotEmpty()){
                val u = calculoAtual.last()
                if (u in listOf('-', '*', '/', '+' )){
                    if (calculoAtual.count() == 1){
                        calculoAtual = calculoAtual.dropLast(1)
                        updateTextView()
                    }else {
                        calculoAtual = calculoAtual.dropLast(1)
                        calculoAtual += "+"
                        updateTextView()
                        resultadoCalc = "0"
                    }
                }else{
                    calculoAtual += "+"
                    updateTextView()
                }
            }
        }
        binding.btnSubtracao.setOnClickListener{
            if (calculoAtual.isNotEmpty()){
                val u = calculoAtual.last()
                if (u in listOf('+', '*', '/', '-' )){
                    calculoAtual = calculoAtual.dropLast(1)
                    calculoAtual += "-"
                    updateTextView()
                }else{
                    calculoAtual += "-"
                    updateTextView()
                    resultadoCalc = "0"
                }
            }else{
                calculoAtual += "-"
                updateTextView()
            }
        }
        binding.btnMult.setOnClickListener{
            if (calculoAtual.isNotEmpty()){
                val u = calculoAtual.last()
                if (u in listOf('-', '+', '/', '*' )){
                    if (calculoAtual.count() == 1){
                        calculoAtual = calculoAtual.dropLast(1)
                        updateTextView()
                    }else {
                        calculoAtual = calculoAtual.dropLast(1)
                        calculoAtual += "*"
                        updateTextView()
                        resultadoCalc = "0"
                    }
                }else{
                    calculoAtual += "*"
                    updateTextView()
                }
            }
        }
        binding.btnDivisao.setOnClickListener{
            if (calculoAtual.isNotEmpty()){
                val u = calculoAtual.last()
                if (u in listOf('-', '*', '+','/' )){
                    if (calculoAtual.count() == 1){
                        calculoAtual = calculoAtual.dropLast(1)
                        updateTextView()
                    }else {
                        calculoAtual = calculoAtual.dropLast(1)
                        calculoAtual += "/"
                        updateTextView()
                        resultadoCalc = "0"
                    }
                }else{
                    calculoAtual += "/"
                    updateTextView()
                }
            }
        }
        //Outros Butoes
        binding.btnPonto.setOnClickListener{
            if (calculoAtual.isNotEmpty()){
                val u = calculoAtual.last()
                if (u in listOf('-', '*', '+', '/' )){
                    calculoAtual += "0."
                    updateTextView()
                }else{
                    val s = calculoAtual.length
                    if (calculoAtual[s-2] == '.'){
                        updateTextView()
                    }else if (u == '.'){
                        updateTextView()
                    }else {
                        calculoAtual += "."
                        updateTextView()
                        resultadoCalc = "0"
                    }
                }
            }else{
                calculoAtual += "0."
                updateTextView()
            }
        }
        binding.btnApagar.setOnClickListener{
            if (calculoAtual.isNotEmpty()) {
                calculoAtual = calculoAtual.dropLast(1)
                updateTextView()
            }
        }
        binding.btnApagarTudo.setOnClickListener {
            calculoAtual = ""
            updateTextView()
        }
        binding.btnIgual.setOnClickListener{
            if (calculoAtual.isNotEmpty()) {
                resultadoCalc = efetuarCalculo(calculoAtual).toString()
                iserirHistorico()
                calculoAtual = resultadoCalc
                mostrarResultado()
            } else {
                Toast.makeText(this, "Faça um Calculo. Antes de Apertar Igual", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnHistorico.setOnClickListener{
            navegarTelaHistorico()
        }
        binding.btnTroco.setOnClickListener {
            if (resultadoCalc == "0"){
                Toast.makeText(this, "Faça um Calculo ou Termine o Atual. Antes de Apertar Troco", Toast.LENGTH_LONG).show()
            }else {
                navegarTelaTroco()
            }
        }
    }

    private fun iserirHistorico(){
        val isInserted = dbHelper.insertData(calculoAtual, efetuarCalculo(calculoAtual))
        if (isInserted) {
            //não precisa
        } else {
            Toast.makeText(this, "Erro ao Salvar no Historico", Toast.LENGTH_LONG).show()
        }
    }

    private fun navegarTelaTroco(){
        val intent = Intent(this, TelaTroco::class.java)
        intent.putExtra("Resultado", resultadoCalc)
        startActivity(intent)
    }
    private fun navegarTelaHistorico(){
        val intent = Intent(this, TelaHistorico::class.java)
        startActivity(intent)
    }
    private fun efetuarCalculo(expression: String): Double {
        val exp = ExpressionBuilder(expression).build()
        return exp.evaluate()
    }
    //Notificação
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        val name: CharSequence = "MinhaNotificação"
        val descriptionText = "Minha Decrição da Notificação"

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = descriptionText
        // Register the channel with the system.
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}