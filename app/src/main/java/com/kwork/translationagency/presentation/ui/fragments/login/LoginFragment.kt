package com.kwork.translationagency.presentation.ui.fragments.login

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kwork.translationagency.R
import com.kwork.translationagency.core.base.BaseFragment
import com.kwork.translationagency.core.utils.Extensions.safeNavigate
import com.kwork.translationagency.core.utils.viewBinding
import com.kwork.translationagency.databinding.FragmentLoginBinding
import com.kwork.translationagency.presentation.viewmodel.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    R.layout.fragment_login
) {

    override val binding by viewBinding(FragmentLoginBinding::bind)
    override val viewModel by viewModels<LoginViewModel>()

    override fun init() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            findNavController().safeNavigate(R.id.action_global_homeFragment)
            return
        }
        binding.progressBar.visibility = View.GONE
    }

    override fun launchObserver() {
        viewModel.loginState.observeUIState(
            loading = {
                binding.progressBar.visibility = View.VISIBLE
            },
            success = {
                binding.progressBar.visibility = View.GONE
                findNavController().safeNavigate(R.id.action_global_homeFragment)
            },
            error = { message ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun constructorListeners() {
        binding.btnCircle.setOnClickListener {
            val email = binding.loginEt.text.toString().trim()
            val pass  = binding.passwordEt.text.toString().trim()
            if (email.isBlank() || pass.isBlank()) {
                Toast.makeText(requireContext(),
                    "E‑mail и пароль не должны быть пустыми",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            binding.progressBar.visibility = View.VISIBLE
            viewModel.signIn(email, pass)
        }
    }
}
