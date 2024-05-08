package com.sumitranjan.videoaddproject.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import com.sumitranjan.videoaddproject.UrlData
import java.io.File

class MainActivityViewModel:ViewModel() {
    var videoAddList=MutableLiveData<List<UrlData>>()
    var internetAvailable=MutableLiveData<Boolean>()
    var mediaSourceFileList=MutableLiveData<List<MediaItem>>()
    init {
        arrangeAddList()
    }
     fun arrangeAddList(){
        val list= mutableListOf<UrlData>()
        list.add(UrlData("https://adbeets-assets.s3.ap-south-1.amazonaws.com/assets/80/ASSETS/asset_7378692246785011771DesignAsset_1707124198604_Ads%20offers.mp4","asset_7378692246785011771"))
        list.add(UrlData("https://adbeets-assets.s3.ap-south-1.amazonaws.com/assets/80/ASSETS/asset_10997764465025613630DesignAsset_1707128707418_dantam%20dental%20suite-2.mp4","asset_10997764465025613630"))
        list.add(UrlData("https://adbeets-assets.s3.ap-south-1.amazonaws.com/assets/86/ASSETS/asset_14975988839848876724DesignAsset_1711686900898_World+Kidney+Day+Campaign_Digital+Screen+-+V1+(1).mp4","asset_6890123940375361769"))
        list.add(UrlData("https://adbeets-assets.s3.ap-south-1.amazonaws.com/assets/80/ASSETS/asset_6890123940375361769DesignAsset_1709667781043_Dantam%20Ad_01%20copy.mp4","asset_6890123940375361769"))
        list.add(UrlData("https://adbeets-assets.s3.ap-south-1.amazonaws.com/assets/80/ASSETS/asset_7360241592401237427DesignAsset_1709667785000_Dantam%20Ad_02%20copy.mp4","asset_7360241592401237427"))
        videoAddList.postValue(list)
    }



}