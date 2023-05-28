package com.tesera.data.storage

interface TeseraPrefs {
    var authToken: String
    var username: String
    var searchHistory: Set<String>
}