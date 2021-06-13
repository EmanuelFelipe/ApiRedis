package com.iesbpi.redis.config

import br.iesb.poo.rpg.loja.Itens
import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro
import com.iesbpi.redis.rpg.personagem.PersonagemJogadorRepository
import com.iesbpi.redis.rpg.Rpg
import com.iesbpi.redis.rpg.TipoPersonagem
import com.iesbpi.redis.rpg.batalha.batalha
//import com.iesbpi.redis.rpg.batalha.batalhaChefe
import com.iesbpi.redis.rpg.personagem.PersonagemDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.*


val RPG: Rpg = Rpg()

@RestController
@RequestMapping(value = ["/api"])
class WebController0 {

    @Autowired
    lateinit var personagemJogadorRepository: PersonagemJogadorRepository

    @GetMapping("/jogadores/{id}")
    fun jogadores(@PathVariable id: Int): Any? {

        val jogador = personagemJogadorRepository.findById(id)
        //val listaJogador = RPG.jogadores

        if (jogador != null) {
            return jogador
        } else {
            return "nao tem personagens criados"
        }
    }

    @GetMapping("/jogadores/user/{email}")
    fun jogadoresUsuario(@PathVariable email: String): Int {
        return personagemJogadorRepository.findByUserEmail(email).id
    }

    @GetMapping("/jogador/deletar/{email}")
    fun deletar(@PathVariable email: String){
        val personagemDeletado = personagemJogadorRepository.findByUserEmail(email)
        personagemJogadorRepository.delete(personagemDeletado)
    }

    @PostMapping("/jogador/criarjogador")
    fun criarJogador(@RequestBody personagem: PersonagemDTO): Int{

        val novomonstro : PersonagemMonstro = RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO)

        val novojogador = PersonagemJogador(
            personagem.classe,
            personagem.nome,
            personagem.elemento,
            personagem.email,
            RPG
        )

            novojogador.definirStatusBase()
            novomonstro.definirMonstro(nivelMasmorra = novojogador.nivel)//Conferir com a Isa dps pra ver se faz sentido
            personagemJogadorRepository.save(novojogador)


        //if(jogador == null){
        //    redisTemplate.opsForValue().set( "${id}jogador$num", jsonInString)
        //}else{
        //    return "jogador ja existente"
        //}

        RPG.jogadores.add(novojogador)


        return novojogador.id

    }

    @GetMapping("/batalha/{n}/{idUrl}/{op}")
    fun batalha(@PathVariable n: String?, @PathVariable idUrl: String, @PathVariable op: String?): String{

        val bt = n?.toInt()
        val idJogador = idUrl.toInt()
        val jogador = personagemJogadorRepository.findById(idJogador).get()
        var option = op?.toInt()


        return if (jogador != null) {
            if (bt==0){
                if(RPG.monstros.isEmpty()){
                    val novomonstro : PersonagemMonstro = RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO)
                    novomonstro.definirMonstro(nivelMasmorra = jogador.nivel)
                    }

                    val log: String = batalha(jogador, RPG.monstros[0], RPG, option, 0)
                    personagemJogadorRepository.save(jogador)
                    log
                }else {
                    if(RPG.monstros.isEmpty()){
                        val novomonstro : PersonagemMonstro = RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_CHEFE)
                        novomonstro.definirMonstro(nivelMasmorra = jogador.nivel)
                    }

                    val log: String = batalha(jogador, RPG.monstros[0], RPG, option, bt)
                    personagemJogadorRepository.save(jogador)
                    log
                }
            } else {
                "sem conteudo"
            }
        }

    @PostMapping("/loja/{idURL}/{opcao}")
    fun loja(@PathVariable idURL: String, @PathVariable opcao: String?): String{

        val idJogador = idURL.toInt()
        val jogador = personagemJogadorRepository.findById(idJogador).get()
        val opcao = opcao.toString()
        var log: String = ""
        if (jogador != null) {
            val itens = Itens()
            log = itens.efeito(jogador, opcao)
            personagemJogadorRepository.save(jogador)
        }
        return log
    }

    @GetMapping("/elemento/{idURL}/{elm}")
    fun mudarElemento(@PathVariable idURL: String, @PathVariable elm: Int): String{
        val idJogador = idURL.toInt()
        val jogador = personagemJogadorRepository.findById(idJogador).get()

        var log: String = ""
        if (jogador != null) {
            val itens = Itens()
            log = itens.elemento(jogador, elm)
        }
        personagemJogadorRepository.save(jogador)
        return log

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