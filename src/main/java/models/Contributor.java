package models;

public class Contributor {
    private String login;
    private int commitCount;

    public String getLogin() {
        return login;
    }

    public int getCommitCount() {
        return commitCount;
    }

    public Contributor(String login, int commitCount) {

        this.login = login;
        this.commitCount = commitCount;
    }

    @Override
    public String toString() {
        return new StringBuilder("Login: ")
                .append(login)
                .append("; Commits: ")
                .append(commitCount)
                .toString();
    }
}
