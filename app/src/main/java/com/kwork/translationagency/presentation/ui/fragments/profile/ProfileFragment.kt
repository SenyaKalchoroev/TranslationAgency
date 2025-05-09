package com.kwork.translationagency.presentation.ui.fragments.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kwork.translationagency.R
import com.kwork.translationagency.core.base.BaseFragment
import com.kwork.translationagency.core.utils.Extensions.gone
import com.kwork.translationagency.core.utils.Extensions.loadImageURL
import com.kwork.translationagency.core.utils.Extensions.showToast
import com.kwork.translationagency.core.utils.Extensions.visible
import com.kwork.translationagency.core.utils.viewBinding
import com.kwork.translationagency.databinding.FragmentProfileBinding
import com.kwork.translationagency.presentation.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment
    : BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    override val binding by viewBinding(FragmentProfileBinding::bind)
    override val viewModel by viewModels<ProfileViewModel>()

    override fun init() {
        val clientId = requireArguments().getString("clientId")
            ?: error("clientId не передан")
        viewModel.loadClient(clientId)
    }

    override fun launchObserver() {
        viewModel.clientState.observeUIState(
            loading =   { binding.progressBar.visible() },
            success = { ui ->
                binding.progressBar.gone()
                binding.profileImage.loadImageURL(ui.avatarUrl)
                binding.userNameTv .text = ui.name
                binding.userNicnameTv .text = ui.nickname
                binding.userNumberTv .text = ui.phone
            },
            error = { msg ->
                binding.progressBar.gone()
                requireContext().showToast(msg)
            }
        )
    }

    override fun constructorListeners() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

