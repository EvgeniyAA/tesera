package com.tesera.domain.users

data class UsersPageParams(
    val alias: String,
    val limit: Int = 30,
    val offset: Int = 0,
)