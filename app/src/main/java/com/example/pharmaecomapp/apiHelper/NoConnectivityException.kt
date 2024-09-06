package com.example.pharmaecomapp.apiHelper

import java.io.IOException

class NoConnectivityException: IOException() {

    override val message: String?
        get() = "No connectivity exception";
}

