// Top -level build file where you can add configuration options common to all sub-projects/modules.

val geminiApiKey = project.findProperty("GEMINI_API_KEY") as String?
plugins {
    alias(libs.plugins.android.application) apply false
}

