package com.amplitude.api;

import java.io.IOException;

import okhttp3.Call;

public interface NetworkClient {

    Response uploadEvents(EventUploadRequest eventUploadRequest, Call.Factory client)
            throws IOException, IllegalArgumentException;

}
