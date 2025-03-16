package com.dezzy.freebeer.data.mappers

import com.dezzy.freebeer.data.local.BeerEntity
import com.dezzy.freebeer.data.remote.BeerDto
import com.dezzy.freebeer.domain.Beer

fun BeerDto.toBeerEntity(): BeerEntity {
    return BeerEntity(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = first_brewed,
        imageUrl = image_url
    )
}

fun BeerEntity.toBeer(): Beer{
    return Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        first_brewed = firstBrewed,
        image_url = imageUrl
    )
}
