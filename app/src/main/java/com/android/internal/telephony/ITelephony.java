package com.android.internal.telephony;

/**
 * Created by Hammad on 12/24/2017.
 */

public interface ITelephony {

    boolean endCall();

    void answerRingingCall();

    void silenceRinger();
}
