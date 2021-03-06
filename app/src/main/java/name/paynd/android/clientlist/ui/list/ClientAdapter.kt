package name.paynd.android.clientlist.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import name.paynd.android.clientlist.R
import name.paynd.android.clientlist.data.Client
import name.paynd.android.clientlist.databinding.RvItemClientBinding

class ClientAdapter(private val editClickListener: (Client) -> Unit) :
    ListAdapter<Client, ClientAdapter.ViewHolder>(ClientItemDiffCallback()) {
    inner class ViewHolder(private val binding: RvItemClientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n") // O_o you don't see it!
        fun bind(client: Client) {
            with(binding) {
                tvBirthValue.text = client.dob
                tvWeightValue.text = "${client.weight} ${client.weightUnit}"
                Glide
                    .with(root)
                    .load(client.uri)
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .into(image)
                tvEdit.setOnClickListener {
                    editClickListener.invoke(client)
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