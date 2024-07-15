package com.joybox.joyplug.services

import org.json.JSONArray
import java.net.URL

interface NetService {
    fun GET(url : URL) : String
    fun POST(url : URL, params : JSONArray) : String
}