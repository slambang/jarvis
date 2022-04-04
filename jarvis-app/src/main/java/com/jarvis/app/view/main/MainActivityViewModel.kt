package com.jarvis.app.view.main

import androidx.lifecycle.*
import com.jarvis.app.domain.interactors.FieldsInteractor
import com.jarvis.app.domain.interactors.SettingsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mapper: MainModelMapper,
    private val settingsInteractor: SettingsInteractor,
    private val fieldsInteractor: FieldsInteractor
) : ViewModel() {

    val menuState: LiveData<MainMenuViewModel> = liveData {
        settingsInteractor.settingsFlow.collect {
            emit(mapper.mapMenuModel(it))
        }
    }

    val configItems: LiveData<List<FieldItemViewModel<*>>> = liveData {
        fieldsInteractor.getAllFields().collect {
            emit(mapper.mapToConfigItems(it))
        }
    }

    fun setJarvisIsLocked(isLocked: Boolean) {
        settingsInteractor.isJarvisLocked = isLocked
    }

    fun setJarvisIsActive(isActive: Boolean) {
        settingsInteractor.isJarvisActive = isActive
    }

    fun updateFieldValue(
        item: FieldItemViewModel<*>,
        newValue: Any,
        isPublished: Boolean
    ) {
        viewModelScope.launch {
            fieldsInteractor.onFieldUpdated(item.fieldDomain, newValue, isPublished)
        }
    }

    fun clearConfigs() {
        viewModelScope.launch {
            fieldsInteractor.deleteAllFields()
        }
    }
}
