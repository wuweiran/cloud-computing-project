package edu.nyu.cs9223.project.notif;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

/**
 * @author wuweiran
 */
public class SnsClientFactory {
    private volatile static SnsClient snsClient;

    private SnsClientFactory() {
    }

    public static SnsClient getInstance() {
        if (snsClient == null) {
            synchronized (SnsClient.class) {
                if (snsClient == null) {
                    snsClient = buildSnsClient();
                }
            }
        }
        return snsClient;
    }

    private static SnsClient buildSnsClient() {
        final AmazonSNS sns = AmazonSNSClientBuilder
                .defaultClient();
        return new SnsClient(sns);
    }
}