package dev.reprator.language.domain

import dev.reprator.core.exception.StatusCodeException

class LanguageEmptyException(message: String? = null, cause: Throwable? = null) : StatusCodeException.Empty(message, cause)
class LanguageNotFoundException(message: String? = null, cause: Throwable? = null) : StatusCodeException.NotFound(message, cause)
class IllegalLanguageException(message: String? = null, cause: Throwable? = null) : StatusCodeException.BadRequest(message, cause)