package com.salekseev.notification.mytodoapp2.ui.addedittask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salekseev.notification.mytodoapp2.data.TaskRepository
import com.salekseev.notification.mytodoapp2.model.Task

class AddEditTaskViewModel : ViewModel() {

    private val taskRepository = TaskRepository.get()

    private var taskId: Long? = null

    val task = MutableLiveData<Task>()
    val toastText = MutableLiveData<String>()

    val deleteVisible = MutableLiveData(false)

    fun saveTask(task: Task) {
        if (taskId != null) {
            taskRepository.updateTask(task.apply { this.id = taskId })
        } else {
            taskRepository.addTask(task)
        }

        toastText.value = "Успешно!"
    }

    fun loadTaskById(id: Long) {
        this.taskId = id
        val receivedTask = taskRepository.getTaskById(id)

        task.value = receivedTask
        deleteVisible.value = true
    }

    fun deleteTask() {
        taskRepository.deleteTask(taskId!!)
        toastText.value = "Удалено!"
    }

}