package com.example.snapview.data.repository

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.example.snapview.domain.repository.Downloader
import java.io.File

class DownloaderImpl(context: Context) : Downloader {

    private val downloaderManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    override fun downloadFile(url: String, fileName: String?) {
        try {
            val title = fileName ?: "New Image"
            val req = DownloadManager.Request(url.toUri())
                .setMimeType("image/*")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle(title)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + title + ".jpg"
                )
            downloaderManager.enqueue(req)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}