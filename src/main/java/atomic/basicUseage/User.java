package atomic.basicUseage;

public class User {
    private String userName;
    public volatile int age;

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
