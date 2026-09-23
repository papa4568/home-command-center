package com.papa.homecommandcenter

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PlannerTest {
    @Test
    fun garageBoxesAreBlockedUntilMattressMoves() {
        val readyIds = Planner.readyTasks(HomeData.tasks, emptySet()).map { it.id }.toSet()

        assertFalse("garage-boxes" in readyIds)
    }

    @Test
    fun movingMattressUnlocksGarageBoxes() {
        val readyIds = Planner.readyTasks(
            HomeData.tasks,
            setOf("mbed-mattress")
        ).map { it.id }.toSet()

        assertTrue("garage-boxes" in readyIds)
    }

    @Test
    fun laundryPullsWaitForCabinetInstallation() {
        val task = HomeData.tasks.first { it.id == "laundry-pulls" }

        assertTrue("laundry-cabinets-install" in Planner.blockedBy(task, emptySet()))
    }

    @Test
    fun unlockScoreRewardsTasksWithDependents() {
        val mattress = HomeData.tasks.first { it.id == "mbed-mattress" }
        val decorativeTask = HomeData.tasks.first { it.id == "short-art" }

        assertTrue(
            Planner.unlockScore(mattress, HomeData.tasks, emptySet()) >
                Planner.unlockScore(decorativeTask, HomeData.tasks, emptySet())
        )
    }
}
