package br.iesb.poo.rpg.loja

import br.iesb.poo.rpg.personagem.PersonagemJogador
import java.io.File
import java.nio.file.Paths


open class Itens(
    identrada: String?,
    tipoentrada: String,
    nomeentrada: String,
    efeitoentrada: String,
    precoentrada: Int,
    jogador: PersonagemJogador
) {

    var preco: Int = -1

    var id: String? = ""

    var nome: String = ""

    var efeito: String = ""

    var tipo: String = ""

    var qtd: Int = 0

    var danoMagico: Int = 0

    var vidaTemp: Int = 0
    var atkTemp: Int = 0
    var defTemp: Int = 0
    var velTemp: Int = 0
    var manaTemp: Int = 0



    init {


        id = identrada

        nome = nomeentrada

        efeito = efeitoentrada


        //1-pocao 2-arma 3-armadura
        tipo = tipoentrada

        preco = precoentrada


    }

    open fun buscar(id: String?): ArrayList<String> {
        var arrAux: ArrayList<String>
        var retorno = arrayListOf<String>()

        File("C:\\Users\\talit\\Documents\\API PI2\\redis\\src\\main\\kotlin\\com\\iesbpi\\redis\\rpg\\loja\\elixir.txt").forEachLine {//[0] - id | [1] - tipo | [2] -nome | [3] -efeito | [4] - preco
            arrAux = it.split("|") as ArrayList<String>
            if (arrAux[0] == id) {
                retorno = arrAux
            }
        }
        return retorno
    }


    open fun efeito(jogador: PersonagemJogador, id: String?) {
        var eff = buscar(id)[3].split(".") as ArrayList<String>

        if (eff[0] == "hp") { //jogador.eff[0]
            vidaTemp = jogador.maxVida + eff[1].toInt()
            jogador.maxVida = vidaTemp

        } else if (eff[0] == "mp") {
            manaTemp = jogador.maxMana + eff[1].toInt()
            jogador.maxMana = manaTemp

        } else if (eff[0] == "at") {
            atkTemp = jogador.maxAtaque + eff[1].toInt()
            jogador.maxAtaque = atkTemp

        } else if (eff[0] == "df") {
            defTemp = jogador.maxDefesa + eff[1].toInt()
            jogador.maxDefesa = defTemp

        } else if (eff[0] == "vl") {
            velTemp = jogador.maxVelocidade + eff[1].toInt()
            jogador.maxVelocidade = velTemp

        }

    }

    open fun upMagias(jogador: PersonagemJogador, id: String?) {
        var eff = buscar(id)[3].split(".") as ArrayList<String>

        if (eff[0] == "fg") {
            vidaTemp = jogador.maxVida + eff[1].toInt()
            jogador.maxVida = vidaTemp

        } else if (eff[0] == "mp") {
            manaTemp = jogador.maxMana + eff[1].toInt()
            jogador.maxMana = manaTemp

        } else if (eff[0] == "at") {
            atkTemp = jogador.maxAtaque + eff[1].toInt()
            jogador.maxAtaque = atkTemp

        } else if (eff[0] == "df") {
            defTemp = jogador.maxDefesa + eff[1].toInt()
            jogador.maxDefesa = defTemp

        } else if (eff[0] == "vl") {
            velTemp = jogador.maxVelocidade + eff[1].toInt()
            jogador.maxVelocidade = velTemp

        }

    }

}



