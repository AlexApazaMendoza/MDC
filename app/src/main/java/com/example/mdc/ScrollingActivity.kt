package com.example.mdc

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mdc.databinding.ActivityScrollingBinding
import com.google.android.material.bottomappbar.BottomAppBar

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*with(binding.fab){
            setOnClickListener {
                with(binding.bottomAppBar){
                    fabAlignmentMode = if (fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                        BottomAppBar.FAB_ALIGNMENT_MODE_END
                    } else {
                        BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    }
                }
            }
        }*/

        val bottomAppBarBehavior = binding.bottomAppBar.behavior
        var isSlide = false

        with(binding.fab){
            setOnClickListener {
                isSlide = if (!isSlide) {
                    bottomAppBarBehavior.slideDown(binding.bottomAppBar)
                    true
                } else {
                    bottomAppBarBehavior.slideUp(binding.bottomAppBar)
                    false
                }
            }
        }

        binding.bottomAppBar.setNavigationOnClickListener {
            Snackbar.make(binding.root,R.string.message_action_success,Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab).show()
        }

        binding.content.btnSkip?.setOnClickListener {
            binding.content.cvAd?.visibility = View.GONE
        }

        binding.content.btnBuy?.setOnClickListener {
            Snackbar.make(it,R.string.card_buying,Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .setAction(R.string.card_to_go) {
                    Toast.makeText(this, R.string.card_historial, Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        loadImage(this)

        /*Glide.with(this)
            .load("https://i1.wp.com/www.senpai.com.mx/wp-content/uploads/2020/07/Naruto_-Cosplay-grupal-recrea-de-forma-extraordinaria-al-Equipo-Minato.jpg?fit=1280%2C720&ssl=1")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.content.imgCover!!)*/

        with(binding.content){
            cbEnablePass?.setOnClickListener {
                tilPassword?.isEnabled = !tilPassword!!.isEnabled
            }
        }

        with(binding.content){
            etUrl?.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
                var errorStr:String? = null
                val url = etUrl?.text.toString()
                if (!b){
                    when {
                        url.isEmpty() -> {
                            errorStr = getString(R.string.card_required)
                        }
                        URLUtil.isValidUrl(url) -> {
                            loadImage(this@ScrollingActivity,url)
                        }
                        else -> {
                            errorStr = getString(R.string.card_invalid_url)
                        }
                    }
                }
                tilUrl?.error = errorStr
            }
        }

        with(binding.content){
            binding.content.toggleButton?.addOnButtonCheckedListener { group, checkedId, isChecked ->
                root.setBackgroundColor(
                    when(checkedId){
                        btnRed?.id -> Color.RED
                        btnBlue?.id -> Color.BLUE
                        else -> Color.GREEN
                    }
                )
            }
        }
    }

    private fun loadImage(context:Context, url:String = "https://i1.wp.com/www.senpai.com.mx/wp-content/uploads/2020/07/Naruto_-Cosplay-grupal-recrea-de-forma-extraordinaria-al-Equipo-Minato.jpg?fit=1280%2C720&ssl=1"){
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.content.imgCover!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}