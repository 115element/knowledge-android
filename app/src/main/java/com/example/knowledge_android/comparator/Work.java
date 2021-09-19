package com.example.knowledge_android.comparator;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: David
 * Date: Aug 17, 2010
 * Time: 12:17:16 AM
 * To change this template use File | Settings | File Templates.
 */

public interface Work extends Runnable {
    
    public void run();

    public Map result();

}

