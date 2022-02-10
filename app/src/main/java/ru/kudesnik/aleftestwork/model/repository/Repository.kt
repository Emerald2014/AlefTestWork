package ru.kudesnik.aleftestwork.model.repository

interface Repository {
    fun getAllImages(): List<String>
}