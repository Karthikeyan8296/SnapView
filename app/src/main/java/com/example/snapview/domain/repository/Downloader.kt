package com.example.snapview.domain.repository

interface Downloader {
    fun downloadFile(url: String, fileName: String?)
}