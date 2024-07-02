package com.example.mobilefinalproject.data

interface Downloader {
    fun DownloadFile(url: String): Long
}