package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
class LoginFragment() : Fragment() {
    private val accountService = IoC.AccountService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val defaultMap = mapOf(
            "EUR" to mapOf(
                "GBP" to 0.5,
                "USD" to 2.0
            ),
            "GBP" to mapOf(
                "EUR" to 2.0,
                "USD" to 4.0
            ),
            "USD" to mapOf(
                "EUR" to 0.5,
                "GBP" to 0.25
            )
        )


        val intent = (view.context as AppCompatActivity).intent
        val username = intent.getStringExtra("username") ?: "Lara"
        val password = intent.getStringExtra("password") ?: "1234"

        val exchangeMap = intent.getSerializableExtra("exchangeMap") ?: defaultMap

        CurrentUser.Name = username
        ExchangeMap.map = exchangeMap as Map<String, Map<String, Double>>

        var account = accountService.getById(1)
        account.balance = intent.getDoubleExtra("balance", 100.0)

        var loginButton: Button = view.findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            var inputName: String = view.findViewById<EditText>(R.id.loginUsername).text.toString()
            var inputPas: String = view.findViewById<EditText>(R.id.loginPassword).text.toString()
            if (username == inputName && password == inputPas) {

                Toast.makeText(view.context, "logged in", Toast.LENGTH_SHORT).show()

                val bundle = Bundle()
                bundle.putString("name", inputName)
                findNavController().navigate(R.id.action_loginFragment_to_userMenuFragment, bundle)

            } else Toast.makeText(view.context, "invalid credentials", Toast.LENGTH_SHORT).show()
        }
    }
}