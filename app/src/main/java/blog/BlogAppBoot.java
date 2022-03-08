package blog;

import blog.app.BlogApp;

public class BlogAppBoot {
    public static void main(String[] args) throws Exception {
        BlogApp app;

        try {
            app = new BlogApp();
            app.start();
        } catch (Throwable e) {
            System.exit(1);
        }
    }
}
