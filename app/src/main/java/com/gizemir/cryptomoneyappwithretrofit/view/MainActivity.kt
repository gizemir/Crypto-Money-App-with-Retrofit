package com.gizemir.cryptomoneyappwithretrofit.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.gizemir.cryptomoneyappwithretrofit.R
import com.gizemir.cryptomoneyappwithretrofit.adapter.CryptoAdapter
import com.gizemir.cryptomoneyappwithretrofit.databinding.ActivityMainBinding
import com.gizemir.cryptomoneyappwithretrofit.model.CryptoModel
import com.gizemir.cryptomoneyappwithretrofit.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),  CryptoAdapter.Listener{
    private lateinit var binding: ActivityMainBinding


    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var cryptoAdapter: CryptoAdapter? = null

    //Disposable
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

         compositeDisposable = CompositeDisposable()

        binding.cryptoRecyclerView.layoutManager = LinearLayoutManager(this)
        loadData()

    }

    private  fun loadData(){
        //verilerin yüklenmesi
        //retrofit objesi oluşturduk
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //rxJAva ile yapıldığında kullanılır sadece
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

        //retrofit ile API'yi biribirne bağladık
        //val service = retrofit.create(CryptoAPI::class.java)
       // val call = service.getData()

        //verileri asenkron şekilde yüklemek için
       /* call.enqueue(object: Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)

                        cryptoAdapter = CryptoAdapter(cryptoModels!!, this@MainActivity)
                        binding.cryptoRecyclerView.adapter = cryptoAdapter
                    }
                }

            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                //hata olursa loglarda göster
                t.printStackTrace()
            }

        }) */
    }
    private fun handleResponse(cryptoList: List<CryptoModel>){
        cryptoModels = ArrayList(cryptoList)

        cryptoAdapter = CryptoAdapter(cryptoModels!!, this@MainActivity)
        binding.cryptoRecyclerView.adapter = cryptoAdapter

    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this, "Clicked: ${cryptoModel.currency}", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}