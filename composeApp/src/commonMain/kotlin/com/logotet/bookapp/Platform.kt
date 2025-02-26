package com.logotet.bookapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform