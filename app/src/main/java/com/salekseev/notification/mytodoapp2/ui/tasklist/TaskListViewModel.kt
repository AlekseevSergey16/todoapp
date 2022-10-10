package com.salekseev.notification.mytodoapp2.ui.tasklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salekseev.notification.mytodoapp2.data.TaskRepository
import com.salekseev.notification.mytodoapp2.model.Task

class TaskListViewModel : ViewModel() {
    private val taskRepository = TaskRepository.get()

    private val taskListLiveData = MutableLiveData<List<Task>>()

    val itemChangedPosition = MutableLiveData<Int>()

    val tasks: LiveData<List<Task>>
        get() = taskListLiveData

    fun getAllTasks() {
        val taskMutableList = taskRepository.getAllTasks()
        val tasks: MutableList<Task> = mutableListOf()

        for (task in taskMutableList) {
            tasks.add(Task(id = task.id, title = task.title, completed = task.completed))
        }

        taskListLiveData.value = tasks.toList()
    }

    fun changeCompletedTask(taskId: Long, completed: Boolean) {
        taskRepository.changeCompletedTaskRepo(taskId, completed)
        taskListLiveData.value = taskListLiveData.value!!.map { task ->
            if (task.id == taskId) {
                task.copy(completed = completed)
            } else {
                task
            }
        }
    }

}