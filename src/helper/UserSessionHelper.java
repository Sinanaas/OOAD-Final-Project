package helper;
public class UserSessionHelper {
        private static UserSessionHelper instance;
        private String loggedInUserId;

        private UserSessionHelper() {
                // private constructor to enforce singleton pattern
        }

        public static UserSessionHelper getInstance() {
                if (instance == null) {
                        instance = new UserSessionHelper();
                }
                return instance;
        }

        public String getLoggedInUserId() {
                return loggedInUserId;
        }

        public void setLoggedInUserId(String loggedInUserId) {
                this.loggedInUserId = loggedInUserId;
        }

        public void clear() {
                this.loggedInUserId = null;
        }
}
