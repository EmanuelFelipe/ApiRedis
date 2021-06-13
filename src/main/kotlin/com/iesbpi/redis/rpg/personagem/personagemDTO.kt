package com.iesbpi.redis.rpg.personagem

import br.iesb.poo.rpg.personagem.PersonagemJogador

data class PersonagemDTO(
    val classe: Int,
    val nome: String,
    val elemento: Int,
    val email: String
)