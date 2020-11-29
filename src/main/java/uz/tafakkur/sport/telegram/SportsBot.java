package uz.tafakkur.sport.telegram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.tafakkur.sport.service.dto.ProfileDTO;

public class SportsBot extends TelegramLongPollingBot {
    private final Logger log = LoggerFactory.getLogger(SportsBot.class);

    private final BotService botService;

    private final String botName;

    private final String botToken;

    private static final HashMap<String, String> localization = MessageConstants.MESSAGES_CYRILL;

    private String currentChatId;

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
            currentChatId = update.getMessage().getChatId().toString();
            handleText(update, update.getMessage().getText());
        }
        if (update.getCallbackQuery() != null) {
            log.debug("Callback query: {}", update.getCallbackQuery().getData());
            currentChatId = update.getCallbackQuery().getMessage().getChatId().toString();
            handleCallbackQuery(update);
        }
    }

    private void handleText(Update update, String inputText) {
        if (inputText.startsWith("/")) {
            handleCommand(update, inputText);
        } else {
            handleButtonCommand(update, inputText);
        }
    }

    private void handleCommand(Update update, String inputText) {
        if (inputText.equals("/start")) {
            ProfileDTO profileDTO = botService.createOrUpdateProfile(update);
            String messageText;
            if (profileDTO == null) {
                messageText = "Рўйхатдан ўтишда хатолик юз берди!";
            } else {
                messageText = "Дўстларингиз билан футбол ўйнашингиз учун " + "<b>зал</b> ёки <b>поля</b> топамиз.";
            }

            sendHomeButtons(update, messageText);
        }
    }

    private void handleButtonCommand(Update update, String inputText) {
        if (inputText.equals(localization.get("about"))) {
            sendAbout(update);
        }
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

    private void sendHomeButtons(Update update, String message) {
        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>() {

            {
                KeyboardRow keyboardButtons = new KeyboardRow();
                keyboardButtons.add(localization.get("search"));
                add(keyboardButtons);

                keyboardButtons = new KeyboardRow();
                keyboardButtons.add(localization.get("currentLocation"));
                add(keyboardButtons);

                keyboardButtons = new KeyboardRow();
                keyboardButtons.add(localization.get("about"));
                add(keyboardButtons);
            }
        };

        sendMessage(message, "HTML", new ReplyKeyboardMarkup(keyboardRowList));
    }

    private void sendMessage(String message, String mode, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(currentChatId);
        if (mode != null) {
            sendMessage.setParseMode(mode);
        }
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        sendMessage.setText(message);
        sendMessage(sendMessage);
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

    private void sendAbout(Update update) {
        String message = localization.get("aboutMsg");
        sendMessage(message, "HTML", null);
    }
}
