package com.ardy.jobsubmis.connect

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import android.content.Intent
import com.ardy.jobsubmis.api
import com.ardy.jobsubmis.data.AdapterSource
import com.ardy.jobsubmis.data.NewsData

class MainViewModel: ViewModel() {
//    companion object {
//        private val TAG = MainActivity::class.java.simpleName
//    }

    val listGith = MutableLiveData<ArrayList<NewsDataApi>>()



    fun getGith(): LiveData<ArrayList<NewsDataApi>> {
        return listGith
    }



    fun setItems() {


            val listuser = ArrayList<NewsDataApi>()
            val client = AsyncHttpClient()
            val url =
                "https://newsapi.org/v2/everything?from=2021-09-01&domains=bbc.com&language=en&sortBy=publishedAt&apiKey=cc29820a395940269cdd40ed4d21c3e3"
            client.addHeader("Authorization", "token cc29820a395940269cdd40ed4d21c3e3")
            client.addHeader("User-Agent", "request")
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)

                    try {
                        val responseObject = JSONObject(result)
                        val items = responseObject.getJSONArray("articles")
                        for (i in 0 until items.length()) {
                            val item = items.getJSONObject(i)
                            val username = item.getString("title")
                            val avatar = item.getString("urlToImage")
                            val desc = item.getString("description")
                            val time = item.getString("publishedAt")
                            val url = item.getString("url")
                            val tanggal = time.split("T").toTypedArray()
                            val user = NewsDataApi()
                            user.namenews = username
                            user.photo = avatar
                            user.desk = desc
                            user.date = tanggal[0]
                            user.url  = url
                            listuser.add(user)

                        }
                        listGith.postValue(listuser)


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
                ) {


                    val errorMessage = when (statusCode) {
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${error.message}"
                    }
                    Log.d("onFailure", errorMessage)
                }
            })
        }


    fun setItemsCnn() {


        val listuser = ArrayList<NewsDataApi>()
        val client = AsyncHttpClient()
        val url =
            "https://newsapi.org/v2/everything?from=2021-09-01&domains=cnn.com&language=en&sortBy=publishedAt&apiKey=cc29820a395940269cdd40ed4d21c3e3"
        client.addHeader("Authorization", "token cc29820a395940269cdd40ed4d21c3e3")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("articles")
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("title")
                        val avatar = item.getString("urlToImage")
                        val desc = item.getString("description")
                        val time = item.getString("publishedAt")
                        val url = item.getString("url")
                        val tanggal = time.split("T").toTypedArray()
                        val user = NewsDataApi()
                        user.namenews = username
                        user.photo = avatar
                        user.desk = desc
                        user.date = tanggal[0]
                        user.url  = url
                        listuser.add(user)
                    }
                    listGith.postValue(listuser)


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {


                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("onFailure", errorMessage)
            }
        })
    }
    fun setItemsSky() {


        val listuser = ArrayList<NewsDataApi>()
        val client = AsyncHttpClient()
        val url =
            "https://newsapi.org/v2/everything?from=2021-09-01&domains=skysports.com&language=en&sortBy=publishedAt&apiKey=cc29820a395940269cdd40ed4d21c3e3"
        client.addHeader("Authorization", "token cc29820a395940269cdd40ed4d21c3e3")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("articles")
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("title")
                        val avatar = item.getString("urlToImage")
                        val desc = item.getString("description")
                        val time = item.getString("publishedAt")
                        val url = item.getString("url")
                        val tanggal = time.split("T").toTypedArray()
                        val user = NewsDataApi()
                        user.namenews = username
                        user.photo = avatar
                        user.desk = desc
                        user.date = tanggal[0]
                        user.url  = url
                        listuser.add(user)
                    }
                    listGith.postValue(listuser)


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {


                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("onFailure", errorMessage)
            }
        })
    }

}