package com.amplitude.api;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;

public class DefaultNetworkClient implements NetworkClient {

    @Override
    public Response uploadEvents(EventUploadRequest eventUploadRequest, Call.Factory client)
            throws IOException, IllegalArgumentException {

        String apiVersionString = "" + eventUploadRequest.getApiVersion();
        String timestampString = "" + eventUploadRequest.getUploadTime();

        FormBody body = new FormBody.Builder()
                .add("v", apiVersionString)
                .add("client", eventUploadRequest.getApiKey())
                .add("e", eventUploadRequest.getEvents())
                .add("upload_time", timestampString)
                .add("checksum", eventUploadRequest.getChecksum())
                .build();

        Request request;
        Request.Builder builder = new Request.Builder()
                .url(eventUploadRequest.getUrl())
                .post(body);

        if (!Utils.isEmptyString(eventUploadRequest.getBearerToken())) {
            builder.addHeader("Authorization",
                    "Bearer " + eventUploadRequest.getBearerToken());
        }
        request = builder.build();

        okhttp3.Response response = client.newCall(request).execute();
        return new Response(response.code(), response.body() == null
                ? ""
                : response.body().string());
    }
}
