package com.yapp.picon.domain.usecase

import com.yapp.picon.data.model.RevGeoResult
import com.yapp.picon.data.source.searched.SearchRepository

class GetRevGeoUseCase(private val repository: SearchRepository) {

    private val orders = "roadaddr"
    private val output = "json"

    suspend operator fun invoke(
        coords: String,
        orders: String = this.orders,
        output: String = this.output
    ): RevGeoResult =
        repository.requestRevGeo(coords, orders, output)

}