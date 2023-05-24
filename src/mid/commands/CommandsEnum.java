package mid.commands;

public enum CommandsEnum {


    HELP("help"),
    INFO("info"),
    SHOW("show"),
    ADD("add"),
    UPDATE("update"),
    REMOVE_BY_ID("remove_by_id"),
    CLEAR("clear"),
    EXECUTE_SCRIPT("execute_script"),
    REMOVE_FIRST("remove_first"),
    ADD_IF_MIN("add_if_min"),
    REMOVE_GREATER("remove_greater"),
    SUM_OF_SHOULD_BE_EXPELLED("sum_of_should_be_expelled"),
    AVERAGE_OF_SHOULD_BE_EXPELLED("average_of_should_be_expelled"),
    MIN_BY_NAME("min_by_name"),
    RESPONSE_TEXT("response_text"),
    RESPONSE_STUDY_GROUPS("response_study_groups"),

    RESPONSE_INT("response_int"),
    RESPONSE_ERR("response_err"),

    RESPONSE_STUDY_GROUP("response_study_group"),
    SAVE("save"),
    EXIT("exit"),

    REGISTER_USER("register"),
    LOGIN_USER("login"),
    UNKNOWN_COMMAND("unknown");

    private final String name;

    CommandsEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
