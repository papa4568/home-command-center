package com.papa.homecommandcenter

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private val HomeColors = lightColorScheme(
    primary = Color(0xFF405A45),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDDE8DD),
    onPrimaryContainer = Color(0xFF17301C),
    secondary = Color(0xFF725B45),
    secondaryContainer = Color(0xFFF1E2D2),
    tertiary = Color(0xFF5A6172),
    background = Color(0xFFFAF8F2),
    surface = Color(0xFFFFFCF7),
    surfaceVariant = Color(0xFFECE8E0)
)

@Composable
fun HomeCommandTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = HomeColors,
        typography = MaterialTheme.typography,
        content = content
    )
}

private class HomeStateStore(context: Context) {
    private val prefs = context.getSharedPreferences("home_command_center", Context.MODE_PRIVATE)

    fun loadCompleted(): Set<String> =
        prefs.getStringSet("completed_task_ids", emptySet())?.toSet() ?: emptySet()

    fun saveCompleted(completed: Set<String>) {
        prefs.edit().putStringSet("completed_task_ids", completed).apply()
    }
}

private enum class AppTab(val label: String) {
    DASHBOARD("Home"),
    ROOMS("Rooms"),
    WORK("Work"),
    SHOP("Shop")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCommandApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val store = remember(context) { HomeStateStore(context.applicationContext) }
    val tasks = remember { HomeData.tasks }
    var completed by remember { mutableStateOf(store.loadCompleted()) }
    var tab by rememberSaveable { mutableStateOf(AppTab.DASHBOARD) }

    fun setCompleted(task: HomeTask, isDone: Boolean) {
        completed = completed.toMutableSet().apply {
            if (isDone) add(task.id) else remove(task.id)
        }.toSet()
        store.saveCompleted(completed)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Home Command Center", fontWeight = FontWeight.SemiBold)
                        Text(
                            (Planner.completion(tasks, completed) * 100).roundToInt().toString() + "% complete",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                AppTab.entries.forEach { item ->
                    NavigationBarItem(
                        selected = tab == item,
                        onClick = { tab = item },
                        icon = {
                            Icon(
                                imageVector = when (item) {
                                    AppTab.DASHBOARD -> Icons.Outlined.Home
                                    AppTab.ROOMS -> Icons.Outlined.MeetingRoom
                                    AppTab.WORK -> Icons.Outlined.Build
                                    AppTab.SHOP -> Icons.Outlined.ShoppingCart
                                },
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            when (tab) {
                AppTab.DASHBOARD -> DashboardScreen(tasks, completed, ::setCompleted)
                AppTab.ROOMS -> RoomsScreen(tasks, completed, ::setCompleted)
                AppTab.WORK -> WorkScreen(tasks, completed, ::setCompleted)
                AppTab.SHOP -> ShopScreen(tasks, completed, ::setCompleted)
            }
        }
    }
}

@Composable
private fun DashboardScreen(
    tasks: List<HomeTask>,
    completed: Set<String>,
    onCompletedChange: (HomeTask, Boolean) -> Unit
) {
    val progress = Planner.completion(tasks, completed)
    val ready = Planner.readyTasks(tasks, completed).take(5)
    val decisions = tasks.filter { it.type == TaskType.DECISION && it.id !in completed }
    val purchases = tasks.count { it.type == TaskType.PURCHASE && it.id !in completed }
    val contractorGroups = Planner.contractorGroups(tasks, completed)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Whole-house progress",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        completed.size.toString() + " of " + tasks.size + " tasks finished",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                    Text(
                        (progress * 100).roundToInt().toString() + "%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = decisions.size.toString(),
                    label = "decisions blocking"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = purchases.toString(),
                    label = "things to buy"
                )
            }
        }

        item { SectionTitle("Best next moves", "Ranked by what they unlock") }

        items(ready, key = { it.id }) { task ->
            NextMoveCard(
                task = task,
                score = Planner.unlockScore(task, tasks, completed),
                completed = task.id in completed,
                onCompletedChange = { onCompletedChange(task, it) }
            )
        }

        if (decisions.isNotEmpty()) {
            item { SectionTitle("Blocking decisions", "Resolve these before they create delays") }
            items(decisions.take(4), key = { it.id }) { task ->
                TaskRow(task, tasks, completed, onCompletedChange)
            }
        }

        if (contractorGroups.isNotEmpty()) {
            item { SectionTitle("Bundle contractor visits", "Combine scattered work by trade") }
            contractorGroups.entries.take(4).forEach { entry ->
                item(key = "bundle-" + entry.key.name) {
                    ContractorBundleCard(entry.key, entry.value)
                }
            }
        }

        item { Spacer(Modifier.height(6.dp)) }
    }
}

