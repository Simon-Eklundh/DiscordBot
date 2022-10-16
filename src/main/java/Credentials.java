public class Credentials {

    static String getTEST_TOKEN() {
        return System.getenv("TEST_TOKEN");
    }

    static String getTOKEN() {
        return System.getenv("TOKEN");
    }

    static String getDB_NAME() {
        return System.getenv("DB_NAME");
    }

    static String getUSERNAME() {
        return System.getenv("SQL_USERNAME");
    }

    static String getPASSWORD() {
        return System.getenv("SQL_PASSWORD");
    }

    static String getIP() {
        return System.getenv("IP");
    }

    static String getPORT() {
        return System.getenv("PORT");
    }
}
