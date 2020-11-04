package com.yapp.picon.presentation.nav

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.picon.R
import com.yapp.picon.databinding.CustomEmotionViewBinding
import com.yapp.picon.presentation.CustomEmotion

class CustomEmotionAdapter(
    private val dialog: AlertDialog
): RecyclerView.Adapter<CustomEmotionAdapter.ItemViewHolder>() {
    private var items = listOf(
        CustomEmotion(R.drawable.ic_custom_circle_soft_blue, "새벽 3시"),
        CustomEmotion(R.drawable.ic_custom_circle_cornflower, "구름없는 하늘"),
        CustomEmotion(R.drawable.ic_custom_circle_bluegrey, "아침 이슬"),
        CustomEmotion(R.drawable.ic_custom_circle_very_light_brown, "창문 너머 노을"),
        CustomEmotion(R.drawable.ic_custom_circle_warm_grey, "잔잔한 밤")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        LayoutInflater.from(parent.context).let {
            val binding = CustomEmotionViewBinding.inflate(it, parent, false)
            return ItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.onBind(items[position])

    override fun getItemCount(): Int = items.size

    fun setContents(data: List<CustomEmotion>) {
        items = data
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(
        private var itemBinding: CustomEmotionViewBinding
    ): RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(item: CustomEmotion) {
            itemBinding.ce = item

            // TODO("아이템을 길게 클릭했을 때 dialog를 띄우고, 기능 구현")
            itemView.setOnLongClickListener {
                true
            }
        }
    }
}

