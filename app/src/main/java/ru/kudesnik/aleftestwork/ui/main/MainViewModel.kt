package ru.kudesnik.aleftestwork.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kudesnik.aleftestwork.model.AppState
import ru.kudesnik.aleftestwork.model.repository.Repository
import ru.kudesnik.aleftestwork.model.repository.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {
    fun getLiveData() = liveDataToObserve

    fun getData() = with(viewModelScope) {
        liveDataToObserve.value = AppState.Loading

        launch(Dispatchers.IO) {
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getAllImages()))
        }
    }
}