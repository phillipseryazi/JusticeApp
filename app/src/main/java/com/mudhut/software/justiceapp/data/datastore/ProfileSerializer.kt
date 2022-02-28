package com.mudhut.software.justiceapp.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.mudhut.software.justiceapp.LocalProfile
import java.io.InputStream
import java.io.OutputStream


object ProfileSerializer : Serializer<LocalProfile> {
    override val defaultValue: LocalProfile
        get() = LocalProfile.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): LocalProfile {
        return try {
            LocalProfile.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Can not read proto", exception)
            defaultValue
        }
    }

    override suspend fun writeTo(t: LocalProfile, output: OutputStream) {
        t.writeTo(output)
    }

}

