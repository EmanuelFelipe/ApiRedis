package com.iesbpi.redis.rpg.repository

import br.iesb.poo.rpg.personagem.PersonagemJogador
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PersonagemJogadorRepository: CrudRepository<PersonagemJogador, Int> {

    fun findByNome(nome: String): Optional<PersonagemJogador>
    fun findByUserEmail(userEmail: String): PersonagemJogador?
}