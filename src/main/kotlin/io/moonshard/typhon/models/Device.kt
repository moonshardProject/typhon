package io.moonshard.typhon.models

import io.moonshard.typhon.Validator
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Device(@Id var deviceId: ObjectId?, var userId: ObjectId?, var deviceName: String?, var tokenId: ObjectId?) {
    private var validator: Validator<Device> = Validator()

    fun isValid() = validator.check(this)
}