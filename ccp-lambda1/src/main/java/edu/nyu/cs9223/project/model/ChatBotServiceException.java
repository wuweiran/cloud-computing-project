package edu.nyu.cs9223.project.model;

import com.amazonaws.annotation.SdkInternalApi;
import com.amazonaws.opensdk.SdkErrorHttpMetadata;
import com.amazonaws.opensdk.internal.BaseException;

/**
 * Base exception for all service exceptions thrown by AI Customer Service API
 */
public class ChatBotServiceException extends com.amazonaws.SdkBaseException implements BaseException {

    private static final long serialVersionUID = 1L;

    private SdkErrorHttpMetadata sdkHttpMetadata;

    private String message;

    /**
     * Constructs a new ChatBotServiceException with the specified error message.
     *
     * @param message Describes the error encountered.
     */
    public ChatBotServiceException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public ChatBotServiceException sdkHttpMetadata(SdkErrorHttpMetadata sdkHttpMetadata) {
        this.sdkHttpMetadata = sdkHttpMetadata;
        return this;
    }

    @Override
    public SdkErrorHttpMetadata sdkHttpMetadata() {
        return sdkHttpMetadata;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @SdkInternalApi
    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
