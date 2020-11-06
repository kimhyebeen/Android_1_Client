package com.yapp.picon.presentation.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.SearchActivityBinding
import com.yapp.picon.databinding.SearchItemBinding
import com.yapp.picon.presentation.base.BaseActivity

class SearchActivity : BaseActivity<SearchActivityBinding, SearchViewModel>
    (
    R.layout.search_activity
) {

    override val vm: SearchViewModel by viewModels()

    private val searchAdapter =
        object :
            SearchClickAdapter<SearchItemBinding>(
                R.layout.search_item,
                BR.searchItem,
                { item: Map<String, String>?, type: Int -> itemClicked(item, type) }
            ) {}

    private fun itemClicked(item: Map<String, String>?, type: Int) {
        when (type) {
            SearchCode.CLICK_ITEM.code -> clickItem(item)
            SearchCode.CLICK_ITEM_DELETE.code -> deleteItem(item)
        }
    }

    private fun clickItem(item: Map<String, String>?) {
        item?.let {
            saveSearchWord(it)
            val intent = Intent()
            intent.putExtra("mapX", it["mapX"]?.toDouble())
            intent.putExtra("mapY", it["mapY"]?.toDouble())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun saveSearchWord(item: Map<String, String>?) {
        item?.let { vm.insertSearched(it) }
    }

    private fun deleteItem(item: Map<String, String>?) {
        item?.let { vm.deleteItem(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        setAdapter()
        setOnClickListeners()

        binding.searchEtSearchWord.requestFocus()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
        binding.setVariable(BR.searchVM, vm)

        val toastMsgObserver = Observer<String> {
            showToast(it)
        }

        vm.toastMsg.observe(this, toastMsgObserver)
    }

    private fun setAdapter() {
        binding.searchRecyclerView.adapter = searchAdapter
    }

    private fun closeSearch() {
        setResult(Activity.RESULT_OK)
        finish()
        overridePendingTransition(0, 0)
    }

    private fun setOnClickListeners() {
        binding.searchIbClear.setOnClickListener {
            binding.searchEtSearchWord.text = null
            vm.clearItems()
        }

        binding.searchTvCancel.setOnClickListener {
            closeSearch()
        }

        binding.searchEtSearchWord.setOnEditorActionListener { textView, i, _ ->
            when (i) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    textView.text.toString().let {
                        if (it.isNotBlank()) {
                            vm.requestLocal()
                            true
                        } else {
                            showToast("검색어를 입력해주세요.")
                            false
                        }
                    }
                }
                else -> false
            }
        }

        binding.searchRemainingView.setOnClickListener {
            closeSearch()
        }
    }

    override fun onBackPressed() {
        closeSearch()
//        super.onBackPressed()
    }
}