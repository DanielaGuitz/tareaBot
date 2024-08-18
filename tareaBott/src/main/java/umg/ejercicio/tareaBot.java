package umg.ejercicio;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class tareaBot extends TelegramLongPollingBot {
    // Mapa para almacenar la información personal de cada usuario
    private Map<Long, String[]> userInfo = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "NarutoDanibot"; // Reemplaza con el nombre de tu bot
    }

    @Override
    public String getBotToken() {
        return "7030982991:AAGCt1hISkMUYZNve7xO3olf1tJVoIu92VI"; // Reemplaza con el token de tu bot
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String nombre = update.getMessage().getFrom().getFirstName();
            long chatId = update.getMessage().getChatId();

            // Comando /start
            if (messageText.equalsIgnoreCase("/start")) {
                sendWelcomeMessage(chatId);
            }
            // Comando de /info
            else if (messageText.equalsIgnoreCase("/info")) {
                if (userInfo.containsKey(chatId)) {
                    String[] info = userInfo.get(chatId);
                    sendText(chatId, "📘 Nombre: " + nombre + "\n" +
                            "🎓 Carnet: " + info[0] + "\n" +
                            "📚 Semestre: " + info[1]);
                } else {
                    sendText(chatId, "Por favor, ingresa tu carnet seguido del semestre en este formato:\n" +
                            "/setinfo [carnet] [semestre]");
                }
            }
            // Comando para establecer información del usuario
            else if (messageText.startsWith("/setinfo")) {
                String[] parts = messageText.split(" ", 3);
                if (parts.length == 3) {
                    String carnet = parts[1];
                    String semestre = parts[2];
                    userInfo.put(chatId, new String[]{carnet, semestre});
                    sendText(chatId, "Tu información ha sido guardada. Usa /info para verla.");
                } else {
                    sendText(chatId, "Formato incorrecto. Usa /setinfo seguido de tu carnet y semestre.");
                }
            }
            // Comando /progra
            else if (messageText.equalsIgnoreCase("/progra")) {
                sendText(chatId, "¿Qué opinas de la clase de Programación II?\n" +
                        "1. 👍 Me encanta\n" +
                        "2. 🤔 Es desafiante\n" +
                        "3. 😅 Un poco difícil");
            }
            // Comando /hola
            else if (messageText.equalsIgnoreCase("/hola")) {
                LocalDate today = LocalDate.now();
                String fecha = today.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy"));
                String clima = obtenerClima();
                sendText(chatId, "Hola " + nombre + ", hoy es " + fecha + ". El clima es " + clima + ".");
            }
            // Comando /cambio [monto]
            else if (messageText.startsWith("/cambio")) {
                String[] parts = messageText.split(" ");
                if (parts.length == 2) {
                    try {
                        double euros = Double.parseDouble(parts[1]);
                        double quetzales = euros * obtenerTipoCambio(); // Llamada a función
                        sendText(chatId, euros + " euros son " + quetzales + " quetzales.");
                    } catch (NumberFormatException e) {
                        sendText(chatId, "Por favor, ingresa un número válido después de /cambio.");
                    }
                }
            }
            // Comando /grupal mensaje
            else if (messageText.startsWith("/grupal")) {
                List<Long> chatIds = List.of(654654654L, 48791321L, 46573123L); // Lista de IDs
                String mensaje = messageText.substring(8); // Mensaje después de '/grupal '

                for (Long id : chatIds) {
                    sendText(id, "Mensaje para ti: " + mensaje);
                    try {
                        Thread.sleep(2000); // Simula un retraso para evitar el spam
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // Método para enviar mensajes
    public void sendText(Long who, String what) {
        SendMessage sm = new SendMessage();
        sm.setChatId(who.toString());
        sm.setText(what);
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Método para enviar un mensaje de bienvenida con un botón
    public void sendWelcomeMessage(Long chatId) {
        // Crear el botón
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Comenzar");
        button.setCallbackData("start");

        // Crear el teclado con el botón
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);

        // Crear el mensaje
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("¡Hola! Bienvenido al bot. Haz clic en el botón para comenzar.");
        message.setReplyMarkup(markup);

        // Enviar el mensaje
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Simulación de tipo de cambio dinámico
    private double obtenerTipoCambio() {
        return 8.9; // Aquí podrías conectar con una API real o usar este valor simulado.
    }

    // Simulación de clima dinámico
    private String obtenerClima() {
        String[] climas = {"soleado ☀️", "nublado ☁️", "lluvioso 🌧️"};
        return climas[new Random().nextInt(climas.length)];
    }
}




