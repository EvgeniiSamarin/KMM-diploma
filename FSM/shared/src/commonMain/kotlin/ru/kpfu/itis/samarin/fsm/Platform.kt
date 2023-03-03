package ru.kpfu.itis.samarin.fsm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform