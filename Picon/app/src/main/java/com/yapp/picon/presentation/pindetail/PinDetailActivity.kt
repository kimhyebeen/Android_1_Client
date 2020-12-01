package com.yapp.picon.presentation.pindetail

import android.os.Bundle
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.PinDetailActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.presentation.model.Post
import org.koin.androidx.viewmodel.ext.android.viewModel

class PinDetailActivity : BaseActivity<PinDetailActivityBinding, PinDetailViewModel>(
    R.layout.pin_detail_activity
) {

    override val vm: PinDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //todo pin 클릭 시 intent로 넘겨주고 intent로 값 받기 값 = list<post>
        // map 으로 전환하여 세팅해주기

        setData()
        setListeners()
    }

    private fun setData() {
        (intent.getSerializableExtra("posts") as ArrayList<Post>).let {
            vm.setPost(it[0])
        }
    }

    private fun setListeners() {
//        binding.simpleJoinBtnJoin.setOnClickListener { vm.joinMembership() }
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