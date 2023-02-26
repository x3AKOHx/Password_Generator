package com.example.passwordgenerator.view_models

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordgenerator.data.PassEntity
import com.example.passwordgenerator.data.PassRepository
import com.example.passwordgenerator.data.RandomPasswordGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PassViewModel @Inject constructor(
    private val passRepository: PassRepository,
    private val passwordGenerator: RandomPasswordGenerator,
) : ViewModel() {
    private val _pass = MutableStateFlow(listOf<PassEntity>())
    val passFlow: StateFlow<List<PassEntity>> get() = _pass

    private val _expandedPassIdsList = MutableStateFlow(listOf<Long>())
    val expandedPassIdsList: StateFlow<List<Long>> get() = _expandedPassIdsList

    init {
        viewModelScope.launch {
            passRepository.getPasswords().collect {
                _pass.value = it.toMutableStateList()
            }
        }
    }

    fun getPass(passId: Long) = passRepository.getPass(passId)

    fun editTitle(title: String, passId: Long) {
        passRepository.editTitle(title, passId)
    }

    fun editLogin(login: String, passId: Long) {
        passRepository.editLogin(login, passId)
    }

    fun editPass(pass: String, passId: Long) {
        passRepository.editPass(pass, passId)
    }

    fun deletePass(passId: Long) {
        passRepository.deletePass(passId)
    }

    fun insert(pass: PassEntity) {
        passRepository.insert(pass)
    }

    fun generatePassword(): String {
        return passwordGenerator.generatePassword()
    }

    fun onCardArrowClicked(cardId: Long) {
        _expandedPassIdsList.value = _expandedPassIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }
}