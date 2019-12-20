package edu.nyu.cs9223.project.notif;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;

/**
 * This sample demonstrates how to make basic requests to Amazon SQS using the
 * AWS SDK for Java.
 * <p>
 * Prerequisites: You must have a valid Amazon Web Services developer account,
 * and be signed up to use Amazon SQS. For more information about Amazon SQS,
 * see https://aws.amazon.com/sqs
 * <p>
 * Make sure that your credentials are located in ~/.aws/credentials
 *
 * @author wuweiran
 */
public class SnsClient {

    private final AmazonSNS sns;

    public SnsClient(AmazonSNS sns) {
        this.sns = sns;
    }

    public void sendSms(String message, String phoneNumber) {
        PublishRequest publishRequest = new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber);
        sns.publish(publishRequest);
    }
}
