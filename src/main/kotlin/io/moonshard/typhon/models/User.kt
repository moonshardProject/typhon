package io.moonshard.typhon.models

import io.moonshard.typhon.Validator
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import reactor.core.publisher.Mono

class PassswordIsNotValid : Exception("PassswordIsNotValid")

@Document
data class User(var userId: ObjectId? = null,
                var name: String? = null,
                var lastName: String? = null,
                var email: String? = null,
                var phoneNumber: String? = null,
                var password: String? = null) {

    private var validator: Validator<User> = Validator()
    fun isValid() = validator.check(this)

    fun IsPasswordValid(candid: String) {
        if (this.password != candid) {
            throw PassswordIsNotValid()
        }
    }

}
