package com.salekseev.notification.mytodoapp2.ui.tasklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.salekseev.notification.mytodoapp2.R
import com.salekseev.notification.mytodoapp2.model.Task
import com.salekseev.notification.mytodoapp2.ui.addedittask.ARG_TASK_ID

class TaskListFragment : Fragment() {

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var adapter: TaskListAdapter

    private val viewModel: TaskListViewModel by lazy {
        ViewModelProvider(this).get(TaskListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_task_list, container, false)

        tasksRecyclerView = view.findViewById(R.id.tasks_recyclerview)
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))

        adapter = TaskListAdapter(object : OnItemClickListener {
            override fun onItemClicked(task: Task) {
                val bundle = bundleOf(Pair(ARG_TASK_ID, task.id))
                Navigation.findNavController(view).navigate(R.id.addEditTaskFragment, bundle, navOptions2())
            }

            override fun onCheckBoxClicked(taskId: Long, checked: Boolean) {
                viewModel.changeCompletedTask(taskId, checked)
            }
        })
        tasksRecyclerView.adapter = adapter

        addButton = view.findViewById(R.id.add_button)
        addButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.addEditTaskFragment, null, navOptions2())
        }

        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            adapter.submitList(tasks)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllTasks()
    }

    private fun navOptions(): NavOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_in_left)
        .setPopEnterAnim(R.anim.slide_out_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()

    private fun navOptions2(): NavOptions = NavOptions.Builder()
        .setEnterAnim(androidx.appcompat.R.anim.abc_fade_in)
        .setExitAnim(androidx.appcompat.R.anim.abc_fade_out)
        .setPopEnterAnim(androidx.appcompat.R.anim.abc_fade_in)
        .setPopExitAnim(androidx.appcompat.R.anim.abc_fade_out)
        .build()

    companion object {
        @JvmStatic
        fun newInstance() = TaskListFragment()
    }

}