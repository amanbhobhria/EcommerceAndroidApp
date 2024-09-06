package com.example.pharmaecomapp.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.text.Html
import android.text.format.Formatter
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.example.pharmaecomapp.apiHelper.ApiClient
import com.example.pharmaecomapp.apiHelper.ApiService
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Random

class Utility {

    companion object {

        fun changeStatusBarColor(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.BLACK
            }
        }

        fun convertInMils(strDate: String): Boolean {

            var isCorrectTime = false;
            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm a");
            val date = sdf.parse(strDate);
            val millis = date.getTime();

            val now = Calendar.getInstance();
            now.add(Calendar.HOUR, 2);
            if (millis > now.timeInMillis) {
                isCorrectTime = true
            }
            return isCorrectTime
        }

        fun isBetween12Hr(strDate: String, minute:String): Boolean {

            var isCorrectTime = false;
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm");
            val date = sdf.parse(strDate);
            val millis = date.getTime();

            val now = Calendar.getInstance();
            now.add(Calendar.HOUR, 12);
//            now.add(Calendar.MINUTE, minute.toInt());
            if (millis > now.timeInMillis) {
                isCorrectTime = true
            }
            return isCorrectTime
        }


        fun isTimeBefore2Hr(strDate: String, minute:String): Boolean {

            var isCorrectTime = false;
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm");
            val date = sdf.parse(strDate);
            val millis = date.getTime();

            val now = Calendar.getInstance();
            now.add(Calendar.HOUR, 2);
//            now.add(Calendar.MINUTE, minute.toInt());
            if (millis > now.timeInMillis) {
                isCorrectTime = true
            }
            return isCorrectTime
        }

        fun convertInMils2(strDate: String): Long {

            var isCorrectTime = false;
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm");
            val date = sdf.parse(strDate);
            val millis = date.getTime();

            return millis
        }


        fun isBetween6Month(): Long {

            val now = Calendar.getInstance();
            now.add(Calendar.MONTH,6 );

            Log.e("valid6: ",now.timeInMillis.toString() )

            return now.timeInMillis
        }

        fun checkCurrentDate(createDate:String,todaysDate:String): Boolean{

            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val strDate = sdf.parse(createDate)
            val strDateToday = sdf.parse(todaysDate)

            return (strDate.time==strDateToday.time)
        }

        fun isTimeAfterHr2(strDate: String): Boolean {

            var isCorrectTime = false;
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm");
            val date = sdf.parse(strDate);
            val millis = date.getTime();

            val now = Calendar.getInstance();
            now.add(Calendar.HOUR, 2);
//            now.add(Calendar.MINUTE, minute.toInt());
            if (millis < now.timeInMillis) {
                isCorrectTime = true
            }
            return isCorrectTime
        }


        fun validDaywise(date:String,dayCount:String): Long {
            var timeDD=changeDateFormateForApi(date,
                "dd/MM/yyyy hh:mm a", "dd")
            var timeMM=changeDateFormateForApi(date,
                "dd/MM/yyyy hh:mm a", "MM")

            var timeYYYY=changeDateFormateForApi(date,
                "dd/MM/yyyy hh:mm a", "yyyy")

            val now = Calendar.getInstance();
            now.set(timeYYYY.toInt(),timeMM.toInt()-1,timeDD.toInt())

            if (dayCount.toInt()==0) {
                now.add(Calendar.MONTH,6);
            }else{
                now.add(Calendar.DAY_OF_YEAR, dayCount.toInt());
            }

            Log.e("validDayWise: ",now.timeInMillis.toString())

            return now.timeInMillis
        }


        fun getCurrentStamp(): String {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("dd-MM-yyyy HH:mm:ss a")

            Log.e("Current time => ", "" + df.format(c.time))
            return df.format(c.time)
        }


