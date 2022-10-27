package com.mobidal.pharmacynamoune.model

class Category(
    val id: Int,
    val name: String,
    val offerText: String,
    val pictureUrl: String,
    val categoryList: List<Category>
)