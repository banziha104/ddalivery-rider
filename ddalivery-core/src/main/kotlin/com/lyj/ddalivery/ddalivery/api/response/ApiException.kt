package com.lyj.ddalivery.ddalivery.api.response

import lombok.Getter

@Getter
class ApiException(var status : ApiResponseCode) : RuntimeException() {
    override lateinit var message: String

    init {
        message = status.message
    }

    constructor(status: ApiResponseCode, e: Exception?) : this(status) {
        this.status = status
        message = status.message
    }

    constructor(status: ApiResponseCode, message: String, e: Exception?) : this(status) {
        this.status = status
        this.message = message
    }
}
