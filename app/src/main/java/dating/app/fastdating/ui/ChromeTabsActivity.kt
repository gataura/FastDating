package dating.app.fastdating.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import dating.app.fastdating.EXTRA_TASK_URL
import dating.app.fastdating.R


class ChromeTabsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chrome_tabs)

        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this@ChromeTabsActivity, R.color.abc_btn_colored_borderless_text_material))
        builder.addDefaultShareMenuItem()
        builder.enableUrlBarHiding()
        builder.setShowTitle(true)
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(intent.getStringExtra(EXTRA_TASK_URL)))
    }
}