        fun setHtmlText(tvText: TextView, content: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvText.text = Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)
            } else {
                tvText.text = Html.fromHtml(content)
            }
        }
        fun getUrlImage( imageurl: String): File? {
            var file: File?=null
            Handler().postDelayed(object:Runnable{
                override fun run() {
                    var stream: InputStream? = null
                    try {
                        Log.i("URL", imageurl)
                        val url = URL(imageurl)
                        val urlConn: URLConnection = url.openConnection()
                        val httpConn: HttpURLConnection = urlConn as HttpURLConnection
                        httpConn.connect()
                        stream= httpConn.getInputStream()
                    } catch (e: MalformedURLException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val bmpimg: Bitmap = BitmapFactory.decodeStream(stream)
                    file= File(Environment.getExternalStorageDirectory().toString()+""+ File.separator +"temporary_file.jpg")
                    val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
                    bmpimg.compress(Bitmap.CompressFormat.JPEG, 100, os)
                    os.close()
                }
            },1)

            return file
        }

        fun changeDateFormateForApi(date: String, currentFormate: String, changeFormate: String): String {
            var date = date
            var spf = SimpleDateFormat(currentFormate)
            var newDate: Date? = null
            try {
                newDate = spf.parse(date)
                spf = SimpleDateFormat(changeFormate)
                date = spf.format(newDate)
            } catch (e: Exception) {
                Log.e("changeDateteForApi: ", e.localizedMessage)
            }
            return "" + date
        }



        fun setAnimation(itemView: View, i: Int) {
            var i = i
            val on_attach = true
            val DURATION = 500
            i = i
            if (!on_attach) {
                i = -1
            }
            val isNotFirstItem = i == -1
            i++
            itemView.alpha = 0f
            val animatorSet = AnimatorSet()
            val animator = ObjectAnimator.ofFloat(itemView, "alpha", 0f, 0.5f, 1.0f)
            ObjectAnimator.ofFloat(itemView, "alpha", 0f).start()

            val dur: Long
            if (isNotFirstItem) {
                dur = (DURATION / 2).toLong()
            } else {
                dur = (i * DURATION / 3).toLong()
            }
            animator.startDelay = dur
            animator.duration = 500
            animatorSet.play(animator)
            animator.start()
        }

        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }


        fun showSnackBar(context: Activity, msg: String) {
            Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
        }

        fun getObjKeys(issueObj: JSONObject): String {

            var key=""
            val iterator: Iterator<*> = issueObj.keys()
            while (iterator.hasNext()) {
                key = iterator.next() as String
                Log.e("keeey" + key, issueObj.getString(key))
            }

            return key
        }


        /*   fun makeEmail(context: Context) {
               try {
                   val intent = Intent(Intent.ACTION_VIEW)
                   intent.setType("plain/text")
                   intent.setData(Uri.parse(ApiContants.EmailAddress))
                   intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail")
                   intent.putExtra(Intent.EXTRA_SUBJECT, "Support request from" + getUser().name);
                   intent.putExtra(Intent.EXTRA_TEXT, "Regards,\n${getUser().name}\n" + "");
                   context.startActivity(intent);
               } catch (e: java.lang.Exception) {
                   val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                           "mailto", ApiContants.EmailAddress, null));
                   intent.putExtra(Intent.EXTRA_SUBJECT, "Support request from" + getUser().name);
                   intent.putExtra(Intent.EXTRA_TEXT, "Regards,\n${getUser().name}\n");
                   context.startActivity(Intent.createChooser(intent, "Send email..."));
               }
           }*/

        fun fullScreenDialog(layoutId: Int, activity: Activity): Dialog {
            val dialog = Dialog(activity)
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(layoutId)
            dialog.setCancelable(false)
            val window = dialog.window
            window!!.setGravity(Gravity.CENTER)
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width = (size.x * 0.94).toInt()
            val height = (size.y * 0.94).toInt()
            dialog.show()
            dialog.window!!.setLayout(width, height)

            return dialog
        }


        fun getParmMap(): MutableMap<String, String> {
            return HashMap<String, String>()
        }


