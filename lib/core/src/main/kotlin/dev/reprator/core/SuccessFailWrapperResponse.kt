package dev.reprator.core


interface HttpStatusCodeModal {
    val statusCode: Int
}


data class ResultResponse(override val statusCode: Int, val data: Any) : HttpStatusCodeModal

data class FailResponse(override val statusCode: Int, val error: String) : HttpStatusCodeModal