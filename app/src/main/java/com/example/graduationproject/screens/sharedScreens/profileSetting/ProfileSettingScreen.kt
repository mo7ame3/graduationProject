package com.example.graduationproject.screens.sharedScreens.profileSetting

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.graduationproject.components.*
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.profile.GetProfile
import com.example.graduationproject.model.shared.profile.User
import com.example.graduationproject.model.shared.updatePassword.UpdatePassword
import com.example.graduationproject.model.shared.updateProfileData.UpdateProfileData
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.sharedpreference.SharedPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun ProfileSettingScreen(
    navController: NavController,
    client: Boolean,
    profileSettingViewModel: ProfileSettingViewModel
) {

    //Shared preference variables
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val token = sharedPreference.getToken.collectAsState(initial = "")
    val userId = sharedPreference.getUserId.collectAsState(initial = "")

    //state flow list
    val getProfileSetting = MutableStateFlow<List<User>>(emptyList())

    //coroutineScope
    val scope = rememberCoroutineScope()

    var loading by remember {
        mutableStateOf(true)
    }
    var exception by remember {
        mutableStateOf(false)
    }

    if (userId.value.toString().isNotEmpty() && token.value.toString().isNotEmpty()) {
        val getProfile: WrapperClass<GetProfile, Boolean, Exception> =
            produceState<WrapperClass<GetProfile, Boolean, Exception>>(
                initialValue = WrapperClass(data = null)
            ) {
                value = profileSettingViewModel.getProfile(
                    userId = userId.value.toString(),
                    authorization = "Bearer " + token.value.toString()
                )
            }.value
        if (getProfile.data?.status == "fail" || getProfile.data?.status == "error" || getProfile.e != null) {
            exception = true
            Toast.makeText(
                context,
                "خطأ في الانترنت",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (getProfile.data != null) {
                scope.launch {
                    getProfileSetting.emit(getProfile.data!!.data?.user!!)
                    loading = false
                    exception = false
                }
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        if (!loading && !exception) {
            //name
            val nameEdit = remember {
                mutableStateOf(getProfileSetting.value[0].name)
            }

            //address var
            val addressList = listOf(
                "مركز الفيوم",
                "مركز يوسف الصديق",
                "مركز طامية",
                "مركز سنورس",
                "مركز إطسا",
                "مركز إبشواي"
            )
            val expanded = remember {
                mutableStateOf(false)
            }
            val addressEdit = remember {
                mutableStateOf(getProfileSetting.value[0].address)
            }

            //bio
            val bioEdit = remember {
                if (getProfileSetting.value[0].bio == null)
                    mutableStateOf("")
                else
                    mutableStateOf(getProfileSetting.value[0].bio.toString())

            }

            // is change password

            val isChanePassword = remember {
                mutableStateOf(false)
            }

            // old password
            val oldPasswordEdit = remember {
                mutableStateOf("")
            }
            val oldEye = remember {
                mutableStateOf(false)
            }
            val oldPasswordEditNotError = remember {
                mutableStateOf(false)
            }

            // new password

            val newPasswordEdit = remember {
                mutableStateOf("")
            }
            val newEye = remember {
                mutableStateOf(false)
            }
            val newPasswordEditNotError = remember {
                mutableStateOf(false)
            }

            //confirm password

            val confirmPasswordEdit = remember {
                mutableStateOf("")
            }
            val confirmPasswordEditNotError = remember {
                mutableStateOf(false)
            }
            val keyboardController = LocalSoftwareKeyboardController.current

            //valid check old password from data base

            val valid = if (isChanePassword.value) {
                (nameEdit.value.isNotBlank() && addressEdit.value.isNotBlank() && oldPasswordEditNotError.value && newPasswordEditNotError.value && confirmPasswordEditNotError.value)
            } else {
                (nameEdit.value.isNotBlank() && addressEdit.value.isNotBlank())
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextInput(text = nameEdit,
                    leadingImageVector = Icons.Default.Person,
                    isNotBackground = true,
                    onAction = KeyboardActions {
                        keyboardController?.hide()
                    })
                Spacer(modifier = Modifier.height(5.dp))
                if (!client) {
                    TextInput(text = bioEdit,
                        label = "نبذه عني",
                        leadingImageVector = Icons.Default.Description,
                        isNotBackground = true,
                        onAction = KeyboardActions {
                            keyboardController?.hide()
                        }
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }

                DropList(
                    expanded = expanded,
                    value = addressEdit,
                    list = addressList,
                    isNotBackground = true,
                    leadingImageVector = Icons.Default.LocationOn
                )
                if (!isChanePassword.value) {
                    Spacer(modifier = Modifier.height(5.dp))
                    DefaultButton(label = "تغيير كلمة السر", modifier = Modifier.width(150.dp)) {
                        isChanePassword.value = !isChanePassword.value
                    }

                } else {
                    Spacer(modifier = Modifier.height(5.dp))
                    DefaultButton(
                        label = "عدم تغيير كلمة السر",
                        modifier = Modifier.width(150.dp)
                    ) {
                        isChanePassword.value = !isChanePassword.value
                    }
                }
                if (isChanePassword.value) {
                    PasswordInput(password = oldPasswordEdit,
                        eye = oldEye,
                        onButtonAction = {
                            oldEye.value = !oldEye.value
                        },
                        isNotError = oldPasswordEditNotError,
                        leadingImageVector = Icons.Default.Lock,
                        isNotBackground = true,
                        label = "كلمة السر القديمة",
                        onAction = KeyboardActions {
                            keyboardController?.hide()
                        })
                    Spacer(modifier = Modifier.height(5.dp))
                    PasswordInput(password = newPasswordEdit,
                        eye = newEye,
                        onButtonAction = {
                            newEye.value = !newEye.value
                        },
                        isNotError = newPasswordEditNotError,
                        leadingImageVector = Icons.Default.Lock,
                        isNotBackground = true,
                        label = "كلمة السر الجديدة",
                        onAction = KeyboardActions {
                            keyboardController?.hide()
                        })
                    Spacer(modifier = Modifier.height(5.dp))
                    PasswordInput(password = confirmPasswordEdit,
                        eye = newEye,
                        onButtonAction = {
                            newEye.value = !newEye.value
                        },
                        isNotError = confirmPasswordEditNotError,
                        leadingImageVector = Icons.Default.Lock,
                        isNotBackground = true,
                        isConfirmed = true,
                        passwordConfirm = newPasswordEdit,
                        label = "كلمة السر الجديدة",
                        onAction = KeyboardActions {
                            keyboardController?.hide()
                        })
                }
                if (!isChanePassword.value) Spacer(modifier = Modifier.height(302.dp))
                else Spacer(modifier = Modifier.height(100.dp))
                LoginButton(
                    label = "تأكيد",
                    enabled = valid,
                    isLogin = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(start = 25.dp, end = 25.dp)
                ) {
                    scope.launch {
                        loading = true
                        if (bioEdit.value.trim().isNotBlank()) {
                            val response: WrapperClass<UpdateProfileData, Boolean, Exception> =
                                profileSettingViewModel.updateProfileSetting(
                                    userId = userId.value.toString(),
                                    authorization = "Bearer " + token.value.toString(),
                                    name = nameEdit.value,
                                    address = addressEdit.value,
                                    bio = bioEdit.value
                                )
                            if (response.data?.status == "success") {
                                sharedPreference.saveName(nameEdit.value)
                                if (isChanePassword.value) {
                                    val changePassword: WrapperClass<UpdatePassword, Boolean, Exception> =
                                        profileSettingViewModel.updatePassword(
                                            authorization = "Bearer " + token.value.toString(),
                                            oldPassword = oldPasswordEdit.value,
                                            password = newPasswordEdit.value,
                                            passwordConfirm = confirmPasswordEdit.value
                                        )

                                    if (changePassword.data?.status == "success") {
                                        sharedPreference.saveState("")
                                        sharedPreference.saveToken("")
                                        navController.navigate(route = AllScreens.LoginScreen.name) {
                                            navController.popBackStack()
                                        }
                                    } else if (changePassword.data?.status == "fail" || changePassword.data?.status == "error" || changePassword.e != null) {
                                        loading = false
                                        Toast.makeText(
                                            context,
                                            "كلمة السر القديمة غير صحيحة",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }
                                else {
                                    navController.popBackStack()
                                }
                            }
                            else if (response.data?.status == "fail" || response.data?.status == "error" || response.e != null) {
                                loading = false
                                Toast.makeText(
                                    context,
                                    "خطأ في الانترنت",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                        else {
                            val response: WrapperClass<UpdateProfileData, Boolean, Exception> =
                                profileSettingViewModel.updateProfileSetting(
                                    userId = userId.value.toString(),
                                    authorization = "Bearer " + token.value.toString(),
                                    name = nameEdit.value,
                                    address = addressEdit.value,
                                    bio = ""
                                )
                            if (response.data?.status == "success") {
                                sharedPreference.saveName(nameEdit.value)
                                if (response.data?.status == "success") {
                                    sharedPreference.saveName(nameEdit.value)
                                    if (isChanePassword.value) {
                                        val changePassword: WrapperClass<UpdatePassword, Boolean, Exception> =
                                            profileSettingViewModel.updatePassword(
                                                authorization = "Bearer " + token.value.toString(),
                                                oldPassword = oldPasswordEdit.value,
                                                password = newPasswordEdit.value,
                                                passwordConfirm = confirmPasswordEdit.value
                                            )

                                        if (changePassword.data?.status == "success") {
                                            sharedPreference.saveState("")
                                            sharedPreference.saveToken("")
                                            navController.navigate(route = AllScreens.LoginScreen.name) {
                                                navController.popBackStack()
                                            }
                                        } else if (changePassword.data?.status == "fail" || changePassword.data?.status == "error" || changePassword.e != null) {
                                            loading = false
                                            Toast.makeText(
                                                context,
                                                "كلمة السر القديمة غير صحيحة",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    } else {
                                        navController.popBackStack()
                                    }
                                }
                            } else if (response.data?.status == "fail" || response.data?.status == "error" || response.e != null) {
                                loading = false
                                Toast.makeText(
                                    context,
                                    "خطأ في الانترنت",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
        else if (loading && !exception) {
            CircleProgress()
        }
        else if (exception) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    exception = false
                    loading = true
                    scope.launch {
                        val getProfile: WrapperClass<GetProfile, Boolean, Exception> =
                            profileSettingViewModel.getProfile(
                                userId = userId.value.toString(),
                                authorization = "Bearer " + token.value.toString()
                            )
                        if (getProfile.data?.status == "fail" || getProfile.data?.status == "error" || getProfile.e != null) {
                            exception = true
                            Toast.makeText(
                                context,
                                "خطأ في الانترنت",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (getProfile.data != null) {
                                scope.launch {
                                    getProfileSetting.emit(getProfile.data!!.data?.user!!)
                                    loading = false
                                    exception = false
                                }
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Refresh, contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }
    }
}