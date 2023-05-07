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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

        val intent = (view.context as AppCompatActivity).intent
        val username = intent.getStringExtra("username") ?: "Lara"
        val password = intent.getStringExtra("password") ?: "1234"

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