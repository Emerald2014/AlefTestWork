package ru.kudesnik.aleftestwork.model

sealed class AppState {
    data class Success(val modelData: List<String>) : AppState()
    object Loading : AppState()
}