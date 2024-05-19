package my.app.momschoice;

public class Sender  {
    // Define fields as per your JSON request
    private String to;
    private NotificationData data;

    // Constructor
    public Sender(String to, NotificationData data) {
        this.to = to;
        this.data = data;
    }

    // Getter and Setter methods
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

    // Define the inner class for NotificationData
    public static class NotificationData {
        private String title;
        private String body;

        // Constructor
        public NotificationData(String title, String body) {
            this.title = title;
            this.body = body;
        }

        // Getter and Setter methods
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
