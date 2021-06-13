package br.iesb.poo.rpg.loja

import br.iesb.poo.rpg.personagem.PersonagemJogador
import java.io.File
import java.nio.file.Paths


open class Itens(){



    open fun efeito(jogador: PersonagemJogador, id: String?): String {
        var log: String = ""

        if(jogador.dinheiro < jogador.nivel * 2) {
            log = "VOCE NAO POSSUI MOEDAS DE OURO SUFICIENTES"
        } else {
            if (id == "vida") {
                jogador.pontosVida = jogador.pontosVida * 2
                jogador.dinheiro = jogador.dinheiro - jogador.dinheiro/2
            } else if (id == "mana") {
                jogador.pontosMana = jogador.pontosMana * 2
                jogador.dinheiro = jogador.dinheiro - jogador.dinheiro/2
            }
            log = "PARABENS PELA COMPRA"
        }
        return log
    }


}