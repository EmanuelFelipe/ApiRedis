package br.iesb.poo.rpg.personagem

import com.iesbpi.redis.rpg.Rpg
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("Personagem")
open class Personagem() {

    constructor(nick: String, element: Int): this(){
        nome = nick
        elemento = element
    }
    @Id
    var id: Int = -1

    var nome: String ?= null

    var sorte: Int = 0
    var nivel: Int = 1
    var dinheiro: Int = 10
    var magia: Int = 0

    //Vai ser o local aonde vai armazenar as mudanças definitivas nos status

    var maxVida: Int = 1
    var maxMana: Int = 1
    var maxAtaque: Int = 1
    var maxDefesa: Int = 1
    var maxVelocidade: Int = 1

    //Vai ser o local aonde vai armazenar as mudanças temporárias nos status, como em batalhas

    var pontosVida: Int = 1
    var pontosMana: Int = 1
    var ataque: Int = 1
    var defesa: Int = 1
    var velocidade: Int = 1

    //Nós vamos definir os statusBase de cada classe e mosntro para serem usados no levelUp (Ex: statusBase de um Pokémon no Lv 1)

    var statusBaseVida:Int = 1
    var statusBaseMana: Int = 1
    var statusBaseAtaque: Int = 1
    var statusBaseDefesa: Int = 1
    var statusBaseVelocidade: Int = 1

    private var stab: Int = 2
    var bonusEfetivo: Int = 2


    // Água - 1; Fogo - 2; Natureza - 3; Luz - 4; Escuridão - 5;
    // Água > Fogo > Natureza > Água || Luz > Escuridão > Luz

    var elemento: Int ?= null

    fun calcularDano(nivelAtacante: Int,
                     statusAtaqueAtacante: Int,
                     statusDefesaVitima: Int,
                     elementoAtaque: Int,
                     elementoAtacante: Int,
                     elementoVitima: Int): Int{ //Função que calcula o dano de um ataque

        if(elementoAtaque != elementoAtacante) {

            stab = 1

        }

        //INICIO AGUA

        var dano: Int = 0
        var poderAtaque: Int = 10

        if(elementoAtaque == 1 && elementoVitima == 1){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

        } else if(elementoAtaque == 1 && elementoVitima == 2){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo


        } else if(elementoAtaque == 1 && elementoVitima == 3){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * 1/2


        } else if(elementoAtaque == 1 && elementoVitima == 4){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


        } else if(elementoAtaque == 1 && elementoVitima == 5){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

        }

        //INICIO FOGO

        else if(elementoAtaque == 2 && elementoVitima == 1){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * 1/2

        } else if(elementoAtaque == 2 && elementoVitima == 2){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


        } else if(elementoAtaque == 2 && elementoVitima == 3){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo


        } else if(elementoAtaque == 2 && elementoVitima == 4){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


        } else if(elementoAtaque == 2 && elementoVitima == 5){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

        }

        //INICIO NATUREZA

        else if(elementoAtaque == 3 && elementoVitima == 1){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo

        } else if(elementoAtaque == 3 && elementoVitima == 2){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * 1/2


        } else if(elementoAtaque == 3 && elementoVitima == 3){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


        } else if(elementoAtaque == 3 && elementoVitima == 4){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


        } else if(elementoAtaque == 3 && elementoVitima == 5){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

        }

        //INICIO LUZ

        else if(elementoAtaque == 4 && elementoVitima == 1){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

        } else if(elementoAtaque == 4 && elementoVitima == 2){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


        } else if(elementoAtaque == 4 && elementoVitima == 3){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


        } else if(elementoAtaque == 4 && elementoVitima == 4){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


        } else if(elementoAtaque == 4 && elementoVitima == 5){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo

        }

        //INICIO ESCURIDAO

        else if(elementoAtaque == 5 && elementoVitima == 1){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

        } else if(elementoAtaque == 5 && elementoVitima == 2){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


        } else if(elementoAtaque == 5 && elementoVitima == 3){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


        } else if(elementoAtaque == 5 && elementoVitima == 4){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo


        } else if(elementoAtaque == 5 && elementoVitima == 5){

            dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

        }

        reduzMana(dano)

        return dano
    }

    fun atacarPersonagem(ataquePersonagem: Int, defesaInimigo: Int): Int{

        var dano: Int = 0

        dano = (ataquePersonagem - 3 .. ataquePersonagem).random() - (defesaInimigo/2)

        if(dano <= 0){
            dano = 1
        }

        return dano


    }

    fun defender(defesaPersonagem: Int): Int {

        var defesaP: Int = defesaPersonagem

        defesaP * 3

        return defesaP
    }


    fun calcularCura(nivelAtacante: Int, statusMaxManaAtacante: Int){

        var cura: Int = 0

        cura = (nivelAtacante * statusMaxManaAtacante)/2
        this.pontosVida += cura

        if (this.pontosVida > this.maxVida){
            this.pontosVida = this.maxVida
        }

        reduzMana(cura)
    }

    fun reduzMana(valor: Int){
        this.pontosMana = this.pontosMana - valor / 2
    }

    //INTERFACES?
    protected open fun genId(rpgAtual: Rpg): Int {
        return 0
    }

    open fun derrota(rpg: Rpg): String {
        return ""
    }
}