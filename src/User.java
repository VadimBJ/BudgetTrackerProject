import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
  private final String login;
  private String passwordHash;

  public User(String login, String password) {
    this.login = login;
    this.passwordHash = passwordHash(login + "passwordHash" + password + "ax" + login);
  }

  public static String makeHash(String password, String login) {
    return passwordHash(login + "passwordHash" + password + "ax" + login);
  }

  private static String passwordHash(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
      return bytesToHex(hashedBytes);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error hashing password", e);
    }
  }

  private static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

  public String getLogin() {
    return login;
  }

  public String getPasswordHash() {
    return passwordHash;
  }
}
