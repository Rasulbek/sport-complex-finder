package uz.tafakkur.sport.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SportsBot extends TelegramLongPollingBot {
    private final Logger log = LoggerFactory.getLogger(SportsBot.class);

    private final BotService botService;

    private final String botName;

    private final String botToken;

    public SportsBot(BotService botService, String botName, String botToken) {
        this.botService = botService;
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleText(update, update.getMessage().getText());
        }
        if (update.getCallbackQuery() != null) {
            log.debug("Callback query: {}", update.getCallbackQuery().getData());
            handleCallbackQuery(update);
        }
    }

    private void handleText(Update update, String inputText) {
        //        if (inputText.startsWith("/")) {
        //            handleCommand(update, inputText);
        //        } else {
        //            handleButtonCommand(update, inputText);
        //        }
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setParseMode("HTML");
        message.setText("SALOM! HUSH KELIBSIZ");
        sendMessage(message);
    }

    private void handleCallbackQuery(Update update) {
        //        try {
        //            JSONObject jsonObject = new JSONObject(update.getCallbackQuery().getData());
        //            String callbackType = jsonObject.getString("type");
        //            if ("location".equals(callbackType)) {
        //                long cityId = jsonObject.getLong("id");
        //                if (cityId == 0) {
        //                    //request location
        //                } else {
        //                    taqvimBotService.changePlace(update, cityId);
        //                    sendHomeButtons(update, Constants.MESSAGES_CYRILL.get("placeChanged"));
        //                }
        //            }
        //        } catch (Exception e) {
        //            sendErrorMessage(update);
        //        }
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendErrorMessage(Update update) {
        //        sendHomeButtons(update, null);
    }
}
