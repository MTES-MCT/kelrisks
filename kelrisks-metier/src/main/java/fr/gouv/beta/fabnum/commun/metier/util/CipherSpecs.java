package fr.gouv.beta.fabnum.commun.metier.util;

public class CipherSpecs {
    
    public byte[] IV   = null;
    public byte[] salt = new byte[16]; // (16 == KEY_LENGTH / 8)
}
