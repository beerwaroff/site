package forum.service

import forum.dto.AuthDTO

interface AuthService {

    fun authorization(dto: AuthDTO): Any

    fun registration(dto: AuthDTO): Any

    fun registrationAdmin(dto: AuthDTO): Any
}