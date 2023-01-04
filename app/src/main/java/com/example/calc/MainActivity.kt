package com.example.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var textViewInput: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewInput = findViewById(R.id.text_view_input)


     //   button_clear.setOnClickListener {
       //     textViewInput?.text = ""
       // }

        button_seven.setOnClickListener {
        textViewInput?.append("7")  // once we press 7 let it add 7 to text view input
       //  Toast.makeText(this, "Button clicked",Toast.LENGTH_SHORT).show()
        }
    }
        fun onDigit(view: View) {  // we can use this function on the activity_main.xml as onclick
            //but its now depreciated
            // its same as using set onclick listener
            //  Toast.makeText(this, "Button clicked",Toast.LENGTH_SHORT).show()
            textViewInput?.append((view as Button).text)
            lastNumeric = true  // these statements make onDecimalPoint true so only when a button
            lastDot =  false        // is clicked we can input a dot
        }

    fun onClear(view: View){
        textViewInput?.text = ""  // to clear the screen using the clear button
    }

    fun  onDecimalPoint (view: View){       //we done want two decimal points to follow each other
        if (lastNumeric && !lastDot){       // this is always false
            textViewInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        textViewInput?.text.let {
            if (lastNumeric && !isOperatorAdded(it.toString())){
                textViewInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if (value.startsWith("-")){
            false
        }
        else{
            value.contains("/")
                    || value.contains("-")
                    || value.contains("*")
                    || value.contains("+")
        }
    }

    fun onEqual(view: View){
        if (lastNumeric){  // if the last value is a number then do the block below
            var textViewInputValue = textViewInput?.text.toString()
            // The app was crashing when subtracting  negative values so we use the block of codes below
            var prefix = ""

            try {
                if (textViewInputValue.startsWith("-")){
                    prefix = "-"
                    textViewInputValue = textViewInputValue.substring(1) // this code removes the first char ie if the value was -99 it will now be 99
                }       // the code to prevent the crashing stops here

                if(textViewInputValue.contains("-")){
                    val splitValue = textViewInputValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    textViewInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }

                else if(textViewInputValue.contains("+")){
                    val splitValue = textViewInputValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    textViewInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }

                else if(textViewInputValue.contains("*")){
                    val splitValue = textViewInputValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    textViewInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

                else if (textViewInputValue.contains("/")){
                    val splitValue = textViewInputValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    textViewInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
            }
            catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }
}

