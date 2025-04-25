package com.plcoding.kotlinxcustomserializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 * 自定义的 Kotlinx Serialization 序列化器
 * 单例的序列化器，专门用于 BookWorkDto
 */
object BookWorkDtoSerializer : KSerializer<BookWorkDto> {

    /**
     * 定义序列化结构的元信息
     */
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        serialName = BookWorkDto::class.simpleName!!
    ) {
        element<String?>("description")
    }

    /**
     * 反序列化
     */
    override fun deserialize(decoder: Decoder): BookWorkDto = decoder.decodeStructure(descriptor) {
        var description: String? = null

        while (true) {
            when (decodeElementIndex(descriptor)) {
                0 -> {
                    // 只处理 JSON 格式，所以先强制转为 JsonDecoder
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
                        "This serializer only works with JSON"
                    )
                    val element = jsonDecoder.decodeJsonElement()

                    description = if (element is JsonObject) {
                        // 如果 description 是对象，就用 DescriptionDto 来反序列化，再取出其中的 .value
                        decoder.json.decodeFromJsonElement(
                            element = element,
                            deserializer = DescriptionDto.serializer()
                        ).value
                    } else if (element is JsonPrimitive && element.isString) {
                        // 如果是普通字符串，就直接拿 .content
                        element.content
                    } else null
                }

                CompositeDecoder.DECODE_DONE -> break
                else -> throw SerializationException("Invalid index")
            }
        }

        return@decodeStructure BookWorkDto(description)
    }

    /**
     * 序列化
     */
    override fun serialize(encoder: Encoder, value: BookWorkDto) = encoder.encodeStructure(
        descriptor
    ) {
        value.description?.let {
            encodeStringElement(descriptor, 0, it)
        }
    }
}