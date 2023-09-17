package com.qamar.composetemplate.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.selsela.cpapp.R
import java.util.*


fun Context.whatsappContact(phone: String,isLink:Boolean=false) {
    var url = ""
    if(isLink)
     url = phone
    else
     url = "https://api.whatsapp.com/send?phone=${phone}"

    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    this.startActivity(i)
}

fun Context.snapchatContact(account: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://snapchat.com/add/"))
        intent.setPackage("com.snapchat.android")
        this.startActivity(intent)
    } catch (e: Exception) {
        this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(account)))
    }
}

fun Context.facebookContact(account: String) {
    try {

        this.packageManager.getPackageInfo("com.facebook.katana", 0)
        val url = "https://www.facebook.com/$account"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url))

        this.startActivity(intent)
    } catch (e: Exception) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.facebook.com/$account")
        )
        this.startActivity(intent)
        e.printStackTrace()

        e.printStackTrace()

    }
}

fun Context.twitterContact(account: String) {
    val intent: Intent
    intent = try {
        // Check if the Twitter app is installed on the phone.
        this.packageManager.getPackageInfo("com.twitter.android", 0)
        Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=$account"))
    } catch (e: java.lang.Exception) {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/$account"))
    }
    this.startActivity(intent)
}

fun Context.instaContact(account: String) {
    val uri = Uri.parse(account)
    val i = Intent(Intent.ACTION_VIEW, uri)
    i.setPackage("com.instagram.android")
    try {
        this.startActivity(i)
    } catch (e: ActivityNotFoundException) {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://instagram.com/_u/${account}")
            )
        )
    }
}

fun Context.phoneContact(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
    this.startActivity(intent)
}

fun Context.openUrl(url: String){
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        "http://$url"
    }

    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}
fun Context.emailContact(phone: String) {
    val email = Intent(Intent.ACTION_SEND)
    email.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(phone))
    email.putExtra(Intent.EXTRA_SUBJECT, "")
    email.putExtra(Intent.EXTRA_TEXT, "")
    email.type = "text/plain"

    this.startActivity(Intent.createChooser(email, getString(R.string.share_using)))
}




fun Activity.launchAppInStore(){
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    } catch (e: ActivityNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
    }
}
fun Activity.launchConsultantAppInStore(){
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.selsela.consultations.employee")))
    } catch (e: ActivityNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.selsela.consultations.employee")))
    }
}

fun Context.shareApp() {
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_using))
        val  shareMessage = "https://play.google.com/store/apps/details?id=$packageName"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        this.startActivity(Intent.createChooser(shareIntent,  getString(R.string.share_using),null))
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

fun Context.launchAppInStore(){
    try {
        this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    } catch (e: ActivityNotFoundException) {
        this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
    }
}

/*fun Context.twitterContact(link: String){
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    this.startActivity(intent)
}*/