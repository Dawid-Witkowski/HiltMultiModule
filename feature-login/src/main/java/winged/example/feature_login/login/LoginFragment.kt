package winged.example.feature_login.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import winged.example.core.baseFragment.BaseFragment
import winged.example.core.utils.extractText
import winged.example.core.utils.isAValidEmail
import winged.example.core_data.db.entity.LoginCredentials
import winged.example.hiltmultimodule.featurelogin.R
import winged.example.hiltmultimodule.featurelogin.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLogInButton()
        setUpTextRedirection()
        observeForLoginEvents()
    }

    private fun setUpTextRedirection() {
        binding.signUpTV.setOnClickListener {
            navigateTo(R.id.registerFragment)
        }
    }

    private fun setUpLogInButton() {
        binding.logInBTN.setOnClickListener {
            val email = binding.emailTIET.extractText()
            val password = binding.passwordTIET.extractText()
            if(email.isAValidEmail() && password.isNotBlank()) {
                viewModel.logIn(LoginCredentials(mail = email, password = password))
            }
        }
    }

    private fun observeForLoginEvents() {
        viewModel.loginEvent.observe(viewLifecycleOwner) { result ->
            if(result.isSuccess) {
                /* Adding some kind of "Main Screen" module would be an idea
                 but as I've stated previously, this is just a small "test" project
                 showing off architecture, so I hope you will forgive me <3
                 (PS: if you are reading this and there still isn't that module, you can make a PR
                 and add it)*/
                Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "No matching account", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        // getting rid of ALL of the listeners, even the ones I did not set.
        // see ListenerAwareEditText comment
        binding.emailTIET.clearTextChangedListeners()
        binding.passwordTIET.clearTextChangedListeners()
        super.onDestroyView()
    }
}