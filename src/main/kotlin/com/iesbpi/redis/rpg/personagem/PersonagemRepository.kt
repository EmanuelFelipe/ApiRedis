package com.iesbpi.redis.rpg.personagem

import br.iesb.poo.rpg.personagem.Personagem
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonagemRepository: CrudRepository<Personagem, Int>{

    fun findByNome(nome: String): Personagem
}