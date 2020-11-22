package uz.tafakkur.sport.telegram;

import java.util.Hashtable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotComponent {
    private final Logger log = LoggerFactory.getLogger(BotComponent.class);

    private final BotService botService;

    public BotComponent(BotService botService) throws TelegramApiException {
        this.botService = botService;
        startBot();
    }

    private void startBot() throws TelegramApiException {
        log.info("Starting BOTs...");

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        Hashtable<String, String> bots = new Hashtable<>();
        bots.put("zaltopbot", "1378757694:AAFL1fDwBRvrdHEGvFJNpXB_FALHU6dH6wA");
        //        bots.put("polyatopbot", "1229985555:AAEi-U9_PFEC5kTZAt-lkfopK2N6ArAxhlE");

        bots.forEach(
            (botName, token) -> {
                try {
                    telegramBotsApi.registerBot(new SportsBot(botService, botName, token));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        );
    }
}
