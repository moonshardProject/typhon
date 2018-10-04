package io.moonshard.typhon.models

import io.moonshard.typhon.Validator
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

class PassswordIsNotValid : Exception("PassswordIsNotValid")

@Document
data class User(var userId: ObjectId?, var name: String?, var lastName: String?, var email: String?, var password: String?) {
    private var validator: Validator<User> = Validator()
    fun isValid() = validator.check(this)

    fun IsPasswordValid(candid: String) {
        if (this.password != candid) {
            throw PassswordIsNotValid()
        }
    }
}
