package com.example.knowledge_android.comparator.sign.codec;

public interface BinaryEncoder extends Encoder {

    /**
     * Encodes a byte array and return the encoded data as a byte array.
     * 
     * @param pArray
     *            Data to be encoded
     *
     * @return A byte array containing the encoded data
     * 
     * @throws EncoderException
     *             thrown if the Encoder encounters a failure condition during
     *             the encoding process.
     */
    byte[] encode(byte[] pArray) throws EncoderException;
}
