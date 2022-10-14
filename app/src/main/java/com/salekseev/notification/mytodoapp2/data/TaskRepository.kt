package com.salekseev.notification.mytodoapp2.data

import com.salekseev.notification.mytodoapp2.model.Task
import kotlin.random.Random

class TaskRepository private constructor() {

    private val tasks = createTasks()

    fun getAllTasks() = tasks.toMutableList()

    fun addTask(task: Task) {
        task.id = (3..1000).random().toLong()
        tasks.add(task)
    }

    fun updateTask(task: Task) {
        tasks.filter { it.id == task.id }
            .map { t ->
                t.apply {
                    title = task.title
                    completed = task.completed
                }
            }
    }

    fun deleteTask(id: Long) {
        tasks.removeIf { it.id == id }
    }

    fun getTaskById(id: Long): Task = tasks.last { it.id == id }

    fun changeCompletedTaskRepo(taskId: Long, completed: Boolean) {
        for (i in 0 until tasks.size) {
            if (tasks[i].id == taskId) {
                tasks[i].completed = completed
                break
            }
        }
    }

    private fun createTasks(): MutableList<Task> {
        return mutableListOf()
    }

    companion object {
        private val INSTANCE: TaskRepository = TaskRepository()
        fun get(): TaskRepository = INSTANCE
    }

}