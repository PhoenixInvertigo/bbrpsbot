package com.backbeatballad.rpsbot.listeners;

import com.backbeatballad.rpsbot.configs.DiscordConfig;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RpsListener {

    private DiscordConfig discordConfig;

    public RpsListener(DiscordConfig discordConfig){
        this.discordConfig = discordConfig;
    }

    @PostConstruct
    private void AddListeners(){
        discordConfig.getApi().addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!rps")) {
                new MessageBuilder()
                        .setContent("Choose your fighter!")
                        .addComponents(
                                ActionRow.of(Button.success("success", "Rock", "\u270A"),
                                        Button.danger("danger", "Paper", "\u270B"),
                                        Button.secondary("secondary", "Scissors", "\u270C")))
                        .send(event.getChannel());
            }
        });
    }
}
