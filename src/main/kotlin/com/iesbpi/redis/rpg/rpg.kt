package com.iesbpi.redis.rpg


import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro
import com.iesbpi.redis.config.RPG
import com.iesbpi.redis.pessoas.IdPessoa

enum class TipoPersonagem {
    PERSONAGEM_MONSTRO,
    PERSONAGEM_CHEFE
}

class Rpg {

    val jogadores = mutableListOf<PersonagemJogador>()
    val monstros = mutableListOf<PersonagemMonstro>()
    val ids = mutableListOf<IdPessoa>()

    private val listaNomes = arrayOf(
        "Bei, ",
        "Thok, ",
        "Mashbu, ",
        "Lurtz, "
    )


    private val listaTitulos = arrayOf(
        "o Comilão",
        "o Banguela",
        "o Preguiçoso",
        "o Destraido"
    )

    private val listaChefe = arrayOf(
        "Blexoverreth, o Terrivel",
        "Nedraco, o não Bonito",
        "Hanthad, o Perverso",
        "Kenniston Funkeiro",
        "P3, a Prova"
    )

    fun criarMonstro(
        tipoPersonagem: TipoPersonagem
    ): PersonagemMonstro {

        val novoPersonagem =if (tipoPersonagem == TipoPersonagem.PERSONAGEM_MONSTRO){
            PersonagemMonstro(
                novaRaca = (0..1).random(),
                (listaNomes).random() + (listaTitulos).random(),
                elementoMonstro = (1..4).random(),
                RPG
            )
        } else{
            PersonagemMonstro(
                novaRaca = 2,
                (listaChefe).random(),
                elementoMonstro = 4,
                RPG
            )
        }

        monstros.add(novoPersonagem)

        return novoPersonagem
    }

}
