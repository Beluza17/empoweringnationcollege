package com.example.empoweringnationcollege.data

data class Course(
    val name: String,
    val duration: String,
    val fees: Double,
    val purpose: String,
    val content: List<String>,
)

object CoursesRepository {
    val sixMonthCourses = listOf(
        Course(
            name = "First Aid",
            duration = "6 months",
            fees = 1500.00,
            purpose = "To provide first aid awareness and basic life support",
            content = listOf(
                "Wounds and bleeding",
                "Burns and fractures",
                "Emergency scene management",
                "Cardio-Pulmonary Resuscitation (CPR)",
                "Respiratory distress e.g., Choking, blocked airway",
            )
        ),
        Course(
            name = "Sewing",
            duration = "6 months",
            fees = 1500.00,
            purpose = "To provide alterations and new garment tailoring services",
            content = listOf(
                "Types of stitches",
                "Threading a sewing machine",
                "Sewing buttons, zips, hems and seams",
                "Alterations",
                "Designing and sewing new garments",
            )
        ),
        Course(
            name = "Landscaping",
            duration = "6 months",
            fees = 1500.00,
            purpose = "To provide landscaping services for new and established gardens",
            content = listOf(
                "Indigenous and exotic plants and trees",
                "Fixed structures (fountains, statues, benches, tables, built-in braai)",
                "Balancing of plants and trees in a garden",
                "Aesthetics of plant shapes and colours",
                "Garden layout",
            )
        ),
        Course(
            name = "Life Skills",
            duration = "6 months",
            fees = 1500.00,
            purpose = "To provide skills to navigate basic life necessities",
            content = listOf(
                "Opening a bank account",
                "Basic labour law (know your rights)",
                "Basic reading and writing literacy",
                "Basic numeric literacy",
            )
        ),
    )

    val sixWeekCourses = listOf(
        Course(
            name = "Child Minding",
            duration = "6 weeks",
            fees = 750.00,
            purpose = "To provide basic child and baby care",
            content = listOf(
                "birth to six-month old baby needs",
                "seven-month to one year old needs",
                "Toddler needs",
                "Educational toys",
            )
        ),
        Course(
            name = "Cooking",
            duration = "6 weeks",
            fees = 750.00,
            purpose = "To prepare and cook nutritious family meals",
            content = listOf(
                "Nutritional requirements for a healthy body",
                "Types of protein, carbohydrates and vegetables",
                "Planning meals",
                "Tasty and nutritious recipes",
                "Preparation and cooking of meals",
            )
        ),
        Course(
            name = "Garden Maintenance",
            duration = "6 weeks",
            fees = 750.00,
            purpose = "To provide basic knowledge of watering, pruning and planting in a domestic garden.",
            content = listOf(
                "Water restrictions and the watering requirements of indigenous and exotic plants",
                "Pruning and propagation of plants",
                "Planting techniques for different plant types",
            )
        ),
    )
}
