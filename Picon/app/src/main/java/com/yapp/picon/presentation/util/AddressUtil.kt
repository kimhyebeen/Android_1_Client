package com.yapp.picon.presentation.util

import com.yapp.picon.data.model.Address

object AddressUtil {

    private val arrDo = listOf(
        "경기도",
        "강원도",
        "충청남도",
        "충청북도",
        "전라남도",
        "전라북도",
        "경상남도",
        "경상북도",
        "제주특별자치도",
        "제주도"
    )

    fun getAddress(address: String): Address {

        var addrCity = ""
        var addrDo = ""
        var addrGu = ""

        val splitAddress = address.split(" ")
        for (index in splitAddress.indices) {
            splitAddress[index].let {
                if (index == 0) {
                    if (arrDo.contains(splitAddress[index])) {
                        addrDo = it
                    }
                }
                if ((index == 0) or (index == 1)) {
                    if ((it.endsWith("시")) or (it.endsWith("군"))) {
                        addrCity = it
                    }
                }
                if ((index == 1) or (index == 2)) {
                    if (it.endsWith("구")) {
                        addrGu = it
                    }
                }
            }
        }
        return Address(address, addrCity, addrDo, addrGu)
    }
}