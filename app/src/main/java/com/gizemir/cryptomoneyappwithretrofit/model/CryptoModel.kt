package com.gizemir.cryptomoneyappwithretrofit.model

import com.google.gson.annotations.SerializedName

data class CryptoModel (
    //serializedname içerisine json'daki değişkenle aynı isim yazılır
   // @SerializedName("currency")
    val currency:String,
   // @SerializedName("price")
    val price:String
)
