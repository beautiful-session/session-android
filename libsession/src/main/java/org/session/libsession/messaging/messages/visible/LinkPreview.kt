package org.session.libsession.messaging.messages.visible

import org.session.libsignal.service.internal.push.SignalServiceProtos

internal class LinkPreview : VisibleMessage(){
    override fun fromProto(proto: SignalServiceProtos.Content): LinkPreview? {
        TODO("Not yet implemented")
    }

    override fun toProto(): SignalServiceProtos.Content? {
        TODO("Not yet implemented")
    }
}