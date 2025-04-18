package com.kwork.translationagency.presentation.ui.fragments.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kwork.translationagency.R
import com.kwork.translationagency.databinding.FragmentClientsBinding
import com.kwork.translationagency.domain.model.ClientModel
import com.kwork.translationagency.presentation.ui.fragments.clients.adapter.ClientsAdapter
import com.kwork.translationagency.presentation.ui.fragments.search.SearchDialogFragment

class ClientsFragment : Fragment() {

    private var _binding: FragmentClientsBinding? = null
    private val binding get() = _binding!!

    private val localClients = listOf(
        ClientModel(
            name = "Иванова Т.",
            nickname = "t.ivanova",
            phone = "+7 123 456 7890",
            avatarResId = R.drawable.img_human
        ),
        ClientModel(
            name = "Марк И.",
            nickname = "mark_i",
            phone = "+7 987 654 3210",
            avatarResId = R.drawable.img_human
        ),
        ClientModel(
            name = "Лиза В.",
            nickname = "lizav",
            phone = "+7 926 000 1122",
            avatarResId = R.drawable.img_human
        ) ,
        ClientModel(
            name = "Иванова Т.",
            nickname = "t.ivanova",
            phone = "+7 123 456 7890",
            avatarResId = R.drawable.img_human
        ),
        ClientModel(
            name = "Марк И.",
            nickname = "mark_i",
            phone = "+7 987 654 3210",
            avatarResId = R.drawable.img_human
        ),
        ClientModel(
            name = "Лиза В.",
            nickname = "lizav",
            phone = "+7 926 000 1122",
            avatarResId = R.drawable.img_human
        )

    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentClientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvOrders.layoutManager = GridLayoutManager(context, 3)
        binding.rvOrders.adapter = ClientsAdapter(localClients)
        binding.btnCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_clientsFragment_to_translatorsFragment)
        }
        binding.searchView.setOnClickListener {
            val dialog = SearchDialogFragment()
            dialog.show(parentFragmentManager, "SearchDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
