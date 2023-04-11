package dev.reprator.language

import dev.reprator.core.Mapper
import dev.reprator.language.controller.LanguageController
import dev.reprator.language.controller.LanguageControllerImpl
import dev.reprator.language.data.LanguageRepositoryImpl
import dev.reprator.language.data.LanguageRepository
import dev.reprator.language.domain.LanguageFacadeImpl
import dev.reprator.language.domain.LanguageFacade
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import dev.reprator.language.data.mapper.LanguageResponseMapper

/**
 * The container to inject all dependencies from the language module.
 */
val languageModule = module {
    singleOf(::LanguageResponseMapper) bind Mapper::class
    single<LanguageRepository> { LanguageRepositoryImpl(get()) }
    singleOf(::LanguageFacadeImpl) bind LanguageFacade::class
    single { LanguageControllerImpl(get()) } bind LanguageController::class
}