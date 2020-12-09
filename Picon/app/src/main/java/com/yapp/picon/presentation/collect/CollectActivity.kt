package com.yapp.picon.presentation.collect

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.CollectActivityBinding
import com.yapp.picon.databinding.CollectItemBinding
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.presentation.model.Pin
import com.yapp.picon.presentation.model.Post
import com.yapp.picon.presentation.postdetail.PostDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CollectActivity : BaseActivity<CollectActivityBinding, CollectViewModel>(
    R.layout.collect_activity
) {

    override val vm: CollectViewModel by viewModel()

    private val collectAdapter =
        object :
            CollectClickAdapter<CollectItemBinding>(
                R.layout.collect_item,
                BR.collectItem,
                { item: Pin -> itemClicked(item) }
            ) {}

    private fun itemClicked(item: Pin) {
        clickItem(item)
    }

    private fun startPostDetailActivity(post: Post) {
        Intent(this, PostDetailActivity::class.java).apply {
            putExtra("post", post)
        }.let {
            startActivity(it)
        }
    }

    private fun clickItem(item: Pin) {
        item.let {
            it.id?.let { id ->
                vm.getPost(id)?.let { post ->
                    startPostDetailActivity(post)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAdapter()
        setListeners()
        setPosts()
    }

    private fun setAdapter() {
        binding.collectRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.collectRecyclerView.adapter = collectAdapter
    }

    private fun setListeners() {
        binding.collectIbBack.setOnClickListener { finish() }
        binding.collectIbFilter.setOnClickListener {
            //todo 필터 구현하기
            showToast("필터는 현재 구현 중에 있습니다.")
        }
    }

    private fun setPosts() {
        vm.setPosts()
    }

    override fun initViewModel() {
        binding.setVariable(BR.collectVM, vm)

        val toastMsgObserver = Observer<String> {
            showToast(it)
        }
        vm.toastMsg.observe(this, toastMsgObserver)
    }

}