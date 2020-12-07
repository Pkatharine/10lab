package com.katya.roomdemo


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.katya.roomdemo.databinding.ListItemBinding
import com.katya.roomdemo.db.Subscriber

class MyRecyclerViewAdapter(private val clickListener:(Subscriber)->Unit)
    : ListAdapter<Subscriber, MyViewHolder>(SubscriberDiffCallback())
{
    private val subscribersList = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
      val binding : ListItemBinding =
          DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
      return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return subscribersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.bind(subscribersList[position],clickListener)
    }

    fun setList(subscribers: List<Subscriber>) {
        subscribersList.clear()
        subscribersList.addAll(subscribers)

    }

}


//де і як намалювати айтем в лісті
class MyViewHolder(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(subscriber: Subscriber,clickListener:(Subscriber)->Unit){
          binding.nameTextView.text = subscriber.name
          binding.emailTextView.text = subscriber.email
          binding.listItemLayout.setOnClickListener{
             clickListener(subscriber)
          }
    }
    companion object {
        fun from(parent: ViewGroup): MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemBinding.inflate(layoutInflater, parent, false)
            return MyViewHolder(binding)
        }
    }
}

class SubscriberDiffCallback :
    DiffUtil.ItemCallback<Subscriber>() {
    override fun areItemsTheSame(oldItem: Subscriber, newItem: Subscriber): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Subscriber, newItem: Subscriber): Boolean {
        return oldItem == newItem
    }
}