package com.yapp.picon.presentation.util

import com.yapp.picon.presentation.model.*

fun toPresentation(post: com.yapp.picon.data.model.Post): Post =
    Post(
        post.id,
        toPresentation(post.coordinate),
        post.imageUrls,
        toPresentation(post.address),
        post.emotion?.let { toPresentation(it) },
        post.memo
    )

private fun toPresentation(coordinate: com.yapp.picon.data.model.Coordinate): Coordinate =
    Coordinate(coordinate.lat, coordinate.lng)

private fun toPresentation(address: com.yapp.picon.data.model.Address): Address =
    Address(address.address, address.addrCity, address.addrDo, address.addrGu)

private fun toPresentation(emotion: com.yapp.picon.data.model.Emotion): Emotion =
    Emotion.valueOf(emotion.name)

fun toPostMarker(post: Post): PostMarker =
    PostMarker(
        post.id,
        post.coordinate,
        post.imageUrls,
        post.address,
        post.emotion,
        post.memo
    )

fun toPost(postMarker: PostMarker) =
    Post(
        postMarker.id,
        postMarker.coordinate,
        postMarker.imageUrls,
        postMarker.address,
        postMarker.emotion,
        postMarker.memo
    )
