package dev.reprator.user

import org.koin.core.KoinApplication

fun KoinApplication.setUpKoinUser() {
    modules(userModule)
}
