package dev.reprator.splash.modal

import dev.reprator.language.modal.LanguageModal
import java.io.Serializable

data class SplashModal(private val imageList: List<String>, private val languageList: List<LanguageModal>): Serializable