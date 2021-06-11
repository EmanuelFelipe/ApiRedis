package com.iesbpi.redis.rpg.batalha

import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro
import com.iesbpi.redis.rpg.Rpg
import com.iesbpi.redis.rpg.TipoPersonagem


fun batalha(jogador: PersonagemJogador, RPG: Rpg): String {

    val monstro: PersonagemMonstro = RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO)

    val racaMonstro = arrayOf("Orc", "Goblin", "Gnomio")
    val afinidade = arrayOf("ÁGUA", "FOGO", "AR", "TERRA")
    var batalhaRolando = true

    var log =
        "--BATALHA DE NÚMERO ${jogador.batalhas}--\n" +
                "--LOG DA BATALHA ENTRE ${jogador.nome} e ${racaMonstro[monstro.raca]} ${monstro.nome} DE NÍVEL ${monstro.nivel}--\n\n"

    // INÍCIO CÁLCULO DOS ATRIBUTOS

    log += "[ ~ ] MONSTRO COM ELEMENTO ${afinidade[monstro.elemento-1]}\n"
    log += "[ ~ ] JOGADOR COM ELEMENTO ${afinidade[jogador.elemento-1]}\n"



    var ataqueJ: Int = jogador.ataque + jogador.ataqueitem
    var ataqueM: Int = monstro.ataque

    var defesaJ: Int = jogador.defesa + jogador.defesaitem
    var defesaM: Int = monstro.defesa

    var danoJ: Int = 0
    var danoM: Int = 0
    var curaJ: Int = 0
    var curaM: Int = 0

    var danoUpgradeJ: Int = 0
    var danoUpgradeM: Int = 0
    var curaUpgradeJ: Int = 0
    var curaUpgradeM: Int = 0
    var reducaoMana: Int = 0

    var opcaoJ: Int = 0
    var opcaoM: Int = 0

    jogador.pontosVida = jogador.maxVida
    jogador.pontosMana = jogador.maxMana
    jogador.ataque = jogador.maxAtaque
    jogador.defesa = jogador.maxDefesa
    jogador.velocidade = jogador.maxVelocidade

    monstro.pontosVida = monstro.maxVida
    monstro.pontosMana = monstro.maxMana
    monstro.ataque = monstro.maxAtaque
    monstro.defesa = monstro.maxDefesa
    monstro.velocidade = monstro.maxVelocidade

    // FIM CÁLCULOS DE ATRIBUTOS
    // INÍCIO COMBATE

    var turno = 1

    if(jogador.velocidade > monstro.velocidade){
        log += "[ * ] JOGADOR INICIOU O COMBATE\n\n"

        while (batalhaRolando) { //Para batalha, a gente vai fazer um switch case para as opções que o jogador pode fazer
            // Nesse caso, vai ter 4 opções: Atacar, Magia, Inventário, Fugir
            // invés de conferir os pontos de vida pelo while, podemos fazer um if que faz esse conferencia, e caso foi verdadeiro, a gente muda a váriavel do while para false, assim ele para a batalha
            // então, vai ser: while (batalhaRolando = true) ai no fim da batalha, batalhaRolando = false, saindo do while

            jogador.defesa = jogador.maxDefesa
            monstro.defesa = monstro.maxDefesa


            opcaoJ = 1
            //TURNO JOGADOR
            when (opcaoJ) {

                1 -> danoJ = jogador.atacarPersonagem(jogador.maxAtaque, jogador.nivel, monstro.defesa) //Ataque Basico
                2 -> jogador.defesa = jogador.defender(jogador.defesa) //opcao pro personagem defender
                3 -> batalhaRolando = jogador.fugirPersonagem(jogador.velocidade, monstro.velocidade) //opcao pro jogador fugir da batalha
                4 -> danoJ = jogador.usarMagia(jogador.nivel, danoUpgradeJ, jogador.maxAtaque, monstro.maxDefesa, jogador.elemento, monstro.elemento)
                5 -> curaJ = jogador.calcularCura(jogador.nivel, 10 + curaUpgradeJ, jogador.maxMana, jogador.elemento)

            }

//            if(opcaoJ == 4){
//
//                reducaoMana = jogador.reduzMana(custoMagia)
//
//                jogador.pontosMana -= reducaoMana
//            }
//            else if (opcaoJ == 5){
//
//                reducaoMana = jogador.reduzMana(custoMagia)
//
//                jogador.pontosMana -= reducaoMana
//            }

            jogador.pontosVida += curaJ
            monstro.pontosVida -= danoJ



            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM $defesaM DE DEFESA\n"

            if (monstro.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += monstro.derrota(RPG)
                log += jogador.vitoria(monstro)
                batalhaRolando = false //break faz a msm funcao do rolando, mas ai a gente vê qual a melhor opcao

            }

            //TURNO MONSTRO

            opcaoM = 1

            when(opcaoM) {

                1 -> danoM = monstro.atacarPersonagem(monstro.maxAtaque, monstro.nivel, jogador.defesa) //Ataque Basico
                2 -> monstro.defesa = monstro.defender(monstro.defesa) //opcao pro personagem defender
                3 -> danoM = monstro.usarMagia(monstro.nivel, danoUpgradeM, monstro.maxAtaque, jogador.maxDefesa, monstro.elemento, jogador.elemento)
                4 -> curaM = monstro.calcularCura(monstro.nivel, 10, monstro.maxMana, monstro.elemento)
            }

            monstro.pontosVida += curaM
            jogador.pontosVida -= danoM


            log += "TURNO ${turno}: MONSTRO ATACOU COM $ataqueM JOGADOR FICOU COM $defesaJ DE DEFESA\n"

            turno++

            if (jogador.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota(RPG)
                batalhaRolando = false

            }
        }

        log += "\n--FIM DO COMBATE--\n"
    } else {
        log += "[ * ] EMBOSCADA! MONSTRO INICIOU O COMBATE\n\n"

        while (batalhaRolando) {

            jogador.defesa = jogador.maxDefesa
            monstro.defesa = monstro.maxDefesa

            //TURNO MONSTRO

            opcaoM = 1

            when(opcaoM) {

                1 -> danoM = monstro.atacarPersonagem(monstro.maxAtaque, monstro.nivel, jogador.defesa) //Ataque Basico
                2 -> monstro.defesa = monstro.defender(monstro.defesa) //opcao pro personagem defender
                3 -> danoM = monstro.usarMagia(monstro.nivel, danoUpgradeM, monstro.maxAtaque, jogador.maxDefesa, monstro.elemento, jogador.elemento)
                4 -> curaM = monstro.calcularCura(monstro.nivel, 10, monstro.maxMana, monstro.elemento)
            }

            monstro.pontosVida += curaM
            jogador.pontosVida -= danoM


            log += "TURNO ${turno}: MONSTRO ATACOU COM $ataqueM JOGADOR FICOU COM ${defesaJ}\n"

            if (jogador.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota(RPG)
                batalhaRolando = false

            }

            opcaoJ = 1
            //TURNO JOGADOR
            when (opcaoJ) {

                1 -> danoJ = jogador.atacarPersonagem(jogador.maxAtaque, jogador.nivel, monstro.defesa) //Ataque Basico
                2 -> jogador.defesa = jogador.defender(jogador.defesa) //opcao pro personagem defender
                3 -> batalhaRolando = jogador.fugirPersonagem(jogador.velocidade, monstro.velocidade) //opcao pro jogador fugir da batalha
                4 -> danoJ = jogador.usarMagia(jogador.nivel, danoUpgradeJ, jogador.maxAtaque, monstro.maxDefesa, jogador.elemento, monstro.elemento)
                5 -> curaJ = jogador.calcularCura(jogador.nivel, 10 + curaUpgradeJ, jogador.maxMana, jogador.elemento)

            }

//            if(opcaoJ == 4){
//
//                reducaoMana = jogador.reduzMana(custoMagia)
//
//                jogador.pontosMana -= reducaoMana
//            }
//            else if (opcaoJ == 5){
//
//                reducaoMana = jogador.reduzMana(custoMagia)
//
//                jogador.pontosMana -= reducaoMana
//            }

            jogador.pontosVida += curaJ
            monstro.pontosVida -= danoJ


            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM ${defesaM}\n"

            turno++

            if (monstro.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += monstro.derrota(RPG)
                log += jogador.vitoria(monstro)
                batalhaRolando = false

            }
        }

        log += "\n--FIM DO COMBATE--\n"
    }

    return log
}

