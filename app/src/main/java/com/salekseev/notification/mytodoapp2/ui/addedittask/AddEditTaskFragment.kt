package com.salekseev.notification.mytodoapp2.ui.addedittask

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.salekseev.notification.mytodoapp2.R
import com.salekseev.notification.mytodoapp2.model.Task

const val ARG_TASK_ID = "taskId"

class AddEditTaskFragment : Fragment() {

    private lateinit var titleEditText: TextInputEditText
    private lateinit var completedCheckBox: CheckBox
    private lateinit var saveButton: FloatingActionButton
    private lateinit var deleteButton: MaterialButton

    private val viewModel: AddEditTaskViewModel by lazy {
        ViewModelProvider(this).get(AddEditTaskViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_add_edit_task, container, false)

        titleEditText = view.findViewById(R.id.title_edittext)
        completedCheckBox = view.findViewById(R.id.completed_checkbox)
        saveButton = view.findViewById(R.id.save_button)
        deleteButton = view.findViewById(R.id.delete_button)

        saveButton.setOnClickListener {
            viewModel.saveTask(Task(
                id = null,
                title = titleEditText.text.toString(),
                completed = completedCheckBox.isChecked
            ))
            Navigation.findNavController(view).popBackStack()
        }

        deleteButton.setOnClickListener {
            viewModel.deleteTask()
            Navigation.findNavController(view).popBackStack()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getLong(ARG_TASK_ID)?.let { id ->
            viewModel.loadTaskById(id)
        }

        viewModel.task.observe(viewLifecycleOwner) { task ->
            titleEditText.setText(task.title)
            completedCheckBox.isChecked = task.completed
            completedCheckBox.jumpDrawablesToCurrentState()
        }

        viewModel.deleteVisible.observe(viewLifecycleOwner) { visible ->
            deleteButton.visibility = if (visible) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }

        viewModel.toastText.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddEditTaskFragment()
    }
}