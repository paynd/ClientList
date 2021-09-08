package name.paynd.android.clientlist.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import name.paynd.android.clientlist.data.Client
import name.paynd.android.clientlist.databinding.RvItemClientBinding

class ClientAdapter(private val editClickListener: (String) -> Unit) :
    ListAdapter<Client, ClientAdapter.ViewHolder>(ClientItemDiffCallback()) {
    inner class ViewHolder(private val binding: RvItemClientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(client: Client) {
            with(binding) {
                tvBirthValue.text = client.dob
                tvWeightValue.text = client.weight
//            binding.image // set image here
                tvEdit.setOnClickListener {

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemClientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class ClientItemDiffCallback : DiffUtil.ItemCallback<Client>() {
    override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Client, newItem: Client): Boolean {
        return oldItem.id == newItem.id
    }
}