package com.example.mobiledevlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobiledevlab.ui.main.MainFragment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val request = ServiceBuilder.buildService(Tv8Endpoints::class.java)
        val call = request.getArticles(getString(R.string.categoreis))
        call.enqueue(object : Callback<AllArticles>{
            override fun onResponse(call: Call<AllArticles>, response: Response<AllArticles>) {
                if (response.isSuccessful){
                    recycler_view.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = com.example.mobiledevlab.ArticleAdapter(response.body()!!.results)
                    }
                }
            }
            override fun onFailure(call: Call<AllArticles>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
