package com.codingwithrufat.bespeaker.common.listeners

import com.codingwithrufat.bespeaker.common.utils.CallType
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.CallNotification

interface OnClickedCallButtonListener {
    fun onClickButton(callUserID: String, callModel: CallNotification)
}