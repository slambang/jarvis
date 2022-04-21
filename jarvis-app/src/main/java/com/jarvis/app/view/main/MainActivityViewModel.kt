package com.jarvis.app.view.main

import androidx.lifecycle.*
import com.jarvis.app.domain.interactors.SettingsInteractor
import com.jarvis.app.domain.usecases.DeleteAllConfigsUseCase
import com.jarvis.app.domain.usecases.GetAllFieldsUseCase
import com.jarvis.app.domain.usecases.UpdateFieldUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mapper: MainModelMapper,
    private val settingsInteractor: SettingsInteractor,
    private val getAllFields: GetAllFieldsUseCase,
    private val updateField: UpdateFieldUseCase,
    private val deleteAllFields: DeleteAllConfigsUseCase
) : ViewModel() {

    val menuState: LiveData<MainMenuViewModel> = liveData {
        settingsInteractor.settingsFlow.collect {
            emit(mapper.mapMenuModel(it))
        }
    }

    val configItems: LiveData<List<FieldItemViewModel<*>>> = liveData {
        getAllFields().collect {
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
            updateField(item.fieldDomain, newValue, isPublished)
        }
    }

    fun clearConfigs() {
        viewModelScope.launch {
            deleteAllFields()
        }
    }
}
