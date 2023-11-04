package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.log
import kotlin.random.Random
import android.widget.EditText as EditText

class MainActivity : AppCompatActivity() {

    private lateinit var preference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //buscar os objetos e ter a referencia deles
        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        //bando de dados de preferencias
        preference = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = preference.getString("resultID", null)


        result?.let{
            txtResult.text = "Ultima aposta: $it"
        }

        btnGenerate.setOnClickListener {

            val text = editText.text.toString()
            numberGenerator(text, txtResult, editText)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView, editText: EditText) {

        val qtd = text.toIntOrNull()

        if (qtd == null || qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = java.util.Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)
            if (numbers.size == qtd) {
                break
            }
        }
        val SortedNumbers = numbers.sorted()

        txtResult.text = SortedNumbers.joinToString(" - ")

        preference.edit().apply {
            putString("resultID", txtResult.text.toString())
            apply()
        }
        editText.text.clear()
    }
}