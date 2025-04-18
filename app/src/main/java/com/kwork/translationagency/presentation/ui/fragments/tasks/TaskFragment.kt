package com.kwork.translationagency.presentation.ui.fragments.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kwork.translationagency.R
import com.kwork.translationagency.databinding.FragmentTaskBinding
import com.kwork.translationagency.domain.model.TaskModel
import com.kwork.translationagency.presentation.ui.fragments.search.SearchDialogFragment
import com.kwork.translationagency.presentation.ui.fragments.tasks.adapter.TaskAdapter

class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private val localTasks = listOf(
        TaskModel(
            imageRes = R.drawable.ic_new_task_not_colored,
            taskName = "Проверить новые заказы..",
            secondDate = "завершено",
            firstDate = "Анна П.",
            taskTime = "вчера"
        ),
        TaskModel(
            imageRes = R.drawable.ic_new_task_not_colored,
            taskName = "Связаться с переводчиком..",
            secondDate = "активно",
            firstDate = "Олег В.",
            taskTime = "сегодня"
        ),
        TaskModel(
            imageRes = R.drawable.ic_new_task_not_colored,
            taskName = "Оценить документы..",
            secondDate = "новое",
            firstDate = "Кира К.",
            taskTime = "15 мин назад"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TaskAdapter(localTasks)

        binding.rvTask.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTask.adapter = adapter
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
