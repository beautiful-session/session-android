package org.thoughtcrime.securesms.webrtc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import org.thoughtcrime.securesms.webrtc.audio.SignalAudioManager
import org.webrtc.SurfaceViewRenderer
import javax.inject.Inject

@HiltViewModel
class CallViewModel @Inject constructor(private val callManager: CallManager): ViewModel() {

    val localRenderer: SurfaceViewRenderer?
    get() = callManager.localRenderer

    val remoteRenderer: SurfaceViewRenderer?
    get() = callManager.remoteRenderer

    private var _videoEnabled: Boolean = false

    val videoEnabled: Boolean
        get() = _videoEnabled

    private var _isSpeaker: Boolean = false
    val isSpeaker: Boolean
        get() = _isSpeaker


    enum class State {
        CALL_PENDING,

        CALL_INCOMING,
        CALL_OUTGOING,
        CALL_CONNECTED,
        CALL_RINGING,
        CALL_BUSY,
        CALL_DISCONNECTED,

        NETWORK_FAILURE,
        RECIPIENT_UNAVAILABLE,
        NO_SUCH_USER,
        UNTRUSTED_IDENTITY,
    }

    val audioDeviceState
        get() = callManager.audioDeviceEvents
                .onEach {
                    _isSpeaker = it.selectedDevice == SignalAudioManager.AudioDevice.SPEAKER_PHONE
                }

    val localAudioEnabledState
        get() = callManager.audioEvents.map { it.isEnabled }

    val localVideoEnabledState
        get() = callManager.videoEvents
                .map { it.isEnabled }
                .onEach { _videoEnabled = it }

    val remoteVideoEnabledState
        get() = callManager.remoteVideoEvents.map { it.isEnabled }

    val callState
        get() = callManager.callStateEvents

    val recipient
        get() = callManager.recipientEvents

}