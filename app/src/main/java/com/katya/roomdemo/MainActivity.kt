package com.katya.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.katya.roomdemo.databinding.ActivityMainBinding
import com.katya.roomdemo.db.Subscriber
import com.katya.roomdemo.db.SubscriberDatabase
import com.katya.roomdemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        subscriberViewModel.message.observe(this, Observer {
         it.getContentIfNotHandled()?.let {
             Toast.makeText(this, it, Toast.LENGTH_LONG).show()
         }
        })

        subscriberViewModel.showSnackBarEvent.observe(this, Observer { })


        subscriberViewModel.showSnackBarEvent.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                    this.findViewById(android.R.id.content),
                    getString(R.string.deleted_message),
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                // Reset state to make sure the snackbar is only shown once, even if the device
                // has a configuration change.
                subscriberViewModel.doneShowingSnackbar()
            }
        })

    }

   private fun initRecyclerView(){
       binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
       adapter = MyRecyclerViewAdapter({selectedItem:Subscriber->listItemClicked(selectedItem)})
       binding.subscriberRecyclerView.adapter = adapter
       displaySubscribersList()
   }

    private fun displaySubscribersList(){
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.i("MYTAG",it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Subscriber){
        //Toast.makeText(this,"selected name is ${subscriber.name}",Toast.LENGTH_LONG).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}