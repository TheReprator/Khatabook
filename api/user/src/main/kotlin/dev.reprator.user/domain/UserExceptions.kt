package dev.reprator.user.domain

import dev.reprator.core.exception.StatusCodeException

class UserEmptyException(message: String = "Empty user list", cause: Throwable? = null) : StatusCodeException.Empty(message, cause)
class UserNotFoundException(message: String = "User didn't exist", cause: Throwable? = null) : StatusCodeException.NotFound(message, cause)
class IllegalUserException(message: String? = "Illegal user info", cause: Throwable? = null) : StatusCodeException.BadRequest(message, cause)