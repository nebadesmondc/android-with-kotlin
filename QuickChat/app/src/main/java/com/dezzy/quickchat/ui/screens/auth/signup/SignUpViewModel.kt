package com.dezzy.quickchat.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow<SignUpState>(SignUpState.Initial)
    val state = _state.asStateFlow()

    fun signUp(username:String, email: String, password: String) {
        _state.value = SignUpState.Loading
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.updateProfile(
                        UserProfileChangeRequest.Builder().setDisplayName(username).build()
                    )
                    _state.value = SignUpState.Success
                } else {
                    _state.value = SignUpState.Error
                }
            }
    }
}

sealed class SignUpState {
    object Initial: SignUpState()
    object Loading: SignUpState()
    object Error: SignUpState()
    object Success: SignUpState()

}