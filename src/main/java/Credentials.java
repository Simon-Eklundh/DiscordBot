public class Credentials {

    String getTEST_TOKEN(){
        return System.getenv("TEST_TOKEN");
    }

    String getTOKEN() {
        return System.getenv("TOKEN");
    }

     String getDB_NAME() {
        return System.getenv("DB_NAME");
    }

     String getUSERNAME() {
        return System.getenv("SQL_USERNAME");
    }

     String getPASSWORD() {
        return System.getenv("SQL_PASSWORD");
    }

     String getIP() {
        return System.getenv("IP");
    }

     String getPORT() {
        return System.getenv("PORT");
    }
}
