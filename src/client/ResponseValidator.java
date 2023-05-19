package client;


import mid.fabrics.message.instance.Message;
import mid.logger.ColorLogger;

public class ResponseValidator {


    public static void responseValidate(Message message) {
        switch (message.getCommand()) {
            case RESPONSE_TEXT:
                System.out.printf(ColorLogger.logInfo(message.getData()) + "\n\n");
                break;
            case RESPONSE_STUDY_GROUPS:
                System.out.printf(ColorLogger.logInfo(message.getStudyGroups().toString()) + "\n\n");
                break;
            case RESPONSE_INT:
                System.out.printf(ColorLogger.logInfo(message.getId().toString()) + "\n\n");
                break;
            case RESPONSE_STUDY_GROUP:
                System.out.printf(ColorLogger.logInfo(message.getStudyGroup().toString()) + "\n\n");
                break;
            case RESPONSE_ERR:
                System.out.printf(ColorLogger.logErr(message.getData()) + "\n\n");
                break;
            default:
                System.out.print("Неопознанный ответ" + "\n\n");
        }
    }
}
