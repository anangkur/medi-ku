package com.anangkur.uangkerja.feature.listProduct

interface ListProductActionListener{
    fun onClickItem(productId: String)
    fun onClickCategory(categoryId: Int, categoryName: String)
    fun onClickDeleteCategory()
}