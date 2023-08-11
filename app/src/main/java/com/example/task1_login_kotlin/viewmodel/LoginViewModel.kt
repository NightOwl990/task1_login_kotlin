package com.example.task1_login_kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.task1_login_kotlin.respositories.LoginRepository
import com.example.task1_login_kotlin.utils.CheckLogin
import com.example.task1_login_kotlin.view.interfaces.OnDialogCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : BaseViewModel() {

    private lateinit var dialogCallBack: OnDialogCallBack
    fun setDialogCallBack(dialogCallBack: OnDialogCallBack) {
        this.dialogCallBack = dialogCallBack
    }

    var res = MutableLiveData<String>()
    fun login(username: String, password: String) = viewModelScope.launch {
        val validationMessage = CheckLogin.validateLoginInput(username, password)
        if (validationMessage.isNotEmpty()) {
            res.postValue(validationMessage)
            return@launch
        }
        try {
            dialogCallBack.showDialog()
            val response = withContext(Dispatchers.IO) {
                repository.startLogin("nightowl990", "123456")
            }
            when (response) {
                "200", "201" -> res.postValue("Login successful")
                "401" -> res.postValue("Incorrect account or password")
                else -> res.postValue("Error $response")
            }
        } catch (e: Exception) {
            res.postValue("Error: ${e.message}")
        } finally {
            dialogCallBack.cancelDialog()
        }
    }
}


