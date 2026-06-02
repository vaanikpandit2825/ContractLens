package com.example.devlens

data class ClauseAnalysis(
    val title: String,
    val riskLevel: String,
    val summary: String,
    val originalClause: String,
    val explanation: String,
    val recommendation: String
    )