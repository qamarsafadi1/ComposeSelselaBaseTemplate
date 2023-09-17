package com.qamar.composetemplate.util
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import java.io.File

class PdfDownloader(private val context: Context) {

    fun downloadPdfFile(url: String, fileName: String) {

        // Create a download request
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        // Get download service and enqueue the request
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)

        // Register a broadcast receiver to listen for download completion
        val downloadCompleteReceiver = DownloadCompleteReceiver(downloadId, fileName)
        context.registerReceiver(downloadCompleteReceiver, downloadCompleteReceiver.getIntentFilter())
    }

    private inner class DownloadCompleteReceiver(
        private val downloadId: Long,
        private val fileName: String
    ) : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val query = DownloadManager.Query().setFilterById(downloadId)
            val cursor = downloadManager.query(query)

            if (cursor != null && cursor.moveToFirst()) {
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    val downloadFileUri =
                        cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                    cursor.close()

                    if (downloadFileUri != null) {
                        // File downloaded successfully, open it with a PDF viewer app
                        val file = File(Uri.parse(downloadFileUri).path)
                        val pdfIntent = Intent(Intent.ACTION_VIEW)
                        pdfIntent.setDataAndType(Uri.fromFile(file), "application/pdf")
                        pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                        try {
                            context.startActivity(pdfIntent)
                        } catch (e: ActivityNotFoundException) {
                            // PDF viewer app not found, show an error message or prompt user to install a PDF viewer app
                        }
                    }
                }
            }
        }

        fun getIntentFilter(): IntentFilter {
            val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            return intentFilter
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}
