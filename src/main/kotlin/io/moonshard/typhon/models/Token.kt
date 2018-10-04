package io.moonshard.typhon.models

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Token(var UserId: ObjectId?, var data:String?){

}