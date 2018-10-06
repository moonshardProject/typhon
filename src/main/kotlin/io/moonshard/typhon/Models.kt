package io.moonshard.typhon

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Device(@Id var deviceId: ObjectId?, var userId: ObjectId?, var deviceName: String?, var tokenId: ObjectId?) {
    private var validator: Validator<Device> = Validator()

    fun isValid() = validator.check(this)
}


data class Event(var date: Date?,
                 var desc: String?,
                 var command: String?,
                 var userId: ObjectId?,
                 var moduleId: ObjectId?)



@Document
data class Module(@Id var moduleId:ObjectId?, var userId: ObjectId?, var moduleName:String?){
    private var validator: Validator<Module> = Validator()

    fun isValid() = validator.check(this)
}

@Document
data class Token(var UserId: ObjectId?, var data:String?){
    private val validator: Validator<Token> = Validator()
    fun isValid() = validator.check(this)
}


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
