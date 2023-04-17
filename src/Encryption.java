public class Encryption {

    public static String encryptStrCesar(String line, int shift) {
        StringBuilder ciphertext = new StringBuilder();
        for (char c : line.toCharArray()) {
            ciphertext.append((char) (c + shift));
        }
        return ciphertext.toString();
    }

    public static String decryptStrCesar(String ciphertext, int shift) {
        return encryptStrCesar(ciphertext, -shift);
    }
}
