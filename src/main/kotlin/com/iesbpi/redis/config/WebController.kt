package com.iesbpi.redis.config

import br.iesb.poo.rpg.loja.Itens
import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONPObject
import com.google.gson.JsonObject
import com.iesbpi.redis.rpg.Rpg
import com.iesbpi.redis.rpg.batalha.batalha
import com.iesbpi.redis.rpg.batalha.batalhaChefe
import com.iesbpi.redis.rpg.personagem.PersonagemDTO
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.RequestPredicates.GET


val RPG: Rpg = Rpg()

@RestController
@RequestMapping(value = ["/api"])
class WebController0 {

    val mapper = ObjectMapper()

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>

    @GetMapping("/")
    fun rotas(): String {
        return "ROTAS\n\n" +
                "GET Listar Jogadores: /jogadores\n" +
                "GET Listar Monstros: /monstros\n" +
                "POST Criar um novo Jogador: /jogadores/criarjogador\n" +
                "POST Travar uma Batalha: /batalha/(JogadorID)/(AjudanteID)\n" +
                "POST Comprar Itens: /loja/(JogadorID)/(ItemID)\n" +
                "POST Visualizar seu Inventario: /inventario/(JogadorID)\n" +
                "POST Contratar um Mercenário: /taverna/(JogadorID)\n" +
                "PUT Enviar uma Menssagem: /taverna/chat/(JogadorID)/(Menssagem)\n" +
                "GET Visualizar o Chat de Menssagens: /taverna/chat"
    }

    @GetMapping("/jogadores")
    fun jogadores(): Any? {

        val listaJogador = redisTemplate.opsForValue().get("Jogador")

        if (listaJogador != null) {
            return listaJogador
        } else {
            return "nao tem personagens salvos"
        }
    }

    @PostMapping("/jogador/criarjogador")
    fun criarJogador(@RequestBody personagem: PersonagemDTO): String{

        val jogador1 = redisTemplate.opsForValue().get("Jogador1")
        val jogador2 = redisTemplate.opsForValue().get("Jogador2")
        val jogador3 = redisTemplate.opsForValue().get("Jogador3")

        val novojogador = PersonagemJogador(
            personagem.classe,
            personagem.nome,
            personagem.elemento,
            RPG
        )

        novojogador.definirStatusBase() //Conferir com a Isa dps pra ver se faz sentido


        RPG.jogadores.add(novojogador)

        val jsonInString = mapper.writeValueAsString(novojogador)
        if(jogador3 != null && jogador2 != null && jogador1 != null){
            return "limite de criação de personagens atingido"
        }else{
            if(jogador1 == null){
                redisTemplate.opsForValue().set("Jogador" + 1, jsonInString)
            }
            if (jogador1 != null){
                redisTemplate.opsForValue().set("Jogador" + 2, jsonInString)
            }
            if (jogador2 != null && jogador1 != null){
                redisTemplate.opsForValue().set("Jogador" + 3, jsonInString)
            }
        }



        return "Criado com sucesso ${if (novojogador.classe == 1){
            "Arqueiro"
        } else if (novojogador.classe == 2){
            "Cavaleiro"
        } else{
            "Mago"
        }
        } ${novojogador.nome} de ID: ${novojogador.id}"

    }

    @GetMapping("/batalha/{idURL}")
    fun batalha(@PathVariable idURL: String?): String{

        val idJogador = idURL?.toInt()
        val jogador = RPG.jogadores.find { it.id == idJogador }

        if (jogador != null) {
            if (jogador.batalhas % 10 == 0 && (1..10).random() > 5) {
                val log: String = batalhaChefe(jogador, RPG)
                return log
            } else {
                val log: String = batalha(jogador, RPG)
                return log
            }
            jogador.batalhas++
        } else {
            return "No Content"
        }
    }

    @PostMapping("/loja/{idURL}/{opcao}")
    fun loja(@PathVariable idURL: String?, @PathVariable opcao: String?): String{
        val idJogador = idURL?.toInt()
        val opcao = opcao.toString()
        val jogador = RPG.jogadores.find { it.id == idJogador }

        if (jogador != null) {

            val itens = Itens(opcao, "", "", "", -1, jogador)
            val retorno = itens.buscar(opcao)

            if (!retorno.isNullOrEmpty()) {

                if (jogador.dinheiro >= retorno[4].toInt()) {
                    jogador.dinheiro = jogador.dinheiro - retorno[4].toInt()

                    itens.efeito(jogador, opcao)

                    if (retorno[1] != "poção") {
                        var eff = itens.buscar(opcao)[3].split(".") as ArrayList<String>
                        if (eff[0] == "atk") {
                            var arr = arrayListOf<String>(retorno[1], "O equipamento é ${retorno[2]}", "${eff[1]} de ataque")
                            jogador.inventario.add(arr)

                        }else{
                            var arr = arrayListOf<String>(retorno[1], "O equipamento é ${retorno[2]}", "${eff[1]} de defesa")
                            jogador.inventario.add(arr)
                        }

                    }

                    return "Você comprou ${retorno[2]} pelo valor de ${retorno[4]} moedas de ouro!\n" + "Muito Obrigada! Volte sempre"


                } else {
                    return "Você não tem moedas de ouro suficientes para comprar ${retorno[2]} e não vendemos fiado"
                }

            } else {

                return "Infelizmente estamos com falta de estoque!\n" + "Muito Obrigada! Agradeçemos a compreensão"
            }
        } else {
            return "Verifique o ID, jogador não existe!!"

        }
    }

    @PostMapping("/inventario/{idURL}")
    fun inventario(@PathVariable idURL: String?): Any? {
        //val idJogador = idURL?.toInt()
        val jogador = redisTemplate.opsForValue().get("Jogador")


        if (jogador != null) {

            if (jogador != null) {

                return redisTemplate.opsForValue().get("Jogador")
            } else {
                return "Você não tem itens"
            }
        } else {
            return "Não existe jogador"
        }
    }




    @GetMapping("/monstros")
    fun monstros(): MutableList<PersonagemMonstro> {
        if (RPG.monstros.isNotEmpty()) {
            return RPG.monstros
        } else {
            return RPG.monstros
        }
    }

}