@Composable
private fun StatCard(modifier: Modifier, value: String, label: String) {
    Card(modifier = modifier) {
        Column(Modifier.padding(14.dp)) {
            Text(
                value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(label, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun NextMoveCard(
    task: HomeTask,
    score: Int,
    completed: Boolean,
    onCompletedChange: (Boolean) -> Unit
) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = completed, onCheckedChange = onCompletedChange)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(task.room, style = MaterialTheme.typography.labelMedium)
                Text(task.title, fontWeight = FontWeight.SemiBold)
                Text(
                    task.type.label + " • " + task.effort.label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.TrendingUp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    score.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ContractorBundleCard(trade: Trade, work: List<HomeTask>) {
    Card {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                trade.label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                work.size.toString() + " open job" + (if (work.size == 1) "" else "s") + " — quote together",
                color = MaterialTheme.colorScheme.primary
            )
            work.take(4).forEach {
                Text("• " + it.room + ": " + it.title, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun RoomsScreen(
    tasks: List<HomeTask>,
    completed: Set<String>,
    onCompletedChange: (HomeTask, Boolean) -> Unit
) {
    var selectedRoom by rememberSaveable { mutableStateOf<String?>(null) }

    if (selectedRoom == null) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { SectionTitle("Rooms", "Tap a room for its full punch list") }
            items(HomeData.roomOrder, key = { it }) { room ->
                val roomTasks = tasks.filter { it.room == room }
                val progress = Planner.roomProgress(room, tasks, completed)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedRoom = room }
                ) {
                    Column(
                        Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                room,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                roomTasks.count { it.id in completed }.toString() + "/" + roomTasks.size,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    } else {
        val room = selectedRoom!!
        val roomTasks = tasks.filter { it.room == room }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { selectedRoom = null }) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                    Column {
                        Text(room, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(
                            roomTasks.count { it.id in completed }.toString() + " of " + roomTasks.size + " complete",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            items(roomTasks, key = { it.id }) { task ->
                TaskRow(task, tasks, completed, onCompletedChange)
            }
        }
    }
}

private enum class WorkFilter(val label: String) {
    NEXT("Ready now"),
    QUICK("Quick"),
    WEEKEND("Weekend"),
    CONTRACTOR("Contractor"),
    DECISIONS("Decisions")
}

@Composable
private fun WorkScreen(
    tasks: List<HomeTask>,
    completed: Set<String>,
    onCompletedChange: (HomeTask, Boolean) -> Unit
) {
    var filter by rememberSaveable { mutableStateOf(WorkFilter.NEXT) }

    val visible = when (filter) {
        WorkFilter.NEXT -> Planner.readyTasks(tasks, completed)
        WorkFilter.QUICK -> tasks.filter {
            it.id !in completed &&
                it.effort == Effort.QUICK &&
                Planner.blockedBy(it, completed).isEmpty()
        }
        WorkFilter.WEEKEND -> tasks.filter {
            it.id !in completed && it.effort == Effort.WEEKEND
        }
        WorkFilter.CONTRACTOR -> tasks.filter {
            it.id !in completed && it.type == TaskType.CONTRACTOR
        }
        WorkFilter.DECISIONS -> tasks.filter {
            it.id !in completed && (it.type == TaskType.DECISION || it.type == TaskType.MEASURE)
        }
    }

    Column(Modifier.fillMaxSize()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(WorkFilter.entries) { item ->
                FilterChip(
                    selected = filter == item,
                    onClick = { filter = item },
                    label = { Text(item.label) }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    visible.size.toString() + " open task" + (if (visible.size == 1) "" else "s"),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            items(visible, key = { it.id }) { task ->
                TaskRow(task, tasks, completed, onCompletedChange)
            }
        }
    }
}

@Composable
private fun ShopScreen(
    tasks: List<HomeTask>,
    completed: Set<String>,
    onCompletedChange: (HomeTask, Boolean) -> Unit
) {
    val purchases = tasks.filter { it.type == TaskType.PURCHASE }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SectionTitle(
                "Shopping",
                purchases.count { it.id !in completed }.toString() + " items still to buy"
            )
        }

        HomeData.roomOrder.forEach { room ->
            val roomPurchases = purchases.filter { it.room == room }
            if (roomPurchases.isNotEmpty()) {
                item(key = "shop-header-" + room) {
                    Text(
                        room,
                        modifier = Modifier.padding(top = 10.dp, bottom = 2.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(roomPurchases, key = { it.id }) { task ->
                    TaskRow(task, tasks, completed, onCompletedChange)
                }
            }
        }
    }
}

@Composable
private fun TaskRow(
    task: HomeTask,
    allTasks: List<HomeTask>,
    completed: Set<String>,
    onCompletedChange: (HomeTask, Boolean) -> Unit
) {
    val done = task.id in completed
    val blockedIds = Planner.blockedBy(task, completed)
    val blockers = blockedIds.mapNotNull { id -> allTasks.firstOrNull { it.id == id } }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (done) {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCompletedChange(task, !done) }
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = done,
                onCheckedChange = { onCompletedChange(task, it) }
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    task.title,
                    fontWeight = FontWeight.Medium,
                    textDecoration = if (done) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    task.room + " • " + task.type.label + " • " + task.effort.label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (blockedIds.isNotEmpty() && !done) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            " Waiting on " + blockers.joinToString { it.title },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                task.note?.let {
                    Text(
                        it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (task.trade != Trade.GENERAL) {
                    AssistChip(
                        onClick = {},
                        label = { Text(task.trade.label) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(
            subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        HorizontalDivider(modifier = Modifier.padding(top = 6.dp))
    }
}
