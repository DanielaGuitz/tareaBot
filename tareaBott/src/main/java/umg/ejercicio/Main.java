package umg.ejercicio;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            // Inicializar la API de bots de Telegram
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Crear instancia del bot (tareaBot en este caso)
            tareaBot miBot = new tareaBot();

            // Registrar el bot en Telegram
            botsApi.registerBot(miBot);

            // Confirmación de que el bot está funcionando
            System.out.println("El bot está funcionando...");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());

        }}}





