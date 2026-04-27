package user;

import utils.PropertyReader;

public class UserFactory {
    public static User withAdminPermission() {
        return new User(PropertyReader.getProperty("foridea.user"), PropertyReader.getProperty("foridea.password"));
    }

    public static User withLockedPermission() {
        return new User(PropertyReader.getProperty("foridea.locked_user"), PropertyReader.getProperty("foridea.password"));
    }

    public static User withEmptyLoginPermission() {
        return new User("", PropertyReader.getProperty("foridea.password"));
    }

    public static User withEmptyPasswordPermission() {
        return new User(PropertyReader.getProperty("foridea.user"), "");
    }

    public static User withIncorrectLoginPermission() {
        return new User(PropertyReader.getProperty("foridea.incorrect_user"), PropertyReader.getProperty("foridea.password"));
    }
}
