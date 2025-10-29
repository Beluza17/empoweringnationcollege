package com.example.empoweringnationcollegeimport

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.empoweringnationcollege.data.Course
import com.example.empoweringnationcollege.data.CoursesRepository
import com.example.empoweringnationcollege.ui.theme.EmpoweringnationcollegeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmpoweringnationcollegeTheme {
                EmpoweringnationcollegeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewScreenSizes
@Composable
fun EmpoweringnationcollegeApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    var selectedCourse by rememberSaveable { mutableStateOf<Course?>(null) }
    var menuExpanded by remember { mutableStateOf(false) }


    if (selectedCourse != null) {
        CourseDetailsScreen(
            course = selectedCourse!!,
            onBack = { selectedCourse = null },
            modifier = Modifier.fillMaxSize()
        )
    } else {
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                AppDestinations.entries.forEach {
                    item(
                        icon = {
                            Icon(
                                it.icon,
                                contentDescription = it.label
                            )
                        },
                        label = { Text(it.label) },
                        selected = it == currentDestination,
                        onClick = { currentDestination = it }
                    )
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Empowering the Nation") },
                        actions = {
                            Box {
                                IconButton(onClick = { menuExpanded = true }) {
                                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                                }
                                DropdownMenu(
                                    expanded = menuExpanded,
                                    onDismissRequest = { menuExpanded = false }
                                ) {
                                    AppDestinations.entries.forEach { destination ->
                                        DropdownMenuItem(
                                            text = { Text(destination.label) },
                                            onClick = {
                                                currentDestination = destination
                                                menuExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                when (currentDestination) {
                    AppDestinations.HOME -> HomeScreen(
                        onNavigate = { currentDestination = it },
                        modifier = Modifier.padding(innerPadding)
                    )

                    AppDestinations.SUMMARY_SIX_MONTH -> SixMonthSummaryScreen(
                        onCourseClick = { selectedCourse = it },
                        modifier = Modifier.padding(innerPadding)
                    )

                    AppDestinations.SUMMARY_SIX_WEEK -> SixWeekSummaryScreen(
                        onCourseClick = { selectedCourse = it },
                        modifier = Modifier.padding(innerPadding)
                    )

                    AppDestinations.CALCULATE_FEES -> CalculateFeesScreen(modifier = Modifier.padding(innerPadding))
                    AppDestinations.CONTACT_DETAILS -> ContactDetailsScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    SUMMARY_SIX_MONTH("Six-month Courses", Icons.Default.School),
    SUMMARY_SIX_WEEK("Six-week Courses", Icons.Default.School),
    CALCULATE_FEES("Calculate Fees", Icons.Default.AttachMoney),
    CONTACT_DETAILS("Contact Details", Icons.Default.Phone),
}

@Composable
fun HomeScreen(onNavigate: (AppDestinations) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "E-T-N", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Empowering the Nation was established in 2022 and offers courses in Johannesburg. Hundreds of domestic workers and gardeners have been trained on both the six-month long Learnerships and six-week Short Skills Training Programmes to empower themselves and can provide more marketable skills.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onNavigate(AppDestinations.SUMMARY_SIX_MONTH) }) {
            Text("View Six-month Courses")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onNavigate(AppDestinations.SUMMARY_SIX_WEEK) }) {
            Text("View Six-week Courses")
        }
    }
}

@Composable
fun SixMonthSummaryScreen(onCourseClick: (Course) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(CoursesRepository.sixMonthCourses) { course ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onCourseClick(course) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = course.name, style = MaterialTheme.typography.headlineSmall)
                    Text(text = "Duration: ${course.duration}")
                    Text(text = "Fees: R${course.fees}")
                }
            }
        }
    }
}

@Composable
fun SixWeekSummaryScreen(onCourseClick: (Course) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(CoursesRepository.sixWeekCourses) { course ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onCourseClick(course) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = course.name, style = MaterialTheme.typography.headlineSmall)
                    Text(text = "Duration: ${course.duration}")
                    Text(text = "Fees: R${course.fees}")
                }
            }
        }
    }
}

@Composable
fun CalculateFeesScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    val allCourses = CoursesRepository.sixMonthCourses + CoursesRepository.sixWeekCourses
    val selectedCourses = remember { mutableStateOf<Set<Course>>(emptySet()) }
    var totalFee by remember { mutableStateOf(0.0) }

    fun validate(): Boolean {
        nameError = if (name.isBlank()) "Name cannot be empty" else null
        phoneError = if (phone.isBlank() || !Patterns.PHONE.matcher(phone).matches()) "Invalid phone number" else null
        emailError = if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email address" else null
        return nameError == null && phoneError == null && emailError == null
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Calculate Total Fees", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = name,
            onValueChange = { name = it; nameError = null },
            label = { Text("Name") },
            isError = nameError != null,
            supportingText = { nameError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = phone,
            onValueChange = { phone = it; phoneError = null },
            label = { Text("Phone Number") },
            isError = phoneError != null,
            supportingText = { phoneError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it; emailError = null },
            label = { Text("Email Address") },
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Select Courses:", style = MaterialTheme.typography.headlineSmall)
        LazyColumn(modifier = Modifier.height(200.dp)) {
            items(allCourses) { course ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val currentSelection = selectedCourses.value
                            selectedCourses.value = if (course in currentSelection) {
                                currentSelection - course
                            } else {
                                currentSelection + course
                            }
                        }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = course in selectedCourses.value,
                        onCheckedChange = {
                            val currentSelection = selectedCourses.value
                            selectedCourses.value = if (course in currentSelection) {
                                currentSelection - course
                            } else {
                                currentSelection + course
                            }
                        }
                    )
                    Text(text = "${course.name} - R${course.fees}", modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (validate()) {
                    val numCourses = selectedCourses.value.size
                    val discount = when {
                        numCourses > 3 -> 0.15
                        numCourses == 3 -> 0.10
                        numCourses == 2 -> 0.05
                        else -> 0.0
                    }
                    val subtotal = selectedCourses.value.sumOf { it.fees }
                    val discountedTotal = subtotal * (1 - discount)
                    totalFee = discountedTotal * 1.15
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Total")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (totalFee > 0) {
            Text(
                text = "Total Fee (including discount and 15% VAT): R${"%.2f".format(totalFee)}",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun ContactDetailsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Contact Details", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Phone: 011 123 4567", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Email: info@empoweringthenation.co.za", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Our Venues:", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Sandton\n" +
                "123 Maude Street, Sandton, Johannesburg, 2196", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Rosebank\n" +
                "456 Oxford Road, Rosebank, Johannesburg, 2196", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailsScreen(
    course: Course,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(course.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(text = course.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Duration: ${course.duration}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Fees: R${course.fees}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = course.purpose, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
