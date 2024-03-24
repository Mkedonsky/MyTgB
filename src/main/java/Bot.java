import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    ReplyKeyboardMarkup replyKeyboardMarkup;
    Storage storage;
    ArrayList<Integer> arrayMessageIDs = new ArrayList<>();

    Bot() {
        storage = new Storage();
        initKeyboard();
    }

    @Override
    public String getBotUsername() {
        String BOT_NAME = "NotesreferencesBot";
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        String BOT_TOKEN = "6354474106:AAFxjSSa2sLJk-31iuxBuBVX10uRGTMUSm8";
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        parseMessage(update);
    }

    public void parseMessage(Update update) {
        SendMessage send = new SendMessage();
        String messageOut = "Сообщение не распознано";
        switch (update.getMessage().getText()) {
            case "/start":
                messageOut = "Приветствую, бот знает много цитат. Жми /get, чтобы получить случайную из них";
            case "/get":
            case "Просвяти":
                messageOut = storage.getRandQuote();
                send.setChatId(update.getMessage().getChatId().toString());
                send.setText(messageOut);
                send.setReplyMarkup(replyKeyboardMarkup);
                try {
                    arrayMessageIDs.add(execute(send).getMessageId());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "Перезапуск":
                try {
                    String chatID = update.getMessage().getChatId().toString();
                    arrayMessageIDs.add(update.getMessage().getMessageId());
//                    for (int i = 0; i < count; i++) {
//                        DeleteMessage deleteMessage = new DeleteMessage(chatID,i);
//                        execute(deleteMessage);
//                    }
                    for (int index:arrayMessageIDs) {
                        DeleteMessage deleteMessage = new DeleteMessage(chatID,index);
                        this.execute(deleteMessage);
                    }

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
        }

    }


    private void initKeyboard() {
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        ArrayList<KeyboardRow> keyboardRowArrayList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRowArrayList.add(keyboardRow);
        keyboardRow.add(new KeyboardButton("Просвяти"));
        keyboardRow.add(new KeyboardButton("Перезапуск"));
        replyKeyboardMarkup.setKeyboard(keyboardRowArrayList);
    }
}
