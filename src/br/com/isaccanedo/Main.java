package br.com.isaccanedo;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class Main {

    static String secret_message = "Isac Canedo de Almeida";
    static String encrypt_key = "26-ByteSharedKey";
    static byte key_to_bytes[] = encrypt_key.getBytes();
    static byte iv[] = new byte[16];
    public static void main(String [] args) {
        SecureRandom my_rand = new SecureRandom(); // Use o Java seguro PRNG
        my_rand.nextBytes(iv); // Gere um novo IV a cada vez!
        // (IV) - variável inicial - É uma entrada de tamanho fixo para
        // uma primitiva criptográfica que normalmente é requerida para ser aleatória
        // ou pseudoaleatório
        try {
            byte[] cipher = encrypt(secret_message, key_to_bytes);

            System.out.print("IV:\t\t\t");
            for (int i=0; i<iv.length; i++) System.out.print(new Integer(iv[i])+" ");

            System.out.print("\nTexto cifrado:\t\t");
            for (int i=0; i<cipher.length; i++) System.out.print(new Integer(cipher[i])+" ");

            String decrypted = decrypt(cipher, key_to_bytes);
            System.out.println("\nTexto descriptografado:\t" + decrypted);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(String plainText, byte[] enc_key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(enc_key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(iv));
        return cipher.doFinal(plainText.getBytes());
    }

    public static String decrypt(byte[] cipherText, byte[] enk_key) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(enk_key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(iv));
        return new String(cipher.doFinal(cipherText));
    }
}