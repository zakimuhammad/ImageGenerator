package com.zaki.imagegenerator.model

import java.io.Serializable

data class Data(
    val id: String,
    val name: String,
    val url: String,
    val width: Int,
    val height: Int,
    val box_count: Int
): Serializable
