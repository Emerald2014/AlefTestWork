package ru.kudesnik.aleftestwork.model.repository

import ru.kudesnik.aleftestwork.model.entities.rest.ModelRepo

class RepositoryImpl : Repository {
    override fun getAllImages(): List<String> {
        val dto =
            ModelRepo.api.getModel().execute().body()
        val modelList = mutableListOf<String>()
        if (dto != null) {
            for (index in dto.indices) {
                modelList.add(dto[index])
            }
        }
        return modelList
    }
}