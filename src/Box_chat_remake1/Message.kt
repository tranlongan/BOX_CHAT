package Box_chat_remake1

import java.io.File
import java.io.Serializable

data class Message(
        val contentMessage: String,
        val file: ByteArray? = null
) : Serializable