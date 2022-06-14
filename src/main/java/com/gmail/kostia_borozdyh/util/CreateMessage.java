package com.gmail.kostia_borozdyh.util;


import com.gmail.kostia_borozdyh.dto.OrderDTO;

public class CreateMessage {
    public static final String THEME_CHANGE_PAYMENT_STATUS = "Підтвердження заявки";
    public static final String THEME_SEND_ORDER = "Заявка№";
    public static final String THEME_BLOCK_USER = "Блокування акаунта";
    public static final String THEME_UN_BLOCK_USER = "Розблокування акаунта";
    public static final String THEME_DELETE_USER = "Видалення акаунта";
    public static final String THEME_RESTORE_PASSWORD = "Відновнення паролю";
    public static final String THEME_SEND_CODE = "Отримання посилки";
    public static final String THEME_PUT_ON_RECORD = "Інформація щодо доставки";
    public static final String TEXT_CHANGE_PAYMENT_STATUS = "Менеджер підтвердив вашу заявку на доставку вантажу, до слпати ";
    public static final String TEXT_BLOCK_USER = "Ваш акаунт заблоковано.";
    public static final String TEXT_UN_BLOCK_USER = "Ваш акаунт розблоковано, дякуємо за очікування!";
    public static final String TEXT_DELETE_USER = "Ваш акаунт видалено, для того щоб користуватися нашими послугами доставки, потрібно зареєструватися знову.";
    public static final String TEXT_RESTORE_PASSWORD = "Для того щоб відновити пароль, введіть данний код:";
    public static final String TEXT_SEND_CODE = "Для того щоб забрати посилку пароль скажіть працівнику данний код:";
    public static final String TEXT_PUT_ON_RECORD = "Ваша посилка знаходиться у відділенні, номер замовлення: ";

    public static String[] messageChangePaymentStatus(Integer price) {
        return new String[]{THEME_CHANGE_PAYMENT_STATUS, TEXT_CHANGE_PAYMENT_STATUS + price};
    }

    public static String[] messageSendOrder(OrderDTO order) {
        String sbTheme = THEME_SEND_ORDER + order.getId();
        String sb = "Опис: " + order.getDescription() + "\n" +
                "Вага: " + order.getWeight() + "\n" +
                "Об'єм: " + order.getVolume() + "\n" +
                "Ціна: " + order.getPrice() + "\n" +
                "Місто відправлення: " + order.getCityFrom() + "\n" +
                "Місто прибуття: " + order.getCityTo() + "\n" +
                "Адреса: " + order.getAddress() + "\n" +
                "Дата створення: " + order.getDateCreate() + "\n" +
                "Дата прибуття: " + order.getDateOfArrival() + "\n" +
                "Статус: " + order.getPaymentStatus().getStatus() + "\n";
        return new String[]{sbTheme, sb};

    }

    public static String[] blockUser() {
        return new String[]{THEME_BLOCK_USER, TEXT_BLOCK_USER};
    }

    public static String[] unBlockUser() {
        return new String[]{THEME_UN_BLOCK_USER, TEXT_UN_BLOCK_USER};
    }

    public static String[] deleteUser() {
        return new String[]{THEME_DELETE_USER, TEXT_DELETE_USER};
    }

    public static String[] restorePassword(String code) {
        return new String[]{THEME_RESTORE_PASSWORD, TEXT_RESTORE_PASSWORD + code};
    }

    public static String[] sendCode(String code) {
        return new String[]{THEME_SEND_CODE, TEXT_SEND_CODE + code};
    }

    public static String[] putOnRecord(Integer id) {
        return new String[]{THEME_PUT_ON_RECORD, TEXT_PUT_ON_RECORD + id};
    }
}