//        fun getApiClient(context: Context): ApiService? {
//            return ApiClient().getClient(context).create(ApiService::class.java!!)
//        }

        /*    fun getUser(): UserBean.ResultBean {

                var userBean = Gson().fromJson<UserBean>(PrefManager.getString(ApiContants.UserDetails, ""), UserBean::class.java)

                if (userBean!=null&&userBean.result!=null) {
                    return userBean.result
                }else{
                    return UserBean.ResultBean()
                }
            }

            fun getUserWallet():String {

                var userBean = Gson().fromJson<UserBean>(PrefManager.getString(ApiContants.UserDetails, ""), UserBean::class.java)

                if (userBean!=null&&userBean.availbleamount!=null) {
                    return userBean.availbleamount
                }else{
                    return "0"
                }
            }*/


        fun explainSetting(activity: Activity) {
            val dialogS = AlertDialog.Builder(activity)
            dialogS.setMessage("Go to setting and give some mandatory permissions to continue. Do you want to go to app settings?")
                .setPositiveButton("Yes") { paramDialogInterface, paramInt ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", activity.packageName, null)
                    intent.data = uri
                    activity.startActivity(intent)
                    activity.finish()
                }
                .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> activity.finish() }
            dialogS.show()

        }

        fun getViewHeight(view: View): Int {
            val wm = view.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val deviceWidth: Int
            deviceWidth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                val size = Point()
                display.getSize(size)
                size.x
            } else {
                display.width
            }
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST)
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view.measure(widthMeasureSpec, heightMeasureSpec)
            Log.d("viewheight",view.measuredHeight.toString())
            return view.measuredHeight //        view.getMeasuredWidth();
        }

        fun getIPaddress(activity: Activity): String {
            val wifiManager = activity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val ipAddress = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
            Log.e("id", ipAddress)
            return ipAddress
        }

        /* fun setImage(context: Context, path: Int, ivImage: ImageView) {
             Picasso.with(context).load(path).placeholder(R.drawable.loading_place).into(ivImage)
         }*/

        /*  fun setImage(context: Context, path: String, ivImage: ImageView) {
              Picasso.with(context).load(path).placeholder(R.drawable.loading_place).into(ivImage, object : com.squareup.picasso.Callback {
                  override fun onSuccess() {
                      //set animations here
                  }

                  override fun onError() {
                      //do smth when there is picture loading error
                      ivImage.setImageDrawable(context.resources.getDrawable(R.drawable.loading_error_place))
                  }
              })
                   //   placeholder(R.drawable.loading_place).into(ivImage)
          }

          fun setImageBanner(context: Context, path: String, ivImage: ImageView) {
              Picasso.with(context).load(path).resize(1080,450).placeholder(R.drawable.loading_place).into(ivImage, object : com.squareup.picasso.Callback {
                  override fun onSuccess() {
                      //set animations here
                  }

                  override fun onError() {
                      //do smth when there is picture loading error
                      ivImage.setImageDrawable(context.resources.getDrawable(R.drawable.loading_error_place))
                  }
              })
                   //   placeholder(R.drawable.loading_place).into(ivImage)
          }
  */


        fun getOtpCode(): String {
            val rnd = Random();
            val number = rnd.nextInt(999999);
            return String.format("%06d", number);
        }

        /*fun pickImage(activity: Activity) {
            ImagePicker.with(activity) //  Initialize ImagePicker with activity or fragment context
                    .setToolbarColor("#FF5722") //  Toolbar color
                    .setStatusBarColor("#FF5722") //  StatusBar color (works with SDK >= 21  )
                    .setToolbarTextColor("#FFFFFF") //  Toolbar text color (Title and Done button)
                    .setToolbarIconColor("#FFFFFF") //  Toolbar icon color (Back and Camera button)
                    .setProgressBarColor("#FF5722") //  ProgressBar color
                    .setBackgroundColor("#FFFFFF") //  Background color
                    .setCameraOnly(false) //  Camera mode
                    .setMultipleMode(false) //  Select multiple images or single image
                    .setFolderMode(false) //  Folder mode
                    .setShowCamera(true) //  Show camera button
                    .setFolderTitle("Albums") //  Folder title (works with FolderMode = true)
                    .setImageTitle("Galleries") //  Image title (works with FolderMode = false)
                    .setDoneTitle("Done") //  Done button title
                    .setLimitMessage("") // Selection limit message
                    .setMaxSize(1) //  Max images can be selected
                    .setAlwaysShowDoneButton(true) //  Set always show done button in multiple mode
                    .setRequestCode(100) //  Set request code, default Config.RC_PICK_IMAGES
                    .start()
        }*/

        /*fun compressImage(filepath: String): File? {
            var file: File? = null
            file = try {
                Compressor(AntimatterApp.appContext).compressToFile(File(filepath))
            } catch (e: java.lang.Exception) {
                File(filepath)
            }
            return file
        }*/



        fun makeCall(context: Context, mob: String) {
            try {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$mob")
                context.startActivity(intent)
            } catch (e: java.lang.Exception) {
                Toast.makeText(context,
                    "Unable to call at this time", Toast.LENGTH_SHORT).show()
            }
        }






    }


}