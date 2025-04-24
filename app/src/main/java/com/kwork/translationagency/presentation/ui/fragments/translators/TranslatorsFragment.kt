package com.kwork.translationagency.presentation.ui.fragments.translators

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.kwork.translationagency.databinding.FragmentTranslatorsBinding

class TranslatorsFragment : Fragment() {


    private var _binding: FragmentTranslatorsBinding    ? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTranslatorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvOrders.layoutManager = GridLayoutManager(context, 3)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}