package com.gizemir.cryptomoneyappwithretrofit.service

import com.gizemir.cryptomoneyappwithretrofit.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {
        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    // base url = https://raw.githubusercontent.com/
    //atilsamancioglu/K21-JSONDataSet/master/crypto.json
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")

        //verileri getirme fonskiyonu yazdık(veriler gelirken CryptoModel'in list hali şeklinde gelecek)
    //fun getData(): Call<List<CryptoModel>>
        //RxJava'yı kullanabilmek için observable yaptık
    fun getData(): Observable<List<CryptoModel>>
}