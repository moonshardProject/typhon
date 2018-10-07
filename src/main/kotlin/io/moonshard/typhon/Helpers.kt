package io.moonshard.typhon

import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun checkPassword(candid: String, password: String) = BCrypt.checkpw(candid, password)
fun hashPassword(password: String) = BCrypt.hashpw(password, BCrypt.gensalt())
fun randomString() = UUID.randomUUID().toString().replace("-", "")