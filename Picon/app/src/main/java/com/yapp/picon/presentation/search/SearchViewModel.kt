package com.yapp.picon.presentation.search

import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.domain.entity.SearchedEntity
import com.yapp.picon.domain.usecase.DeleteSearedUseCase
import com.yapp.picon.domain.usecase.GetAllSearedUseCase
import com.yapp.picon.domain.usecase.GetLocalUseCase
import com.yapp.picon.domain.usecase.InsertSearedUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getLocalUseCase: GetLocalUseCase,
    private val getAllSearedUseCase: GetAllSearedUseCase,
    private val insertSearedUseCase: InsertSearedUseCase,
    private val deleteSearedUseCase: DeleteSearedUseCase
) : BaseViewModel() {

    private val _items = MutableLiveData<List<Map<String, String>>>()
    val items: LiveData<List<Map<String, String>>> get() = _items

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private val _isRecentlySearched = MutableLiveData<Boolean>()
    val isRecentlySearched: LiveData<Boolean> get() = _isRecentlySearched

    var searchWord = MutableLiveData<String>()

    init {
        _isRecentlySearched.value = true
        _items.value = mutableListOf()
        loadSearchedWords()
    }

    private fun showToast(msg: String) {
        _toastMsg.value = msg
    }

    private fun htmlToString(html: String): String =
        HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

    private fun loadSearchedWords() {
        viewModelScope.launch {
            getAllSearedUseCase().map {
                mapOf(
                    "title" to it.title,
                    "mapX" to it.mapX,
                    "mapY" to it.mapY,
                    "isSearched" to "true"
                )
            }.run {
                when {
                    size > 5 -> {
                        _items.value = subList(size - 5, size).reversed()
                    }
                    size == 0 -> {
                        _isRecentlySearched.value = false
                        _items.value = mutableListOf()
                    }
                    else -> {
                        _items.value = this.reversed()
                    }
                }
            }
        }
    }

    fun requestLocal() {
        viewModelScope.launch {
            searchWord.value?.let {
                getLocalUseCase(it).let { localResult ->
                    if (localResult.display == 0) {
                        showToast("검색결과가 없습니다.")
                    } else {
                        _isRecentlySearched.value = false
                        _items.value = localResult.items.map { localItem ->
                            mapOf(
                                "title" to htmlToString(localItem.title),
                                "mapX" to localItem.mapx,
                                "mapY" to localItem.mapy,
                                "isSearched" to "false"
                            )
                        }
                    }
                }
            } ?: showToast("검색어를 입력해주세요.")
        }
    }

    fun clearItems() {
        _items.value = mutableListOf()
    }

    private fun itemToEntity(item: Map<String, String>) =
        item["title"]?.let { title ->
            item["mapX"]?.let { mapX ->
                item["mapY"]?.let { mapY ->
                    SearchedEntity(title = title, mapX = mapX, mapY = mapY)
                }
            }
        }

    fun insertSearched(item: Map<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            itemToEntity(item)?.let {
                deleteSearedUseCase(it.title, it.mapX, it.mapY)
                insertSearedUseCase(it)
            }
        }
    }

    fun deleteItem(item: Map<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            itemToEntity(item)?.let {
                deleteSearedUseCase(it.title, it.mapX, it.mapY)
            }
        }
        loadSearchedWords()
    }

}