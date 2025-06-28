import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Random;


public class Main {

    private static final String DES_ALGO = "DES";
    private static final String AES_ALGO = "AES";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose algorithm (DES/AES): ");
        String algorithm = sc.nextLine().toUpperCase();

        if (!algorithm.equals(DES_ALGO) && !algorithm.equals(AES_ALGO)) {
            System.out.println("Invalid algorithm. Choose either DES or AES.");
            return;
        }

        SecretKey key = generateKey(algorithm);

        while (true) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Program Workflow: ");
            System.out.println("1. Whole Program: ");
            System.out.println("2. Encrypt: ");
            System.out.println("3. Decrypt: ");
            System.out.println("4. Attack: ");
            System.out.println("5. Avalanche Effect: ");
            System.out.println("0. Exit");
            System.out.println("Enter Choice: ");
            int choice = sc.nextInt();

            if (choice == 1) {
                // Step 1: Encrypt the plaintext
                String plaintext = new String(Files.readAllBytes(Paths.get("plaintext.txt")));
                byte[] encryptedData = encrypt(plaintext, key, algorithm);
                Files.write(Paths.get("encrypt.txt"), encryptedData);
                System.out.println("Encryption complete. Ciphertext stored in encrypt.txt.");

                // Step 2: Decrypt the ciphertext
                byte[] encryptedFromFile = Files.readAllBytes(Paths.get("encrypt.txt"));
                String decryptedData = decrypt(encryptedFromFile, key, algorithm);
                Files.write(Paths.get("decrypt.txt"), decryptedData.getBytes());
                System.out.println("Decryption complete. Check decrypt.txt for the result.");

                // Step 3: Attack Generator
                attackGenerator(key, algorithm);

                // Step 4: Avalanche Effect
                demonstrateAvalancheEffect(algorithm);
            } else if (choice == 2) {
                // Step 1: Encrypt the plaintext
                String plaintext = new String(Files.readAllBytes(Paths.get("plaintext.txt")));
                byte[] encryptedData = encrypt(plaintext, key, algorithm);
                Files.write(Paths.get("encrypt.txt"), encryptedData);
                System.out.println("Encryption complete. Ciphertext stored in encrypt.txt.");
            } else if (choice == 3) {
                // Step 1: Decrypt the ciphertext
                byte[] encryptedFromFile = Files.readAllBytes(Paths.get("encrypt.txt"));
                String decryptedData = decrypt(encryptedFromFile, key, algorithm);
                Files.write(Paths.get("decrypt.txt"), decryptedData.getBytes());
                System.out.println("Decryption complete. Check decrypt.txt for the result.");
            } else if (choice == 4) {
                // Step 1: Encrypt the plaintext
                String plaintext = new String(Files.readAllBytes(Paths.get("plaintext.txt")));
                byte[] encryptedData = encrypt(plaintext, key, algorithm);
                Files.write(Paths.get("encrypt.txt"), encryptedData);
                System.out.println("Encryption complete. Ciphertext stored in encrypt.txt.");
                // Step 2: Attack Generator
                attackGenerator(key, algorithm);
            } else if (choice == 5) {
                demonstrateAvalancheEffect(algorithm);
            } else if (choice == 0) {
                System.exit(0);
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // Function to generate encryption key
    public static SecretKey generateKey(String algorithm) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        keyGen.init(algorithm.equals(AES_ALGO) ? 128 : 56); // AES uses 128-bit, DES uses 56-bit
        return keyGen.generateKey();
    }

    // Encrypt function
    public static byte[] encrypt(String data, SecretKey key, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes());
    }

    // Decrypt function
    public static String decrypt(byte[] data, SecretKey key, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(data);
        return new String(decryptedData);
    }

    // Attack Generator to corrupt the encrypted file
    public static void attackGenerator(SecretKey key, String algorithm) throws Exception {
        // Step 1: Corrupt the encrypted file
        byte[] encryptedData = Files.readAllBytes(Paths.get("encrypt.txt"));
        Random rand = new Random();
        encryptedData[rand.nextInt(encryptedData.length)] ^= (byte) (rand.nextInt(256));  // Corrupt random byte

        // Save corrupted encryption in "encrypt_Attack.txt"
        Path corruptEncryptPath = Paths.get("encrypt_Attack.txt");
        Files.write(corruptEncryptPath, encryptedData);
        System.out.println("Attack Generator: Corrupted the encrypt.txt file and saved as encrypt_Attack.txt.");

        // Step 2: Decrypt the corrupted file
        try {
            String decryptedCorruptedData = decrypt(encryptedData, key, algorithm);
            // Save the result of decrypting corrupted encryption in "decrypt_Attack.txt"
            Files.write(Paths.get("decrypt_Attack.txt"), decryptedCorruptedData.getBytes());
            System.out.println("Decryption of attacked ciphertext complete. Check decrypt_Attack.txt for the result.");
        } catch (Exception e) {
            System.out.println("Error during decryption of corrupted data: " + e.getMessage());
            Files.write(Paths.get("decrypt_Attack.txt"), "Decryption Failed".getBytes());
        }
    }

    // Demonstrate Avalanche Effect with file output
    public static void demonstrateAvalancheEffect(String algorithm) throws Exception {
        SecretKey key = generateKey(algorithm);
        String plaintext = new String(Files.readAllBytes(Paths.get("plaintext.txt")));

        // Encrypt original plaintext
        byte[] encryptedData1 = encrypt(plaintext, key, algorithm);

        // Change a single bit in the plaintext
        char[] charArray = plaintext.toCharArray();
        charArray[0] ^= 1; // Toggle one bit in the first character
        String modifiedPlaintext = new String(charArray);

        // Encrypt modified plaintext
        byte[] encryptedData2 = encrypt(modifiedPlaintext, key, algorithm);

        // Write both encryptions to files for comparison
        Files.write(Paths.get("ce.txt"), encryptedData1);

        // Decrypt both ciphertexts to see changes
        String decryptedData1 = decrypt(encryptedData1, key, algorithm);
        String decryptedData2 = decrypt(encryptedData2, key, algorithm);

        // Write decrypted data to files for comparison
        Files.write(Paths.get("cd.txt"), decryptedData1.getBytes());

        // Calculate bit differences in ciphertexts
        int differences = 0;
        for (int i = 0; i < encryptedData1.length; i++) {
            differences += Integer.bitCount(encryptedData1[i] ^ encryptedData2[i]);
        }

        // Display the avalanche effect result
        System.out.println("Avalanche Effect: Number of bit differences in ciphertext: " + differences);
    }
}
