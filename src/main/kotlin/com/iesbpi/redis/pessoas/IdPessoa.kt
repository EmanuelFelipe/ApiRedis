package com.iesbpi.redis.pessoas

import com.iesbpi.redis.rpg.Rpg

class IdPessoa(rpgAtual: Rpg){
    var id: Int = 0

    init {
        id = genId(rpgAtual)

    }

    fun genId(rpgAtual: Rpg): Int {

        var novaId = (0..10000).random()
        while (rpgAtual.ids.find { it.id == novaId } != null) { //TODO EVITAR LOOP INFINITO(CONTADOR?)
            novaId = (0..10000).random()
        }
        return novaId
    }
}