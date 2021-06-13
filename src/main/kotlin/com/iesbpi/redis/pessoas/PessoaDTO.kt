package com.iesbpi.redis.pessoas

import org.springframework.stereotype.Repository

@Repository("Users")
data class PessoaDTO(
    val email: String = "",
    val id: Int = 0

)