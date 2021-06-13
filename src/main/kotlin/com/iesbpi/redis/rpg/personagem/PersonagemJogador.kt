package br.iesb.poo.rpg.personagem


import com.iesbpi.redis.rpg.Rpg
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import kotlin.collections.ArrayList

open class PersonagemJogador() : Personagem() {

    constructor( classeJogador: Int,
                 nomeJogador: String,
                 elementoJogador: Int,
                 usuarioEmail: String,
                 rpgAtual: Rpg ): this(){
        classe = classeJogador
        id = genId(rpgAtual)
        nome = nomeJogador
        elemento = elementoJogador
        userEmail = usuarioEmail

    }

    override var nome: String ?= null
     //var elemento: Int ?= null

    //Arqueiro = 1; Cavaleiro = 2; Mago = 3(Não foi implementado ainda)
    //Arqueiro + Velocidade - maxVida; Cavaleiro + Ataque - maxMana; Mago + maxMana - Defesa

    var classe: Int ?= null
    private var xp: Int = 0
    var inventario = mutableListOf<ArrayList<String>>()
    var batalhas : Int = 1
    var pontosSabedoria: Float = 0.1f

//    val inventario = arrayOf<Array<String>>() //[Item,Quantidade]

//    val inventario = mutableListOf<String>()


    //var inventario = [[PotP, X], [PotM, y]...]
    //equipados = [arma,armadura]

    override fun genId(rpgAtual: Rpg): Int {

        var novaId = (0..10000).random()
        while (rpgAtual.jogadores.find { it.id == novaId } != null) { //TODO EVITAR LOOP INFINITO(CONTADOR?)
            novaId = (0..10000).random()
        }
        return novaId
    }

    fun definirStatusBase(){ //Inicialização dos statusBase

        this.maxAtaque = 7
        this.statusBaseAtaque = 7
        this.pontosSabedoria = 0.0f

        if (classe == 1) {

            this.maxVida = 6
            this.maxMana = 7
            this.maxDefesa = 7
            this.maxVelocidade = 8

            this.statusBaseVida = 6
            this.statusBaseMana = 7
            this.statusBaseDefesa = 8
            this.statusBaseVelocidade = 8


        } else if (classe == 2){

            this.maxVida = 8
            this.maxMana = 6
            this.maxDefesa = 7
            this.maxVelocidade = 7

            this.statusBaseVida = 8
            this.statusBaseMana = 6
            this.statusBaseDefesa = 7
            this.statusBaseVelocidade = 7

        }
        else{

            this.maxVida = 7
            this.maxMana = 8
            this.maxDefesa = 6
            this.maxVelocidade = 7

            this.statusBaseVida = 7
            this.statusBaseMana = 8
            this.statusBaseDefesa = 6
            this.statusBaseVelocidade = 7

        }
        nivelUp()
    }

    override fun derrota(rpg: Rpg): String { //Sugestão: retirar o negócio da redução de vida, manter a consequêcia do dinhiero

        var dinheiroRoubado = this.dinheiro / 2 //Arrumar depois

        this.dinheiro = dinheiroRoubado

        var log = "\n[ :c ] VOCE DESMAIOU E PERDEU ${dinheiroRoubado} de dinheiro\n"

        return log
    }

    fun derrotaChefe(rpg: Rpg): String {

        var dinheiroRoubado = this.dinheiro / 10 //Arrumar depois

        this.dinheiro = dinheiroRoubado

        var log = "\n[ :c ] VOCE DESMAIOU E PERDEU ${dinheiroRoubado} de dinheiro\n"

        return log
    }

    fun vitoria(monstro: PersonagemMonstro): String { //CALCULAR RECOMPENSAS DE VITÓRIA NO COMBATE E ATUALIZAR NÍVEL

        this.dinheiro += monstro.dinheiro
        val xpganho = monstro.nivel * 100
        this.xp += xpganho

        var log =
            "\n[ $ ] AGORA VOCÊ ESTÁ COM ${this.dinheiro} MOEDAS DE OURO E GANHOU $xpganho XP NO NÍVEL ${this.nivel}\n"

        var xpProxNv = 0
        var i = 0

        // LOOP PARA CALCULAR XP NECESSÁRIO PARA O PRÓXIMO NÍVEL
        do {
            i++
            xpProxNv += i * 100 //Sugestão: Mudar o numero multiplicado por 500, para não facilitar o aumento de nível, mas tbm não deixar muito dificil
            //Colocar o While pra pegar o i até chegar no nível seguinte do nível atual, ou seja, fazer this.nivel + 1
        } while (i in 1 until this.nivel)

        // LOOP PARA ATUALIZAR NÍVEL DO PERSONAGEM CASO GANHE XP SUFICIENTE PARA MAIS DE UMA EVOLUÇÃO
        while (this.xp >= xpProxNv) {
            this.nivel++
            log += this.nivelUp()
            xpProxNv += this.nivel * 100
        }

        log += "\n[ ➽ ] XP ATUAL: ${this.xp}\n"
        log += "[ ➽ ] XP NECESSÁRIO PARA O PRÓXIMO NÍVEL: ${xpProxNv}\n"


        return log
    }

    private fun nivelUp(): String { //Será utilizada a mesma fórmula usada para calcular os status dos monstros: ((2 * statusBase) * nivel)/100 + nivel + 10
        //obs: caso o personagem tenha alguma vantagem com um atributo, adicionar + 2, caso tenha desvantagem com um atributo, diminuir - 2

        if((1..100).random() >= 90){

            this.pontosSabedoria += 1.0f
        }
        else{

            this.pontosSabedoria += 0.5f
        }


        if (classe == 1) {

            this.maxVida = ((2 * statusBaseVida) * nivel)/50 + nivel + 10
            this.maxMana = ((2 * statusBaseMana) * nivel)/50 + nivel + 12
            this.maxAtaque = ((2 * statusBaseAtaque) * nivel)/50 + nivel + 12
            this.maxDefesa = ((2 * statusBaseDefesa) * nivel)/50 + nivel + 12
            this.maxVelocidade = ((2 * statusBaseVelocidade) * nivel)/50 + nivel + 14

        } else if (classe == 2){

            this.maxVida = ((2 * statusBaseVida) * nivel)/50 + nivel + 12
            this.maxMana = ((2 * statusBaseMana) * nivel)/50 + nivel + 10
            this.maxAtaque = ((2 * statusBaseAtaque) * nivel)/50 + nivel + 14
            this.maxDefesa = ((2 * statusBaseDefesa) * nivel)/50 + nivel + 12
            this.maxVelocidade = ((2 * statusBaseVelocidade) * nivel)/50 + nivel + 12

        } else {

            this.maxVida = ((2 * statusBaseVida) * nivel)/50 + nivel + 12
            this.maxMana = ((2 * statusBaseMana) * nivel)/50 + nivel + 14
            this.maxAtaque = ((2 * statusBaseAtaque) * nivel)/50 + nivel + 12
            this.maxDefesa = ((2 * statusBaseDefesa) * nivel)/50 + nivel + 10
            this.maxVelocidade = ((2 * statusBaseVelocidade) * nivel)/50 + nivel + 12
        }

        this.pontosVida = this.maxVida
        this.pontosMana = this.maxMana
        this.defesa = this.maxDefesa
        this.velocidade = this.maxVelocidade

        var log = "\n[ ↑ ] VOCÊ UPOU E AGORA ESTÁ NO NÍVEL ${this.nivel}\n"


        return log
    }

}