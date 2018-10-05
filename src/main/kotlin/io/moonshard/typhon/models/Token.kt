package io.moonshard.typhon.models

import io.moonshard.typhon.Validator
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Token(var UserId: ObjectId?, var data:String?){
    private val validator: Validator<Token> = Validator()
    fun isValid() = validator.check(this)

}