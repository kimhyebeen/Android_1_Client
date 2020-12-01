package com.yapp.picon.presentation.pingallery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.PinGalleryActivityBinding
import com.yapp.picon.databinding.SearchItemBinding
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.presentation.model.Post
import com.yapp.picon.presentation.search.SearchCode
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
                { item: Map<String, String>?, type: Int -> itemClicked(item, type) }
            ) {}

    private fun itemClicked(item: Map<String, String>?, type: Int) {
        when (type) {
            SearchCode.CLICK_ITEM.code -> clickItem(item)
//            SearchCode.CLICK_ITEM_DELETE.code -> deleteItem(item)
        }
    }

    private fun clickItem(item: Map<String, String>?) {
        item?.let {
            it["id"]?.toInt()?.let { id ->
                vm.getPost(id)?.let { post ->
                    startActivity(
                        Intent(this@PinGalleryActivity, PinGalleryActivity::class.java)
                            .putParcelableArrayListExtra("posts", arrayListOf(post))
                    )
                }
            }
        }
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

    private fun setListeners() {
//        binding.simpleJoinBtnJoin.setOnClickListener { vm.joinMembership() }
    }

    private fun setData() {
        (intent.getSerializableExtra("posts") as ArrayList<Post>).let {
            vm.setPosts(it)
            Log.e("aa12", it.toString())
        }
    }

    override fun initViewModel() {
        binding.setVariable(BR.pinGalleryVM, vm)

//
//        val toastMsgObserver = Observer<String> {
//            showToast(it)
//        }
//        vm.toastMsg.observe(this, toastMsgObserver)

//        val joinYNObserver = Observer<Boolean> {
//            if (it) {
//                val intent = Intent()
//                vm.id.value?.let { id ->
//                    intent.putExtra("id", id)
//                }
//                vm.pw.value?.let { pw ->
//                    intent.putExtra("pw", pw)
//                }
//                setResult(Activity.RESULT_OK, intent)
//                finish()
//            }
//        }
//        vm.joinYN.observe(this, joinYNObserver)
    }
}