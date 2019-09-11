package datings.apps.fastdatings.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.android.installreferrer.api.InstallReferrerClient
import com.github.arturogutierrez.Badges
import com.github.arturogutierrez.BadgesNotSupportedException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.onesignal.OneSignal
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import datings.apps.fastdatings.*
import datings.apps.fastdatings._core.BaseFcknActivity
import kotlinx.android.synthetic.main.activity_web_view_fckn.*
import me.leolin.shortcutbadger.ShortcutBadger

class SplashFcknActivity : BaseFcknActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    private lateinit var dataSnapshot: DataSnapshot

    private lateinit var mRefferClient: InstallReferrerClient
    private lateinit var database: DatabaseReference
    val REFERRER_DATA = "REFERRER_DATA"
    val badgeCount = 1

    lateinit var prefs: SharedPreferences

    override fun getContentView(): Int = R.layout.activity_web_view_fckn


    override fun initUI() {
        webView = web_view
        progressBar = progress_bar

        prefs = getSharedPreferences("dating.app.fastdating", Context.MODE_PRIVATE)

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()
    }



    override fun setUI() {
        logEvent("splash-screen")
        val config = YandexMetricaConfig.newConfigBuilder("51d5a407-128d-4f71-a932-fb22e869bb6d").build()
        // Initializing the AppMetrica SDK.F
        YandexMetrica.activate(applicationContext, config)
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this.application)
        webView.webViewClient = object : WebViewClient() {
            @SuppressLint("deprecated")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.contains("/money")) {
                    // task url for web view or browser
//                    val taskUrl = dataSnapshot.child(TASK_URL).value as String
                    val taskUrl = dataSnapshot.child(TASK_URL).value as String

                    startActivity(
                            Intent(this@SplashFcknActivity, ChromeTabsActivity::class.java)
                                    .putExtra(EXTRA_TASK_URL, taskUrl)
                    )
                    finish()

//                    if (value == WEB_VIEW) {
//                            startActivity(
//                                    Intent(this@SplashFcknActivity, ChromeTabsActivity::class.java)
//                                .putExtra(EXTRA_TASK_URL, taskUrl)
//                            )
//                        finish()
//                    } else if (value == BROWSER) {
//                        // launch browser with task url
//                        val browserIntent = Intent(
//                            Intent.ACTION_VIEW,
//                            Uri.parse("")
//                        )
//
//                        logEvent("task-url-browser")
//                        startActivity(browserIntent)
//                        finish()
//                    }
                } else if (url.contains("/main")) {
                    startActivity(Intent(this@SplashFcknActivity, CheTamStartingActivity::class.java))
                    finish()
                }
                progressBar.visibility = View.GONE
                return false
            }
        }

        progressBar.visibility = View.VISIBLE


        val success = ShortcutBadger.applyCount(this, badgeCount)
        if (!success) {
            startService(
                    Intent(this, BadgeIntentService::class.java).putExtra("badgeCount", badgeCount)
            )
        }

        try {
            Badges.setBadge(this, badgeCount)
        } catch (badgesNotSupportedException: BadgesNotSupportedException) {
            Log.d("SplashActivityBadge", badgesNotSupportedException.message)
        }


        getValuesFromDatabase({
            dataSnapshot = it


            // load needed url to determine if user is suitable
            webView.loadUrl(it.child(SPLASH_URL).value as String)
        }, {
            Log.d("SplashErrActivity", "didn't work fetchremote")
            progressBar.visibility = View.GONE
        })
    }
}
