package com.example.knowledge_android.comparator.sign.codec;

public interface Encoder {

    /**
     * Encodes an "Object" and returns the encoded content as an Object. The
     * Objects here may just be <code>byte[]</code> or <code>String</code>s
     * depending on the implementation used.
     * 
     * @param pObject
     *            An object ot encode
     * 
     * @return An "encoded" Object
     * 
     * @throws EncoderException
     *             an encoder exception is thrown if the encoder experiences a
     *             failure condition during the encoding process.
     */
    Object encode(Object pObject) throws EncoderException;
}
