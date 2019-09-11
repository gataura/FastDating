package datings.apps.fastdatings.ui

import datings.apps.fastdatings.R
import datings.apps.fastdatings._core.BaseFcknActivity


/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 3/13/19.
 *
 * TODO: add here content for bots or unsuitable users
 */
class ContentFcknActivity : BaseFcknActivity() {
    override fun getContentView(): Int = R.layout.activity_content_fckn

    override fun initUI() {
    }

    override fun setUI() {
        logEvent("content-screen")
    }
}