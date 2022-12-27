package com.bitta.app.utils

import org.json.JSONArray
import org.json.JSONObject

fun jsonOf(vararg pairs: Pair<String, Any>) = JSONObject().apply {
    pairs.forEach { (key, value) -> put(key, value) }
}

fun jsonOf(vararg items: Any) = JSONArray(items)