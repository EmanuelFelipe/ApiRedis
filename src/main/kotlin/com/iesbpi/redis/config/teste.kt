package com.iesbpi.redis.config

import com.fasterxml.jackson.annotation.JsonCreator

class teste(
    val nome: String,
    val email: String,
    val personagens: Int
) {
    data class  NewTeste @JsonCreator constructor(
        val nome: String,
        val email: String,
        val personagens: Int
    )
    override fun toString(): String {
        return "Category [title: ${this.nome}, author: ${this.email}, categories: ${this.personagens}]"
    }
}