package com.danielleitelima.resume.foundation.presentation.component

import coil.decode.SvgDecoder
import coil.request.ImageRequest

fun ImageRequest.Builder.build(imageUrl: String?) = this
    .data(imageUrl)
    .crossfade(true)
    .decoderFactory(SvgDecoder.Factory())
    .build()
