package com.cashwu.model

object TaskRepository {

    private val tasks = mutableListOf(
        Task("cleaning", "Clean the house", Priority.Low),
        Task("gardening", "Mow the lawn", Priority.Medium),
        Task("shopping", "Buy the groceries", Priority.High),
        Task("painting", "Paint the fence", Priority.Medium)
    )

    fun allTasks() = tasks.toList()
//    fun allTasks() : List<Task> = tasks

    fun tasksByPriority(priority: Priority) = tasks.filter { it.priority == priority }

    fun taskByName(name: String) = tasks.find {
        it.name.equals(name, ignoreCase = true)
    }
//    fun taskByName(name: String) = tasks.find {
//        it.name == name
//    }

    fun addTask(task: Task) {
        if (taskByName(task.name) != null) {
            throw IllegalArgumentException("Task with name '${task.name}' already exists")
        }

        tasks.add(task)
    }

    fun removeTask(name: String): Boolean {
        return tasks.removeIf { it.name == name }
    }
}