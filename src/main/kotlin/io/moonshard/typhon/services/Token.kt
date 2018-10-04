package io.moonshard.typhon.services

import org.springframework.stereotype.Service

@Service
class TokenService(val tokenRepository: TokenService){}