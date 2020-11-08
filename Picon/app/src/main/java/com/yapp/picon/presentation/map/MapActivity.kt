package com.yapp.picon.presentation.map

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.MapActivityBinding
import com.yapp.picon.helper.RequestCodeSet
import com.yapp.picon.presentation.base.BaseMapActivity
import com.yapp.picon.presentation.nav.NavActivity
import com.yapp.picon.presentation.nav.NavTypeStringSet
import com.yapp.picon.presentation.search.SearchActivity


class MapActivity : BaseMapActivity<MapActivityBinding, MapViewModel>(
    R.layout.map_activity,
    R.id.map_frame
), NavigationView.OnNavigationItemSelectedListener {

    override val vm: MapViewModel by viewModels()

    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolBar()
        setOnListeners()
    }

    override fun initViewModel() {
        binding.setVariable(BR.mapVM, vm)

        val toastMsgObserver = Observer<String> {
            showToast(it)
        }

        vm.toastMsg.observe(this, toastMsgObserver)
    }

    private fun setToolBar() {
        setSupportActionBar(binding.mapToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setOnListeners() {
        binding.navView.setNavigationItemSelectedListener(this)

        binding.mapIbMenu.setOnClickListener { binding.mapDrawerLayout.openDrawer(GravityCompat.START) }

        binding.mapIbSearch.setOnClickListener {
            vm.toggleButtonShown()
            startSearchActivity()
        }

        binding.mapIbAdd.setOnClickListener {
            vm.toggleShowPostForm()
        }

        binding.mapIbPostForm.setOnClickListener {
            vm.toggleButtonShown()
            vm.toggleShowPostFormBtn()
        }

        binding.mapTvPostFormTempSave.setOnClickListener {
            //todo 위도 경우 만 전송
        }

        binding.mapTvPostFormAddPicture.setOnClickListener {
            checkStoragePermission()
        }

        binding.mapIbPinClose.setOnClickListener {
            vm.toggleShowPostFormBtn()
            vm.toggleShowPostForm()
            vm.toggleButtonShown()
        }
    }

    private fun checkStoragePermission() {
        val storagePermissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                //todo start postActivity
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                showToast("저장 권한을 거부하셨습니다. 해당 기능을 사용할 수 없습니다.")
            }
        }

        TedPermission.with(this)
            .setPermissionListener(storagePermissionListener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .check()
    }

    private fun startSearchActivity() {
        startActivityForResult(
            Intent(this, SearchActivity::class.java),
            RequestCodeSet.SEARCH_REQUEST_CODE.code
        )
    }

    private fun startNavActivity(type: String) {
        startActivity(
            Intent(this, NavActivity::class.java)
                .putExtra("type", type)
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.map_nav_customize_emotion_name -> startNavActivity(NavTypeStringSet.CustomEmotion.type)
            R.id.map_nav_setting -> startNavActivity(NavTypeStringSet.Setting.type)
        }
        return true
    }

    private fun settingOptionToMap() {
        naverMap.apply {
            mapType = NaverMap.MapType.Navi
            isNightModeEnabled = true
            uiSettings.isZoomControlEnabled = false
            uiSettings.isZoomGesturesEnabled = true
            setLayerGroupEnabled("LAYER_GROUP_BUILDING", true)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        this.naverMap.setOnMapClickListener { _, _ ->
            vm.isShownPostForm.value?.let { isShownPostForm ->
                vm.isShownPostFormBtn.value?.let { isShownPostFormBtn ->
                    if (!(isShownPostForm or isShownPostFormBtn)) {
                        vm.toggleButtonShown()
                    }
                }
            }
        }

        settingOptionToMap()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodeSet.SEARCH_REQUEST_CODE.code -> {
                    vm.toggleButtonShown()
                    data?.let {
                        val mapX = it.getDoubleExtra("mapX", 0.0)
                        val mapY = it.getDoubleExtra("mapY", 0.0)
                        val tm128 = Tm128(mapX, mapY)
                        val latLng = tm128.toLatLng()
                        val cameraUpdate = CameraUpdate.scrollTo(latLng)
                        naverMap.moveCamera(cameraUpdate)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (binding.mapDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mapDrawerLayout.closeDrawer(GravityCompat.START)
            return
        }
        vm.isShownPostFormBtn.value?.let {
            if (it) {
                vm.toggleButtonShown()
                vm.toggleShowPostFormBtn()
                return@onBackPressed
            }
        }
        vm.isShownPostForm.value?.let {
            if (it) {
                vm.toggleShowPostForm()
                return@onBackPressed
            }
        }
        super.onBackPressed()
    }

}