package tgpr.tricount.model;

public abstract class Security {
    private static User loggedUser = null;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static int getLoggedUserId() {
        return isLogged() ? loggedUser.getId() : -1;
    }

    public static void login(User user) {
        loggedUser = user;
    }

    public static boolean isLogged() {
        return loggedUser != null;
    }

    public static boolean isLoggedUser(User user) {
        return loggedUser.equals(user);
    }

    public static void logout() {
        login(null);
    }

    public static boolean isAdmin() {
        return loggedUser != null && loggedUser.isAdmin();
    }
}
