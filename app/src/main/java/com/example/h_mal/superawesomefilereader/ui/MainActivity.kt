package com.example.h_mal.superawesomefilereader.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.h_mal.superawesomefilereader.ui.viewmodel.MainViewModelFactory
import com.example.h_mal.superawesomefilereader.R
import com.example.h_mal.superawesomefilereader.databinding.ActivityMainBinding
import com.example.h_mal.superawesomefilereader.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(),
    ExecutionListener, KodeinAware {

    override val kodein by kodein()
    private val factory : MainViewModelFactory by instance()

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        viewModel = ViewModelProviders.of(this,factory).get(MainViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.callback = this


        CoroutineScope(Dispatchers.Main).launch {
            viewModel.item.await().observe(this@MainActivity, Observer {
                if (!it.isNullOrEmpty()){
                    val a = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,it)
                    list_view.adapter = a
                }
            })
        }
    }

    override fun onStarted() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progress_circular.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress_circular.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.clear()
        }

    }
}
