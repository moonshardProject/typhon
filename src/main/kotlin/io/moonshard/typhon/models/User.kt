package io.moonshard.typhon.models

import org.springframework.data.mongodb.core.mapping.Document

class PassswordIsNotValid : Exception("PassswordIsNotValid")

@Document
data class User(var name: String?, var lastName: String?, var email: String?, var password: String?) {
    fun IsPasswordValid(candid: String) {
        if (this.password != candid) {
            throw PassswordIsNotValid()
        }
    }
}
