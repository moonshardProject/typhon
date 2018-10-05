package io.moonshard.typhon.models

import org.bson.types.ObjectId
import java.util.*

data class Event(var date: Date?,
                 var desc: String?,
                 var command: String?,
                 var userId: ObjectId?,
                 var moduleId: ObjectId?)