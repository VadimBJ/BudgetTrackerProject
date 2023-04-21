import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
  private final String login;
  private final String passwordHash;

  public User(String login, String password) {
    this.login = login;
    this.passwordHash = passwordHash(login + "passwordHash" + password + "ax" + login);
  }

  /**
   * создает хеш пароля на основе пароля и логина пользователя
   *
   * @param password пароль пользователя
   * @param login логин пользователя
   * @return возвращает строку с хешем пароля
   */
  public static String makeHash(String password, String login) {
    return passwordHash(login + "passwordHash" + password + "ax" + login);
  }

  /**
   * создает хеш пароля на основе пароля пользователя
   *
   * @param password пароль пользователя
   * @return возвращает строку с хешем пароля
   */
  private static String passwordHash(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
      return bytesToHex(hashedBytes);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error hashing password", e);
    }
  }

  /**
   * проходит по каждому элементу массива и конвертирует его в шестнадцатеричную форму
   *
   * @param bytes входной массив байтов
   * @return возвращает строку, представляющую хеш пароля в шестнадцатеричном виде
   */
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