fun batalhaChefe(jogador: PersonagemJogador, RPG: Rpg): String {

    val chefe: PersonagemMonstro =
        RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_CHEFE)

    var batalhaRolando = true


    var log =
        "--BATALHA DE NÚMERO ${jogador.batalhas}--\n" +
                "--LOG DA BATALHA ENTRE ${jogador.nome} E CHEFE ${chefe.nome}--\n\n"

//    if (jogador.durabilidadeataque == 0) {
//        jogador.removerItem(jogador)
//    } else if (jogador.durabilidadeataque != 0) {
//        jogador.durabilidadeataque--
//    }
//
//    if (jogador.durabilidadedefesa == 0) {
//        jogador.removerItem(jogador)
//    } else if (jogador.durabilidadedefesa != 0) {
//        jogador.durabilidadedefesa--
//    }

    var ataqueJ: Int = jogador.ataque + jogador.ataqueitem
    var ataqueM: Int = chefe.ataque

    var defesaJ: Int = jogador.defesa + jogador.defesaitem
    var defesaM: Int = chefe.defesa

    var danoJ: Int = 0
    var danoM: Int = 0
    var curaJ: Int = 0
    var curaM: Int = 0

    var danoUpgradeJ: Int = 0
    var danoUpgradeM: Int = 0
    var curaUpgradeJ: Int = 0
    var curaUpgradeM: Int = 0
    var reducaoMana: Int = 0

    var opcaoJ: Int = 0
    var opcaoM: Int = 0

    jogador.pontosVida = jogador.maxVida
    jogador.pontosMana = jogador.maxMana
    jogador.ataque = jogador.maxAtaque
    jogador.defesa = jogador.maxDefesa
    jogador.velocidade = jogador.maxVelocidade

    chefe.pontosVida = chefe.maxVida
    chefe.pontosMana = chefe.maxMana
    chefe.ataque = chefe.maxAtaque
    chefe.defesa = chefe.maxDefesa
    chefe.velocidade = chefe.maxVelocidade


    log += "[ f ] CHEFE FINAL - ATAQUE $ataqueM /// DEFESA ${defesaM}\n"
    log += "[ f ] JOGADOR FINAL - ATAQUE $ataqueJ /// DEFESA ${defesaJ}\n\n"


    var turno = 1


    if (jogador.velocidade > chefe.velocidade) {

        log += "[ * ] JOGADOR INICIOU O COMBATE\n\n"

        while (batalhaRolando) {

            jogador.defesa = jogador.maxDefesa
            chefe.defesa = chefe.maxDefesa


            opcaoJ = 1
            //TURNO JOGADOR
            when (opcaoJ) {

                1 -> danoJ = jogador.atacarPersonagem(jogador.maxAtaque, jogador.nivel, chefe.defesa) //Ataque Basico
                2 -> jogador.defesa = jogador.defender(jogador.defesa) //opcao pro personagem defender
                3 -> batalhaRolando = jogador.fugirPersonagem(jogador.velocidade, chefe.velocidade) //opcao pro jogador fugir da batalha
                4 -> danoJ = jogador.usarMagia(jogador.nivel, danoUpgradeJ, jogador.maxAtaque, chefe.maxDefesa, jogador.elemento, chefe.elemento)
                5 -> curaJ = jogador.calcularCura(jogador.nivel, 10 + curaUpgradeJ, jogador.maxMana, jogador.elemento)

            }

//            if(opcaoJ == 4){
//
//                reducaoMana = jogador.reduzMana(custoMagia)
//
//                jogador.pontosMana -= reducaoMana
//            }
//            else if (opcaoJ == 5){
//
//                reducaoMana = jogador.reduzMana(custoMagia)
//
//                jogador.pontosMana -= reducaoMana
//            }

            jogador.pontosVida += curaJ
            chefe.pontosVida -= danoJ

            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM $defesaM DE DEFESA\n"

            if (chefe.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += chefe.derrota(RPG)
                log += jogador.vitoria(chefe)
                batalhaRolando = false
            }

            //TURNO MONSTRO

            opcaoM = 1

            when(opcaoM) {

                1 -> danoM = chefe.atacarPersonagem(chefe.maxAtaque, chefe.nivel, jogador.defesa) //Ataque Basico
                2 -> chefe.defesa = chefe.defender(chefe.defesa) //opcao pro personagem defender
                3 -> danoM = chefe.usarMagia(chefe.nivel, danoUpgradeM, chefe.maxAtaque, jogador.maxDefesa, chefe.elemento, jogador.elemento)
                4 -> curaM = chefe.calcularCura(chefe.nivel, 10, chefe.maxMana, chefe.elemento)
            }

            chefe.pontosVida += curaM
            jogador.pontosVida -= danoM


            log += "TURNO ${turno}: MONSTRO ATACOU COM $ataqueM JOGADOR FICOU COM $defesaJ DE DEFESA\n"

            turno++

            if (jogador.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota(RPG)
                batalhaRolando = false
            }
        }
    } else {
        log += "[ * ] EMBOSCADA! MONSTRO INICIOU O COMBATE\n\n"

        while (batalhaRolando) {

            //TURNO MONSTRO

            opcaoM = 1

            when(opcaoM) {

                1 -> danoM = chefe.atacarPersonagem(chefe.maxAtaque, chefe.nivel, jogador.defesa) //Ataque Basico
                2 -> chefe.defesa = chefe.defender(chefe.defesa) //opcao pro personagem defender
                3 -> danoM = chefe.usarMagia(chefe.nivel, danoUpgradeM, chefe.maxAtaque, jogador.maxDefesa, chefe.elemento, jogador.elemento)
                4 -> curaM = chefe.calcularCura(chefe.nivel, 10, chefe.maxMana, chefe.elemento)
            }

//            if(opcaoM == 3){
//
//                reducaoMana = monstro.reduzMana(custoMagia)
//
//                monstro.pontosMana -= reducaoMana
//            }
//            else if (opcaoM == 4){
//
//                reducaoMana = monstro.reduzMana(custoMagia)
//
//                monstro.pontosMana -= reducaoMana
//            }

            chefe.pontosVida += curaM
            jogador.pontosVida -= danoM


            log += "TURNO ${turno}: MONSTRO ATACOU COM $ataqueM JOGADOR FICOU COM ${defesaJ}\n"

            if (jogador.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrotaChefe(RPG)
                break
            }

            opcaoJ = 1
            //TURNO JOGADOR
            when (opcaoJ) {

                1 -> danoJ = jogador.atacarPersonagem(jogador.maxAtaque, jogador.nivel, chefe.defesa) //Ataque Basico
                2 -> jogador.defesa = jogador.defender(jogador.defesa) //opcao pro personagem defender
                3 -> batalhaRolando = jogador.fugirPersonagem(jogador.velocidade, chefe.velocidade) //opcao pro jogador fugir da batalha
                4 -> danoJ = jogador.usarMagia(jogador.nivel, danoUpgradeJ, jogador.maxAtaque, chefe.maxDefesa, jogador.elemento, chefe.elemento)
                5 -> curaJ = jogador.calcularCura(jogador.nivel, 10 + curaUpgradeJ, jogador.maxMana, jogador.elemento)

            }

            jogador.pontosVida += curaJ
            chefe.pontosVida -= danoJ


            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM ${defesaM}\n"

            turno++

            if (chefe.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += chefe.derrota(RPG)
                log += jogador.vitoria(chefe)
                break
            }
        }

        log += "\n--FIM DO COMBATE--\n"
    }

    return log
}

