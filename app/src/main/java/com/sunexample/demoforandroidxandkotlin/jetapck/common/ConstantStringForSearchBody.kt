package com.sunexample.demoforandroidxandkotlin.jetapck.common

object ConstantStringForSearchBody {
    fun getVideoSearchBody(mToken: String): String {
        return """{
  "context": {
    "client": {
      "hl": "en",
      "gl": "US",
      "clientName": "WEB",
      "clientVersion": "2.20201105",
      "utcOffsetMinutes": 0
    },
    "request": {},
    "user": {}
  },
  "continuation": "$mToken"
}"""
    }

    fun getPlayListBody(mToken: String): String {
        return """{
  "context": {
    "client": {
      "hl": "en",
      "gl": "US",
      "clientName": "WEB",
      "clientVersion": "2.20201105",
      "utcOffsetMinutes": 0
    },
    "request": {},
    "user": {}
  },
  "continuation": "$mToken"
}"""
    }
}