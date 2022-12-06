package winged.example.feature_login.register

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
import winged.example.hiltmultimodule.featurelogin.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class RegisterFragment: BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCreateAccountButton()
        setUpTextRedirection()
        observeRegisterEvents()
    }

    private fun setUpTextRedirection() {
        binding.logInTV.setOnClickListener {
            navigateTo(R.id.loginFragment, true)
        }
    }

    private fun setUpCreateAccountButton() {
        binding.createAnAccountBTN.setOnClickListener {
            val email = binding.emailTIET.extractText()
            val password = binding.passwordTIET.extractText()
            val repeatedPassword = binding.repeatPasswordTIET.extractText()
            if(email.isAValidEmail() && (password == repeatedPassword) && password.isNotEmpty()) {
                viewModel.saveUser(
                    LoginCredentials(mail = email, password = password)
                )
            }
        }
    }

    private fun observeRegisterEvents() {
        viewModel.registerEvent.observe(viewLifecycleOwner) { result ->
            if(result.isSuccess) {
                navigateTo(R.id.loginFragment)
            } else {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        // getting rid of ALL of the listeners, even the ones I did not set.
        // see ListenerAwareEditText comment
        binding.emailTIET.clearTextChangedListeners()
        binding.passwordTIET.clearTextChangedListeners()
        binding.repeatPasswordTIET.clearTextChangedListeners()
        super.onDestroyView()
    }
}