package com.example.fastmeme

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currenturl: String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
    private fun loadmeme(){



// Instantiate the RequestQueue.
        progress.visibility = View.VISIBLE

        val url = "https://meme-api.herokuapp.com/gimme"

// Request a json response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                currenturl = response.getString("url")
                Glide.with(this).load(currenturl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false
                        TODO("Not yet implemented")
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false
                        TODO("Not yet implemented")
                    }
                }).into(memeimageview)
            },
            {
                Toast.makeText(this,"some thing went wrong",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        mysingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
    fun share(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey check out this great Reddit meme $currenturl")
        val chooser=Intent.createChooser(intent,"share with...")
        startActivity(chooser)
    }
    fun next(view: View) {
        loadmeme()
    }
}
