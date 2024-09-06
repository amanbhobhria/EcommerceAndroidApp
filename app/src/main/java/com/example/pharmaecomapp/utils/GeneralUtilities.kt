package com.example.pharmaecomapp.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class GeneralUtilities {

    fun launchActivity(context: AppCompatActivity, target: Class<*>?) {
        val intent = Intent(context, target)
        context.startActivity(intent)
        //   AnimationHelper.fade(context);

        //  context.overridePendingTransition(R.anim.slide_from_right,R.anim.slide_from_left);
    }

    companion object {
        fun launchActivity(context: AppCompatActivity, target: Class<*>?) {
            val intent = Intent(context, target)
            context.startActivity(intent)
            //   AnimationHelper.fade(context);

            //  context.overridePendingTransition(R.anim.slide_from_right,R.anim.slide_from_left);
        }

        }
    }

