package edu.temple.audiobbplayer

import org.json.JSONObject
import java.io.Serializable

// Simple data class
data class Book(val id: Int, val title: String, val author: String, val coverURL: String) : Serializable {
    constructor(book: JSONObject) : this(book.getInt("id"), book.getString("title"), book.getString("author"), book.getString("cover_url"))
}