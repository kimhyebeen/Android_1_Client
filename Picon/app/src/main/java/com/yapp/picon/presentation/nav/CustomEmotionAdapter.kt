package com.yapp.picon.presentation.nav

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.picon.R
import com.yapp.picon.databinding.CustomEmotionViewBinding
import com.yapp.picon.presentation.CustomEmotion
import kotlinx.android.synthetic.main.dialog_custom_emotion.view.*

class CustomEmotionAdapter(
    private var items: List<CustomEmotion>,
    private var setRepoItem: (Int, String) -> Unit
): RecyclerView.Adapter<CustomEmotionAdapter.ItemViewHolder>() {
    private lateinit var context: Context
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: Dialog

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context

        LayoutInflater.from(parent.context).let {
            val binding = CustomEmotionViewBinding.inflate(it, parent, false)
            return ItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.onBind(position)

    override fun getItemCount(): Int = 5

    fun setContents(data: List<CustomEmotion>) {
        items = data
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(
        private var itemBinding: CustomEmotionViewBinding
    ): RecyclerView.ViewHolder(itemBinding.root) {

        fun onBind(index: Int) {
            itemView.id = index
            itemBinding.ce = items[index]

            itemView.setOnLongClickListener {
                setDialog(itemView.id)
                dialog.show()
                true
            }
        }

        private fun setDialog(index: Int) {
            val dialogView: View = LayoutInflater.from(context)
                .inflate(R.layout.dialog_custom_emotion, null, false)
            builder = AlertDialog.Builder(context)
            builder.setView(dialogView)
            dialog = builder.create()

            dialogView.dialog_custom_emotion_color_iv.setImageResource(items[index].background)
            dialogView.dialog_custom_emotion_et.setText(items[index].text)

            dialogView.dialog_custom_emotion_confirm_button.setOnClickListener {
                val value: String = dialogView.dialog_custom_emotion_et.text.toString()
                setRepoItem(index, value)
                items[index].text = value
                notifyItemChanged(index)
                dialog.dismiss()
            }

            dialogView.dialog_custom_emotion_cancel_button.setOnClickListener {
                dialog.dismiss()
            }
        }
    }
}

