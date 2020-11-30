package com.yapp.picon.presentation.map

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.MapActivityBinding
import com.yapp.picon.helper.LocationHelper
import com.yapp.picon.helper.RequestCodeSet
import com.yapp.picon.presentation.base.BaseMapActivity
import com.yapp.picon.presentation.model.PostMarker
import com.yapp.picon.presentation.model.EmotionEntity
import com.yapp.picon.presentation.nav.NavActivity
import com.yapp.picon.presentation.nav.NavTypeStringSet
import com.yapp.picon.presentation.nav.adapter.NavHeaderEmotionAdapter
import com.yapp.picon.presentation.nav.repository.EmotionDatabaseRepository
import com.yapp.picon.presentation.post.PostActivity
import com.yapp.picon.presentation.profile.MyProfileActivity
import com.yapp.picon.presentation.search.SearchActivity
import com.yapp.picon.presentation.util.NaverCamera
import com.yapp.picon.presentation.util.clusterMarker
import com.yapp.picon.presentation.util.pinMarker
import org.koin.androidx.viewmodel.ext.android.viewModel
import ted.gun0912.clustering.naver.TedNaverClustering
import kotlinx.android.synthetic.main.map_nav_head.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MapActivity : BaseMapActivity<MapActivityBinding, MapViewModel>(
    R.layout.map_activity,
    R.id.map_frame
), NavigationView.OnNavigationItemSelectedListener {
  
    override val vm: MapViewModel by viewModel()

    private lateinit var naverMap: NaverMap
    private lateinit var emotionDatabaseRepository: EmotionDatabaseRepository
    private lateinit var headerEmotionAdapter: NavHeaderEmotionAdapter

    private val currentLocationMarker = Marker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        emotionDatabaseRepository = EmotionDatabaseRepository(application)
        headerEmotionAdapter = NavHeaderEmotionAdapter(R.layout.map_nav_head_emotion_item, BR.headEmoItem)
        /*
        todo
            1. 시작 시 지도 카메라 한반도 전체 보여주기
            2. 전체 포스트 조회 및 마커로 뿌려주기
            3. 해당 마커는 클러스터링 라이브러리를 이용한다.
            4. 마커 클릭 시 엑티비티 이동
            5. 다시 화면으로 돌아왔을 경우 전체 포스트 다시 조회
         */

        setToolBar()
        setOnListeners()
    }

    override fun onResume() {
        super.onResume()

        setNavHeader()
    }

    override fun initViewModel() {
        binding.setVariable(BR.mapVM, vm)

        val toastMsgObserver = Observer<String> {
            showToast(it)
        }
        vm.toastMsg.observe(this, toastMsgObserver)

        val loadPostYNObserver = Observer<Boolean> {
            if (it) {
                showMarkers()
                vm.completeLoadPost()
            }
        }
        vm.postLoadYN.observe(this, loadPostYNObserver)
    }

    private fun setToolBar() {
        setSupportActionBar(binding.mapToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setOnListeners() {
        binding.navView.setNavigationItemSelectedListener(this)

        binding.mapIbMenu.setOnClickListener { binding.mapDrawerLayout.openDrawer(GravityCompat.START) }

        binding.mapIbSearch.setOnClickListener {
            vm.toggleShowBtnYN()
            startSearchActivity()
        }

        binding.mapIbAdd.setOnClickListener {
            vm.toggleShowBtnYN()
            vm.toggleShowPinYN()
        }

        binding.mapIbPostPin.setOnClickListener {
            checkStoragePermission()
        }

        binding.mapIbPinClose.setOnClickListener {
            vm.toggleShowPinYN()
            vm.toggleShowBtnYN()
        }

        binding.mapIbCurrentLocation.setOnClickListener {
            setCurrentLocation()
        }
    }

    private fun setNavHeader() {
        setUserCircleImage()
        setUserNameText()
        setAlarmImageEvent()
        setEmotionActivityStart()
        setHeadEmotionAdapter()
    }

    private fun setUserCircleImage() {
        // todo - 유저 프로필 사진으로 변경
        Glide.with(this)
            .load(R.drawable.profile_pic)
            .circleCrop()
            .into(binding.navView.getHeaderView(0).nav_head_circle_user_image)

        binding.navView.getHeaderView(0).nav_head_circle_user_image.setOnClickListener {
            startActivity(
                Intent(this, MyProfileActivity::class.java)
            )
        }
    }

    private fun setUserNameText() {
        // todo - binding.navView.getHeaderView(0).nav_head_user_name_text에 '(닉네임)님' 적용
    }

    private fun setAlarmImageEvent() {
        // todo - 알람 아이콘 맞는 걸로 변경
        // todo - 알람 아이콘 기능 설정
    }

    private fun setEmotionActivityStart() {
        binding.navView.getHeaderView(0).nav_head_emotion_button.setOnClickListener {
            startNavActivity(NavTypeStringSet.CustomEmotion.type)
        }
    }

    private fun setHeadEmotionAdapter() {
        binding.navView.getHeaderView(0).nav_head_emotion_rv.apply {
            adapter = headerEmotionAdapter
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }
        emotionDatabaseRepository.getAll().observe(this, { origin ->
            val list = mutableListOf<EmotionEntity>()
            origin.map { list.add(it) }
            headerEmotionAdapter.setItems(list)
            headerEmotionAdapter.notifyDataSetChanged()

            if (origin.isEmpty()) {
                initDatabase()
            }
        })
    }

    private fun initDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            emotionDatabaseRepository.insert(EmotionEntity(1, "SOFT_BLUE", "새벽 3시"))
            emotionDatabaseRepository.insert(EmotionEntity(2, "CORN_FLOWER", "구름없는 하늘"))
            emotionDatabaseRepository.insert(EmotionEntity(3, "BLUE_GRAY", "아침 이슬"))
            emotionDatabaseRepository.insert(EmotionEntity(4, "VERY_LIGHT_BROWN", "창문 너머 노을"))
            emotionDatabaseRepository.insert(EmotionEntity(5, "WARM_GRAY", "잔잔한 밤"))
        }
    }

    private fun setCurrentLocation() {
        LocationHelper(this).requestLocationPermissions()?.let {
            val cameraUpdate = CameraUpdate.scrollAndZoomTo(
                LatLng(it.latitude, it.longitude),
                NaverCamera.defaultZoom
            )
                .animate(CameraAnimation.Fly, NaverCamera.defaultAnimateTime)
            naverMap.moveCamera(cameraUpdate)

            currentLocationMarker.map = null
            currentLocationMarker.run {
                icon = OverlayImage.fromResource(R.drawable.ic_map_now)
                position = LatLng(it.latitude, it.longitude)
                this.map = naverMap
            }
        }
    }

    private fun checkStoragePermission() {
        val storagePermissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                startPostActivity()
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

    private fun startPostActivity() {
        //todo reverse geolocaion 을 이용해서 위치 넘겨주기
        val intent = Intent(this@MapActivity, PostActivity::class.java)
        intent.putExtra("lat", naverMap.cameraPosition.target.latitude)
        intent.putExtra("lng", naverMap.cameraPosition.target.longitude)
        intent.putExtra("address", vm.address.value)

        startActivityForResult(
            intent,
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
            R.id.map_nav_view_travel_statistic -> startNavActivity(NavTypeStringSet.Statistic.type)
        }
        return true
    }

    private fun settingOptionToMap() {
        naverMap.apply {
            mapType = NaverMap.MapType.Navi
            isNightModeEnabled = true
            uiSettings.isZoomControlEnabled = false
            uiSettings.isZoomGesturesEnabled = true

            lightness = -0.5f
            cameraPosition = NaverCamera.startPosition
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        this.naverMap.setOnMapClickListener { _, _ ->
            vm.showBtnYN.value?.let {
                vm.toggleShowBtnYN()
            }
        }

        /* 카메라 로깅
        this.naverMap.addOnCameraChangeListener { reason, animated ->
            Log.e("NaverMap", "카메라 변경 - reson: $reason, animated: $animated")
            Log.e("NaverMap", "카메라 : ${this.naverMap.cameraPosition}")
        }
         */

        this.naverMap.addOnCameraIdleListener {
            vm.showPinYN.value?.let {
                if (it) {
                    this.naverMap.cameraPosition.target.run {
                        vm.setAddress(latitude, longitude)
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        vm.requestPosts()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodeSet.SEARCH_REQUEST_CODE.code -> {
                    vm.toggleShowBtnYN()
                    data?.let {
                        val mapX = it.getDoubleExtra("mapX", 0.0)
                        val mapY = it.getDoubleExtra("mapY", 0.0)
                        val tm128 = Tm128(mapX, mapY)
                        val latLng = tm128.toLatLng()
                        val cameraUpdate = CameraUpdate.scrollAndZoomTo(
                            latLng,
                            NaverCamera.defaultZoom
                        ).animate(CameraAnimation.Fly, NaverCamera.defaultAnimateTime)
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
        vm.showPinYN.value?.let {
            if (it) {
                vm.toggleShowPinYN()
                vm.toggleShowBtnYN()
                return@onBackPressed
            }
        }
        super.onBackPressed()
    }

    //todo 디자인 수정 필요
    private fun showMarkers() {
        vm.postMarkers.value?.let { postMarkers ->
            TedNaverClustering.with<PostMarker>(this, naverMap)
                .customMarker { clusterItem ->
                    pinMarker(
                        clusterItem.position,
                        this@MapActivity,
                        clusterItem.imageUrls?.get(0),
                        clusterItem.emotion
                    )
                }.customCluster {
                    clusterMarker(
                        this@MapActivity,
                        it.items.random(),
                        it.items.size
                    )
                }
                .items(postMarkers)
                .make()
        }
    }
}