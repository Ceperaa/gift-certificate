package ru.clevertec.ecl.util;

public class Constant {

    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    public static final String HTTP_URL = "http://%s/api%s%s";
    public static final String SEQUENCE = "/sequence";
    public static final String SEQUENCE_NEXTVAL = "/sequence/nextval";
    public static final String RANGE = "/range?firstId=%s&lastId=%s";
    public final static String ORDERS = "/v1/orders";
    public final static String ORDER_COMMIT_LOG = "/v1/order-commit-logs";
    public final static String COMMIT_LOG = "/v1/commit-logs";
    public final static String RECOVERY = "/recovery";
    public final static String USER = "/v1/users";
    public final static String TAG = "/v1/tags";
    public final static String GIFT_CERTIFICATE = "/v1/gift-certificates";

    public static final String REGEX_ENTITIES = "/v1/orders|/v1/tags|/v1/users|/v1/gift-certificates";
    public static final String REGEX_DIGITS = "/(\\d+)";
    public static final String REGEX_HOST_PORT = "[a-zA-Z\\d]+:\\d{4}";
    public final static String REPEAT = "entity";
    public static final String ID = "id";

    public static final String GIFT_CERTIFICATE_SEQ = "gift_certificate_id_seq";
    public static final String USER_SEQ = "users_id_seq";
    public static final String TAG_SEQ = "tag_id_seq";
    public static final String ORDER_SEQ = "orders_id_seq";
}
