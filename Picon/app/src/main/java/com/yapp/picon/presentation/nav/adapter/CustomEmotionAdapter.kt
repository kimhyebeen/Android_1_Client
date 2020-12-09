package com.yapp.picon.presentation.nav.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import com.yapp.picon.R
import com.yapp.picon.databinding.CustomEmotionViewBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.EmotionEntity
import kotlinx.android.synthetic.main.custom_emotion_view.view.*
import kotlinx.android.synthetic.main.dialog_custom_emotion.view.*

class CustomEmotionAdapter(
    private val context: Context,
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int
): BaseRecyclerView.BaseAdapter<EmotionEntity, CustomEmotionViewBinding>(
    layoutRes, bindingVariabledId
) {
    private lateinit var dialog: Dialog

    fun getItemList():List<EmotionEntity> {
        return items
    }

    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<CustomEmotionViewBinding>,
        position: Int
    ) {
        super.onBindViewHolder(baseViewHolder, position)

        baseViewHolder.itemView.id = position
        baseViewHolder.itemView.emotion_item_color_iv.setImageResource(
            getResource(items[position].color)
        )

        baseViewHolder.itemView.setOnClickListener {
            setDialog(it.id)
            dialog.show()
            setDialogSize()
        }
    }

    private fun setDialog(index: Int) {
        val dialogView: View = LayoutInflater.from(context)
            .inflate(R.layout.dialog_custom_emotion, null, false)
        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)
        dialog = builder.create()
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        setDialogView(dialogView, index)
    }

    private fun setDialogSize() {
        val layoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window!!.attributes)
            width = (274 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        dialog.window?.attributes = layoutParams
    }

    private fun setDialogView(dialogView: View, index: Int) {
        dialogView.apply {
            dialog_custom_emotion_color_iv.setImageResource(
                getResource(items[index].color)
            )
            dialog_custom_emotion_et.setText(items[index].emotion)

            dialog_custom_emotion_confirm_button.setOnClickListener {
                val value: String = dialogView.dialog_custom_emotion_et.text.toString()
                items[index].emotion = value
                notifyItemChanged(index)
                dialog.dismiss()
            }

            dialog_custom_emotion_cancel_button.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    private fun getResource(color: String): Int {
        return when (color) {
            "SOFT_BLUE" -> R.drawable.ic_custom_circle_soft_blue
            "CORN_FLOWER" -> R.drawable.ic_custom_circle_cornflower
            "BLUE_GRAY" -> R.drawable.ic_custom_circle_bluegrey
            "VERY_LIGHT_BROWN" -> R.drawable.ic_custom_circle_very_light_brown
            "WARM_GRAY" -> R.drawable.ic_custom_circle_warm_grey
            else -> throw Exception("CustomEmotionAdapter - getResource - color type is wrong.")
        }
    }
}