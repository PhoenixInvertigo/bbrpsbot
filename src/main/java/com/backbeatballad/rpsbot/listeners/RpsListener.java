package com.backbeatballad.rpsbot.listeners;

import com.backbeatballad.rpsbot.configs.DiscordConfig;
import com.backbeatballad.rpsbot.services.ThrowService;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RpsListener {

    private DiscordConfig discordConfig;

    private ThrowService throwService;

    public RpsListener(DiscordConfig discordConfig, ThrowService throwService){
        this.discordConfig = discordConfig;
        this.throwService = throwService;
    }

    @PostConstruct
    private void AddListeners(){
        addRPSListener();
    }

    public void addRPSListener(){
        discordConfig.getApi().addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!rps")) {
                new MessageBuilder()
                        .setContent("Choose your fighter!")
                        .addComponents(
                                ActionRow.of(Button.secondary("rock", "Rock", "\u270A"),
                                        Button.secondary("paper", "Paper", "\u270B"),
                                        Button.secondary("scissors", "Scissors", "\u270C")))
                        .send(event.getChannel());
            }
        });

        discordConfig.getApi().addMessageComponentCreateListener(event -> {
            MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
            String customId = messageComponentInteraction.getCustomId();

            switch (customId) {
                case "rock":
                    messageComponentInteraction.createImmediateResponder()
                            .setContent(throwService.RPS("Rock"))
                            .respond();
                    messageComponentInteraction.getMessage().ifPresent(Message::delete);
                    break;
                case "paper":
                    messageComponentInteraction.createImmediateResponder()
                            .setContent(throwService.RPS("Paper"))
                            .respond();
                    messageComponentInteraction.getMessage().ifPresent(Message::delete);
                    break;
                case "scissors":
                    messageComponentInteraction.createImmediateResponder()
                            .setContent(throwService.RPS("Scissors"))
                            .respond();
                    messageComponentInteraction.getMessage().ifPresent(Message::delete);
                    break;
            }
        });
    }
}
