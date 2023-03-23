package com.tesera.core.model

@JvmInline
value class CredentialsField(
    private val credential: CharArray
) {
    fun clear() = credential.fill(' ')
    fun isEmpty() = credential.isEmpty()
    fun concatToString() = credential.concatToString()

    companion object {
        fun String.toCredentialsField() = CredentialsField(this.toCharArray())
    }
}