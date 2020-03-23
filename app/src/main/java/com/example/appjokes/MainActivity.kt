package com.example.appjokes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appjokes.api.GetJokeInterface
import com.example.appjokes.api.RetrofitClientInstance
import com.example.appjokes.models.JokeContainer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.ResourceSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitClientInstance.retrofitInstance
        val getJokeInterface = retrofit?.create(GetJokeInterface::class.java)
        loadJoke.setOnClickListener {
            progressDialog.visibility = View.VISIBLE
            layoutLoad.visibility = View.GONE
            getJokeInterface?.getJoke()!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    txtJoke.text = result!!.value.joke
                    progressDialog.visibility = View.GONE
                    layoutLoad.visibility = View.VISIBLE
                }, { error ->
                    Toast.makeText(
                        this@MainActivity,
                        "No network connection!!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressDialog.visibility = View.GONE
                    layoutLoad.visibility = View.VISIBLE
                    error.printStackTrace()
                })


        }
    }
}
