package br.iesb.poo.rpg.personagem


import com.iesbpi.redis.rpg.Rpg

class PersonagemMonstro(
        novaRaca: Int,
        nomeMonstro: String,
        elementoMonstro: Int,
        rpgAtual: Rpg
) : Personagem(nomeMonstro, elementoMonstro) {

    var raca: Int = 0
    var derrotado = false

    var nivelMasmorra: Int = 0

    fun definirStatusBase(){ //Inicialização dos statusBase

        this.maxAtaque = 6
        this.statusBaseAtaque = 6

        if (raca == 1) {

            nivelMasmorra += (-2..2).random()

            this.maxVida = 6
            this.maxMana = 6
            this.maxDefesa = 6
            this.maxVelocidade = 5

            this.maxAtaque = 6
            this.statusBaseAtaque = 6

            this.statusBaseVida = 6
            this.statusBaseMana = 6
            this.statusBaseDefesa = 6
            this.statusBaseVelocidade = 6


        } else{

            this.maxVida = 10
            this.maxMana = 10
            this.maxDefesa = 10
            this.maxVelocidade = 1

            this.statusBaseVida = 10
            this.statusBaseMana = 10
            this.statusBaseDefesa = 10
            this.statusBaseVelocidade = 10

        }
    }

    init {

        id = genId(rpgAtual)
        raca = novaRaca


         fun statusMonstro(){ //Será utilizada a mesma fórmula usada para calcular os status dos monstros: ((2 * statusBase) * nivel)/100 + nivel + 10
            //obs: caso o personagem tenha alguma vantagem com um atributo, adicionar + 2, caso tenha desvantagem com um atributo, diminuir - 2

            this.maxVida += ((2 * statusBaseVida) * nivelMasmorra)/100 + nivel + 8
            this.maxMana += ((2 * statusBaseMana) * nivelMasmorra)/100 + nivel + 10
            this.maxAtaque += ((2 * statusBaseAtaque) * nivelMasmorra)/100 + nivel + 10
            this.maxDefesa += ((2 * statusBaseDefesa) * nivelMasmorra)/100 + nivel + 10
            this.maxVelocidade += ((2 * statusBaseVelocidade) * nivelMasmorra)/100 + nivel + 12

        }

    }

    override fun genId(rpgAtual: Rpg): Int {
        var novaId = (0..10000).random()
        while (rpgAtual.monstros.find{it.id == novaId} != null){ //TODO EVITAR LOOP INFINITO(CONTADOR?)
            novaId = (0..10000).random()
        }
        return novaId
    }

    override fun derrota(rpg: Rpg): String {
        this.derrotado = true
        return "[ c: ] ${this.nome} FOI DERROTADO\n"
    }
}