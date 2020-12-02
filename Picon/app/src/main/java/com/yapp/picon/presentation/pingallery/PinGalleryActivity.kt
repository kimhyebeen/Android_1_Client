package com.yapp.picon.presentation.pingallery

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.PinGalleryActivityBinding
import com.yapp.picon.databinding.SearchItemBinding
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.presentation.model.Pin
import com.yapp.picon.presentation.model.Post
import org.koin.androidx.viewmodel.ext.android.viewModel

class PinGalleryActivity : BaseActivity<PinGalleryActivityBinding, PinGalleryViewModel>(
    R.layout.pin_gallery_activity
) {

    override val vm: PinGalleryViewModel by viewModel()

    private val pinGalleryAdapter =
        object :
            PinGalleryClickAdapter<SearchItemBinding>(
                R.layout.pin_gallery_item,
                BR.pinGalleryItem,
                { item: Pin, type: Int -> itemClicked(item, type) }
            ) {}

    private fun itemClicked(item: Pin, type: Int) {
        when (type) {
            PinGalleryCode.CLICK_ITEM.code -> clickItem(item)
            PinGalleryCode.SELECT_ITEM.code -> selectItem()
        }
    }

    private fun clickItem(item: Pin) {
        item.let {
            //todo 핀 상세보기로 이동
            showToast("$it")
//            it["id"]?.toInt()?.let { id ->
//                vm.getPost(id)?.let { post ->
//                    startActivity(
//                        Intent(this@PinGalleryActivity, PinGalleryActivity::class.java)
//                            .putParcelableArrayListExtra("posts", arrayListOf(post))
//                    )
//                }
//            }
        }
    }

    private fun selectItem() {
        vm.selectItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //todo pin 클릭 시 intent로 넘겨주고 intent로 값 받기 값 = list<post>
        // map 으로 전환하여 세팅해주기

        setAdapter()
        setListeners()
        setData()
    }

    private fun setAdapter() {
        binding.pinGalleryRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.pinGalleryRecyclerView.adapter = pinGalleryAdapter
    }

    private fun setData() {
        intent.run {
            getStringExtra("coords")?.let {
                vm.setAddress(it)
            }
            vm.setPosts(getSerializableExtra("posts") as ArrayList<Post>)
        }
    }

    override fun initViewModel() {
        binding.setVariable(BR.pinGalleryVM, vm)

        val toastMsgObserver = Observer<String> {
            showToast(it)
        }
        vm.toastMsg.observe(this, toastMsgObserver)
    }

    private fun setListeners() {
        binding.pinGalleryIbBack.setOnClickListener { finish() }
        binding.pinGalleryTvEdit.setOnClickListener { vm.setEditMode() }
        binding.pinGalleryTvCancel.setOnClickListener { vm.setShowMode() }
        binding.pinGalleryTvDelete.setOnClickListener { vm.deletePost() }
    }

    override fun onBackPressed() {
        if (binding.pinGalleryTvCancel.visibility == View.VISIBLE) {
            vm.setShowMode()
            return
        }
        super.onBackPressed()
    }

}