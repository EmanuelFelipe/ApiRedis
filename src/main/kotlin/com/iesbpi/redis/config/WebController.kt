package com.iesbpi.redis.config

import br.iesb.poo.rpg.loja.Itens
import br.iesb.poo.rpg.personagem.Personagem
import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro
import com.iesbpi.redis.pessoas.IdPessoa
import com.iesbpi.redis.pessoas.PessoaDTO
import com.iesbpi.redis.rpg.Rpg
import com.iesbpi.redis.rpg.TipoPersonagem
import com.iesbpi.redis.rpg.batalha.batalha
//import com.iesbpi.redis.rpg.batalha.batalhaChefe
import com.iesbpi.redis.rpg.personagem.PersonagemDTO
import com.iesbpi.redis.rpg.personagem.PersonagemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.*


val RPG: Rpg = Rpg()

@RestController
@RequestMapping(value = ["/api"])
class WebController0 {

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>


    @Autowired
    lateinit var personagemRepository: PersonagemRepository

    @GetMapping("/jogadores/{id}")
    fun jogadores(@PathVariable id: Int): Any? {

        val jogador = RPG.jogadores.find { it.id == id }
        //val listaJogador = RPG.jogadores

        if (jogador != null) {
            return jogador
        } else {
            return "nao tem personagens criados"
        }
    }

    @PostMapping("/pessoa")
    fun login(@RequestBody pessoa: PessoaDTO): String{

        val novaPessoa = IdPessoa(RPG)
        RPG.ids.add(novaPessoa)
        //pessoaRepository.save(novaPessoa)
        return "Criado com sucesso de ID: ${novaPessoa.id}"

    }

    @PostMapping("/jogador/criarjogador")
    fun criarJogador(@RequestBody personagem: PersonagemDTO): String{

        val novomonstro : PersonagemMonstro = RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO)

        val novojogador = PersonagemJogador(
            personagem.classe,
            personagem.nome,
            personagem.elemento,
            RPG
        )

        novojogador.definirStatusBase()
        novomonstro.definirMonstro(nivelMasmorra = novojogador.nivel)//Conferir com a Isa dps pra ver se faz sentido
        personagemRepository.save(novojogador)

        //if(jogador == null){
        //    redisTemplate.opsForValue().set( "${id}jogador$num", jsonInString)
        //}else{
        //    return "jogador ja existente"
        //}

        RPG.jogadores.add(novojogador)


        return "Criado com sucesso ${if (novojogador.classe == 1){
            "Arqueiro"
        } else if (novojogador.classe == 2){
            "Cavaleiro"
        } else{
            "Mago"
        }
        } ${novojogador.nome} de ID: ${novojogador.id}"

    }

    @GetMapping("/batalha/{n}/{idUrl}/{op}")
    fun batalha(@PathVariable n: String?, @PathVariable idUrl: String?, @PathVariable op: String?): String{

        val bt = n?.toInt()
        val idJogador = idUrl?.toInt()
        val jogador = RPG.jogadores.find { it.id == idJogador }
        var option = op?.toInt()


        return if (jogador != null) {
            if (bt==0){
                val log: String = batalha(jogador, RPG.monstros[0], RPG, option, 0)
                personagemRepository.save(jogador)
                log
            }else {
                val log: String = batalha(jogador, RPG.monstros[0], RPG, option, bt)
                personagemRepository.save(jogador)
                log
            }
        } else {
            "sem conteudo"
        }
    }

    @PostMapping("/loja/{idURL}/{opcao}")
    fun loja(@PathVariable idURL: String?, @PathVariable opcao: String?): String{

        val idJogador = idURL?.toInt()
        val jogador = RPG.jogadores.find { it.id == idJogador }
        val opcao = opcao.toString()
        var log: String = ""
        if (jogador != null) {
            val itens = Itens()
            log = itens.efeito(jogador, opcao)
        }
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