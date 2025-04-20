package com.kwork.translationagency.presentation.ui.fragments.login

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import com.kwork.translationagency.R
import com.kwork.translationagency.core.base.BaseFragment
import com.kwork.translationagency.core.utils.viewBinding
import com.kwork.translationagency.databinding.FragmentLoginBinding
import com.kwork.translationagency.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(R.layout.fragment_login) {

    override val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    override val viewModel: LoginViewModel by viewModels()

    override fun init() {
        binding.progressBar.visibility = View.GONE
    }

    override fun launchObserver() {
        viewModel.loginState.observeUIState(
            loading = {
            },
            success = {
                binding.progressBar.visibility = View.GONE
                findNavController().navigate(R.id.action_loginFragment_to_homeScreen)
            },
            error = { message ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun constructorListeners() {
        binding.btnCircle.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val loginOrEmail = binding.loginEt.text.toString().trim()
            val password     = binding.passwordEt.text.toString()
            viewModel.signIn(loginOrEmail, password)
        }
    }
}

