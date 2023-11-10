package com.upaep.upaeppersonal.model.entities.features.upress

data class UpressItem(
    var itemId: Int? = null,
    var categoryId: Int? = null,
    var title: String? = null,
    var publish: String? = null,
    var introtext: String? = null,
    var imageIntro: String? = null,
    var imageFullText: String? = null,
    var author: String? = null
)
