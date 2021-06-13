package com.iesbpi.redis.rpg.batalha

import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro
import com.iesbpi.redis.rpg.Rpg
import com.iesbpi.redis.rpg.TipoPersonagem


fun batalha(jogador: PersonagemJogador, monstro: PersonagemMonstro, RPG: Rpg, opt: Int?, tipoBt: Int?): String {

    val racaMonstro = arrayOf("Orc", "Goblin", "Gnomio")
    var log =
        "--BATALHA DE NÚMERO ${jogador.batalhas}--\n" +
                "--LOG DA BATALHA ENTRE ${jogador.nome} e ${racaMonstro[monstro.raca]} ${monstro.nome} DE NÍVEL ${monstro.nivel}--\n\n"
    if(tipoBt != 0){
        if (monstro.raca == 1){
            RPG.monstros.removeAt(0)
            val novoMonstro = RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_CHEFE)
            novoMonstro.definirMonstro(jogador.nivel+3)
            return "AOBA SUA BATALHA COMECOU"
        }
    }

    // INÍCIO CÁLCULO DOS ATRIBUTOS

    var danoJ: Int = 0
    var danoM: Int = 0

    var opcaoM: Int = 0
    var elementoAtk: Int = 0

    // FIM CÁLCULOS DE ATRIBUTOS
    // INÍCIO COMBATE

    var turno = 1

    if (jogador.velocidade > monstro.velocidade) {
        log += "[ * ] JOGADOR INICIOU O COMBATE\n\n"

        jogador.defesa = jogador.maxDefesa
        monstro.defesa = monstro.maxDefesa

        //TURNO JOGADOR
        when (opt) {

            1 -> danoJ = jogador.atacarPersonagem(jogador.maxAtaque, monstro.defesa) //Ataque Basico
            2 -> jogador.defesa = jogador.defender(jogador.defesa) //opcao pro personagem defender
            3 -> if (jogador.pontosMana <= 0) {
                log += "\n--MANA INSUFICIENTE--\n"
                jogador.pontosMana = 0
            } else {
                if (jogador.elemento == 6) {
                    jogador.calcularCura(jogador.nivel, jogador.maxMana)
                } else {
                    danoJ = jogador.calcularDano(
                        jogador.nivel,
                        jogador.maxMana,
                        monstro.maxDefesa,
                        jogador.elemento!!,
                        jogador.elemento!!,
                        monstro.elemento!!
                    )
                }
            }

            4 -> if ((1..10).random() > 8) {
                log += "\n--VOCE NAO VAI CONSEGUI FUGIR SE SUAS PERNAS ESTIVEREM TREMENDO--\n"

            } else if (jogador.velocidade > monstro.velocidade) {
                RPG.monstros.removeAt(0)
                val novomonstro: PersonagemMonstro =
                    RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO)
                novomonstro.definirMonstro(jogador.nivel)

                jogador.pontosVida = jogador.maxVida
                jogador.batalhas++

                log += "\n--FIM DO COMBATE JOGADOR METEU O PÉ--\n"
                return log
            }


        }

        if (opt != 4) {
            monstro.pontosVida = monstro.pontosVida - danoJ
            log += "TURNO ${turno}: JOGADOR ATACOU COM $danoJ MONSTRO FICOU COM ${monstro.pontosVida} DE VIDA\n"
        }

        if (monstro.pontosVida <= 0) {
            log += "\n[ = ] JOGADOR GANHOU\n"
            log += monstro.derrota(RPG)
            log += jogador.vitoria(monstro)
            log += "\n--FIM DO COMBATE--\n"
        } else {
            opcaoM = (1..3).random()
            elementoAtk = (1..5).random()

            when (opcaoM) {

                1 -> danoM = monstro.atacarPersonagem(monstro.maxAtaque, jogador.defesa) //Ataque Basico
                2 -> monstro.defesa = monstro.defender(monstro.defesa) //opcao pro personagem defender
                3 -> danoM = monstro.calcularDano(
                    monstro.nivel,
                    monstro.maxMana,
                    jogador.maxDefesa,
                    elementoAtk,
                    monstro.elemento!!,
                    jogador.elemento!!
                )
            }

            jogador.pontosVida = jogador.pontosVida - danoM


            log += "TURNO ${turno}: MONSTRO ATACOU COM $danoM JOGADOR FICOU COM ${jogador.pontosVida} DE VIDA\n"

            turno++

            if (jogador.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota(RPG)

                log += "\n--FIM DO COMBATE--\n"
            }

        }


    } else {
        log += "[ * ] EMBOSCADA! MONSTRO INICIOU O COMBATE\n\n"


        jogador.defesa = jogador.maxDefesa
        monstro.defesa = monstro.maxDefesa

        //TURNO MONSTRO

        opcaoM = (1..3).random()
        elementoAtk = (1..5).random()

        when (opcaoM) {

            1 -> danoM = monstro.atacarPersonagem(monstro.maxAtaque, jogador.defesa) //Ataque Basico
            2 -> monstro.defesa = monstro.defender(monstro.defesa) //opcao pro personagem defender
            3 -> danoM = monstro.calcularDano(
                monstro.nivel,
                monstro.maxMana,
                jogador.maxDefesa,
                elementoAtk,
                monstro.elemento!!,
                jogador.elemento!!
            )
        }

        jogador.pontosVida = jogador.pontosVida - danoM


        log += "TURNO ${turno}: MONSTRO ATACOU COM $danoM JOGADOR FICOU COM ${jogador.pontosVida} DE VIDA\n"

        if (jogador.pontosVida <= 0) {
            log += "\n[ = ] JOGADOR PERDEU\n"
            log += jogador.derrota(RPG)
            log += "\n--FIM DO COMBATE--\n"
        } else {

            //TURNO JOGADOR
            when (opt) {

                1 -> danoJ = jogador.atacarPersonagem(jogador.maxAtaque, monstro.defesa) //Ataque Basico
                2 -> jogador.defesa = jogador.defender(jogador.defesa) //opcao pro personagem defender
                3 -> if (jogador.pontosMana <= 0) {
                    log += "\n--MANA INSUFICIENTE--\n"
                    jogador.pontosMana = 0
                } else {
                    if (jogador.elemento == 6) {
                        jogador.calcularCura(jogador.nivel, jogador.maxMana)
                    } else {
                        danoJ = jogador.calcularDano(
                            jogador.nivel,
                            jogador.maxMana,
                            monstro.maxDefesa,
                            jogador.elemento!!,
                            jogador.elemento!!,
                            monstro.elemento!!
                        )
                    }
                }
                4 -> log += "\n--VOCE NAO VAI CONSEGUI FUGIR SE SUAS PERNAS ESTIVEREM TREMENDO--\n"

            }


            if (opt != 4) {
                monstro.pontosVida = monstro.pontosVida - danoJ
                log += "TURNO ${turno}: JOGADOR ATACOU COM $danoJ MONSTRO FICOU COM ${monstro.pontosVida} DE VIDA\n"
            }

            turno++

            if (monstro.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += monstro.derrota(RPG)
                log += jogador.vitoria(monstro)

                log += "\n--FIM DO COMBATE--\n"
            }

        }

    }

    if (monstro.pontosVida <= 0 || jogador.pontosVida <= 0) {
        RPG.monstros.removeAt(0)
        val novomonstro: PersonagemMonstro = RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO)
        novomonstro.definirMonstro(jogador.nivel)

        jogador.pontosVida = jogador.maxVida
        jogador.pontosMana = jogador.maxMana
        jogador.batalhas++
    }
    return log
}




