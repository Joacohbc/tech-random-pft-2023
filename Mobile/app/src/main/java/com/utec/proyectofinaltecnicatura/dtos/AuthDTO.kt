package com.utec.proyectofinaltecnicatura.dtos
data class LogintDTO (val nombreUsuario: String, val contrasenia: String) {
    constructor() : this("", "")
}
data class TokenDTO (val token: String) {
    constructor() : this("")
}
