package br.com.rchlo.cards.configs;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;

@EnableBinding(StreamConfig.ConfirmedTransactionSource.class)
@Configuration
public class StreamConfig {

    public interface ConfirmedTransactionSource {

        @Output
        MessageChannel confirmedTransactionsTopic();

    }
}
