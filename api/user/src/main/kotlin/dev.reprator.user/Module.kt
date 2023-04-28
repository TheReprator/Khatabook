package dev.reprator.user

import dev.reprator.core.Mapper
import dev.reprator.user.controller.UserController
import dev.reprator.user.controller.UserControllerImpl
import dev.reprator.user.data.UserRepositoryImpl
import dev.reprator.user.data.UserRepository
import dev.reprator.user.domain.UserFacadeImpl
import dev.reprator.user.domain.UserFacade
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import dev.reprator.user.data.mapper.UserResponseMapper
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.named

/**
 * The container to inject all dependencies from the language module.
 */
private const val KOIN_NAMED_MAPPER_USER = "userMapper"

val userModule = module {
    singleOf(::UserResponseMapper).withOptions {qualifier = named(KOIN_NAMED_MAPPER_USER) } bind Mapper::class
    single<UserRepository> { UserRepositoryImpl(get(qualifier = named(KOIN_NAMED_MAPPER_USER))) }
    singleOf(::UserFacadeImpl) bind UserFacade::class
    single { UserControllerImpl(get()) } bind UserController::class
}