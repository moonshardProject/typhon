package io.moonshard.typhon.models

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Module(var moduleId:ObjectId?, var userId: ObjectId?, var moduleName:String?)