package com.vdrop.vdrop_sports_sdk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by dennis on 29/5/17.
 */

public class ProgressRequest extends RequestBody {

    private File mFile;
    private UploadCallbacks mListener;

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public interface UploadCallbacks {
        void onProgressUpdate(int progress);

        void onError();
    }

    public ProgressRequest(final File mFile, final UploadCallbacks mListener) {
        this.mFile = mFile;
        this.mListener = mListener;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("video/*");
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long totalSize = mFile.length();
        long uploaded = 0;

        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                mListener.onProgressUpdate((int) ((uploaded / (float) totalSize) * 100));
                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            in.close();
        }
    }
}
