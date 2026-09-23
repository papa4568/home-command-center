package com.papa.homecommandcenter

object Planner {
    fun completion(tasks: List<HomeTask>, completed: Set<String>): Float {
        if (tasks.isEmpty()) return 0f
        return tasks.count { it.id in completed }.toFloat() / tasks.size.toFloat()
    }

    fun blockedBy(task: HomeTask, completed: Set<String>): Set<String> =
        task.dependencies.filterNot { it in completed }.toSet()

    fun readyTasks(tasks: List<HomeTask>, completed: Set<String>): List<HomeTask> =
        tasks
            .filter { it.id !in completed && blockedBy(it, completed).isEmpty() }
            .sortedWith(
                compareByDescending<HomeTask> { unlockScore(it, tasks, completed) }
                    .thenByDescending { typeWeight(it.type) }
                    .thenBy { it.room }
                    .thenBy { it.title }
            )

    fun unlockScore(task: HomeTask, tasks: List<HomeTask>, completed: Set<String>): Int {
        if (task.id in completed) return 0

        val direct = tasks.count {
            it.id !in completed && task.id in it.dependencies
        }
        val secondOrder = tasks.count { candidate ->
            candidate.id !in completed &&
                candidate.dependencies.any { dependencyId ->
                    tasks.any { middle ->
                        middle.id == dependencyId && task.id in middle.dependencies
                    }
                }
        }

        val blockerBonus = when (task.type) {
            TaskType.DECISION -> 8
            TaskType.MEASURE -> 7
            TaskType.APPOINTMENT -> 5
            TaskType.PURCHASE -> 4
            TaskType.CONTRACTOR -> 3
            TaskType.DIY -> 2
        }

        return (direct * 20) + (secondOrder * 5) + blockerBonus
    }

    fun roomProgress(
        room: String,
        tasks: List<HomeTask>,
        completed: Set<String>
    ): Float {
        val roomTasks = tasks.filter { it.room == room }
        return completion(roomTasks, completed)
    }

    fun contractorGroups(
        tasks: List<HomeTask>,
        completed: Set<String>
    ): Map<Trade, List<HomeTask>> =
        tasks
            .filter { it.type == TaskType.CONTRACTOR && it.id !in completed }
            .groupBy { it.trade }
            .toList()
            .sortedByDescending { (_, work) -> work.size }
            .toMap()

    private fun typeWeight(type: TaskType): Int = when (type) {
        TaskType.DECISION -> 6
        TaskType.MEASURE -> 5
        TaskType.APPOINTMENT -> 4
        TaskType.PURCHASE -> 3
        TaskType.CONTRACTOR -> 2
        TaskType.DIY -> 1
    }
}
