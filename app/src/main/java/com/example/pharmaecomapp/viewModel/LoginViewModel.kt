package com.example.pharmaecomapp.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pharmaecomapp.activity.HomeActivity
import com.example.pharmaecomapp.activity.LoginActivity
import com.example.pharmaecomapp.apiHelper.ApiController
import com.example.pharmaecomapp.apiHelper.ApiResponseListner
import com.example.pharmaecomapp.data.LoginResponse
import com.example.pharmaecomapp.utils.ApiConstants
import com.example.pharmaecomapp.utils.GeneralUtilities
import com.example.pharmaecomapp.utils.PrefManager
import com.example.pharmaecomapp.utils.Utility
import com.google.gson.JsonElement

class LoginViewModel(val context: LoginActivity) : ViewModel() , ApiResponseListner {
    private lateinit var apiClient: ApiController
    val mobileNo= MutableLiveData<List<LoginResponse.UserData>>()
    val errorMsg= MutableLiveData<String>()

    /*  private val _text = MutableLiveData<String>().apply {
          value = "This is home Fragment"
      }
      val text: LiveData<String> = _text
  */







    override fun success(tag: String?, jsonElement: JsonElement?) {
        apiClient.progressView?.hideLoader()
        if (tag == ApiConstants.login) {
            val loginModel = apiClient.getConvertIntoModel<LoginResponse>(jsonElement.toString(), LoginResponse::class.java)
            PrefManager.putString(ApiConstants.AccessToken, loginModel.data.token )
            //   PrefManager.putString(ApiContants.mobileNumber, binding.editMobNo.text.toString())
            //  PrefManager.putString(ApiContants.password,  binding.editPassword.text.toString() )
            Toast.makeText(context, loginModel.msg, Toast.LENGTH_SHORT).show()
            GeneralUtilities.launchActivity(context, HomeActivity::class.java)
            context.finishAffinity()
        }
    }

    override fun failure(tag: String?, errorMessage: String?) {
        apiClient.progressView?.hideLoader()

        if (errorMessage != null) {
            Utility.showSnackBar(context, errorMessage)
        }
    }
}