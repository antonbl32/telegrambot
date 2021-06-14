package by.telegram.bot;

import name.maratik.spring.telegram.annotation.EnableTelegramBot;
import name.maratik.spring.telegram.config.TelegramBotBuilder;
import name.maratik.spring.telegram.config.TelegramBotType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableTelegramBot
public class BotApplication {
    @Value("${bot.name}")
    private String name;
    @Value("${bot.token}")
    private String botToken;

    @Bean
    public TelegramBotType telegramBotType() {
        return TelegramBotType.LONG_POLLING;
    }

    @Bean
    public TelegramBotBuilder telegramBotBuilder() {
        return new TelegramBotBuilder()
                .username(name)
                .token(botToken);
    }

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

}
