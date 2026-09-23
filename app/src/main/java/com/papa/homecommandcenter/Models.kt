package com.papa.homecommandcenter

enum class TaskType(val label: String) {
    DIY("DIY"),
    PURCHASE("Purchase"),
    CONTRACTOR("Contractor"),
    DECISION("Decision"),
    MEASURE("Measure"),
    APPOINTMENT("Appointment")
}

enum class Trade(val label: String) {
    GENERAL("General / Handyman"),
    ELECTRICAL("Electrical"),
    PAINTING("Painting"),
    CARPENTRY("Carpentry / Cabinets"),
    PLUMBING("Plumbing"),
    WALLPAPER("Wallpaper"),
    FLOORING("Flooring"),
    MOUNTING("Mounting"),
    GUTTERS("Gutters"),
    SECURITY("Security")
}

enum class Effort(val label: String) {
    QUICK("Quick"),
    SESSION("1–2 hours"),
    WEEKEND("Weekend"),
    PRO("Pro")
}

data class HomeTask(
    val id: String,
    val room: String,
    val title: String,
    val type: TaskType,
    val trade: Trade = Trade.GENERAL,
    val effort: Effort = Effort.QUICK,
    val dependencies: Set<String> = emptySet(),
    val note: String? = null
)
