import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.vdurmont.emoji.EmojiParser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update){
        String message_text = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();
        if(message_text.equals("/start")){
            SendMessage message = new SendMessage();
            message.setChatId(chat_id);
            message.setText("Привіт" + EmojiParser.parseToUnicode(":smile:") + "!\nЯ твій особистий помічник в тренуваннях"+EmojiParser.parseToUnicode(":muscle:") +". Для того щоб ознайомитись з моїм функціоналом надішли коману /commands. Але перед тим потрібно зареєструватись(потрібно тільки надісллати команду /register)");
            messageCheck(message);
        }else if(message_text.equals("/register")){
            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            String user_username = update.getMessage().getChat().getUserName();
            long user_id = update.getMessage().getChat().getId();
            Date date = new Date();
            User user = new User(user_first_name, user_last_name, user_username, user_id, date);
            SendMessage message = new SendMessage();
            message.setChatId(chat_id);
            SessionFactory sessionFactory = new Configuration()
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
            Session session;
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            sessionFactory.close();

//            int i = 1;
//
//            while(true){
//                session = sessionFactory.getCurrentSession();
//                session.beginTransaction();
//                User userfromdb = session.get(User.class, i);
//                SendMessage message = new SendMessage();
//                message.setChatId(chat_id);
//                if(userfromdb.getUsername() == null){
//                    break;
//                }
//                if(userfromdb.getTelegram_id() == user_id){
//                    continue;
//                }else{
//                    session = sessionFactory.getCurrentSession();
//                    session.beginTransaction();
//                    User user = new User(user_first_name, user_last_name, user_username, user_id, date);
//                    session.save(user);
//                    session.getTransaction().commit();
//                    sessionFactory.close();
//                    message.setText("Вітаю" + EmojiParser.parseToUnicode(":thumbsup:") + "! Ви успішно зареєстровані, можете розпочинати роботу");
//                    messageCheck(message);
//                }
//                i++;
//            }
//            session.save(user);
//            session.getTransaction().commit();
//            sessionFactory.close();


            message.setText("Вітаю" + EmojiParser.parseToUnicode(":thumbsup:") + "! Ви успішно зареєстровані, можете розпочинати роботу.");
            messageCheck(message);
        }else if(message_text.equals("/commands")){
            SendMessage message = new SendMessage();
            String path = "src/commands.txt";
            String content = null;
            try{
                content = FileReader(path, Charsets.UTF_8);
            }catch (IOException e){
                e.printStackTrace();
            }
            message.setChatId(chat_id);
            message.setText(content);
            messageCheck(message);
        }
    }
    @Override
    public String getBotUsername(){
        return "WertigoBot";
    }
    @Override
    public String getBotToken(){
        return "5594175677:AAGnbOBqsC9lDRZke4k7Ir9cAlNJekHDkDA";
    }
    public static String FileReader(String path, Charset encoding) throws IOException {
        List<String> lines = Files.readLines(new File(path), encoding);
        return Joiner.on(System.lineSeparator()).join(lines);
    }
    public void messageCheck(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

//    public boolean checkUsers(User user){
//        boolean result = false;
//        SessionFactory sessionFactory =new Configuration()
//                .addAnnotatedClass(User.class)
//                .buildSessionFactory();
//        Session session;
//
//        while(true){
//            session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
//            User UserFromDB = session.get(User.class, 1);
//
//        }
//        return result;
//    }
}
