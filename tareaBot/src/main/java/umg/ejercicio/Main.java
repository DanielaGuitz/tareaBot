package umg.ejercicio;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static <TelegramBotsApi> void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            tareaBot bot = new tareaBot();
            botsApi.registerBot(bot);
            System.out.println("El bot está funcionando...");
        } catch (Exception ex) {
            System.out.println("error: " + ex.getMessage());
        }
    }

