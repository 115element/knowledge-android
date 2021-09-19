/**
 * @author Richard Kong
 * @version 0 alpha
 */
package com.example.knowledge_android.comparator;

import java.util.ArrayList;
import java.util.Map;


public class BeanUtils
{
    public static Object getValue(Object o, String path)
    {
        String[] paths = splitPath(path);
        return getValueByPaths(o,paths);
    }

    public static void setValue(Object o, String path, Object value)
    {
        String[] paths = splitPath(path);
        setValueByPaths(o,paths,value);
    }

    private static Object getValueByPaths(Object o, String[] paths)
    {
        Object subO = o;
        while(paths.length > 0)
        {
            subO = getValueByName(subO,paths[0]);
            String[] subPath = new String[paths.length-1];
            System.arraycopy(paths,1,subPath,0,subPath.length);
            paths = subPath;
        }
        return subO;
    }
    private static Object getValueByName(Object o, String path)
    {
        Object subO = null;
        if(o instanceof Map)
        {
            subO = ((Map) o).get(path);
        }
        else
        {
            subO = BeanUtils.getValue(o,path);
        }
        return subO;
    }

    private static void setValueByPaths(Object o, String[] paths, Object value)
    {
        Object subO = o;
        while(paths.length > 1)
        {
            subO = getValueByName(subO,paths[0]);
            String[] subPath = new String[paths.length-1];
            System.arraycopy(paths,1,subPath,0,subPath.length);
            paths = subPath;
        }

        setValueByName(subO,paths[0],value);
    }
    private static void setValueByName(Object o, String path, Object value)
    {
        Object subO = null;
        if(o instanceof Map)
        {
            ((Map) o).put(path,value);
        }
        else
        {
            //subO = com.opensymphony.util.BeanUtils.setValue(o,path,value); TODO
        }
    }

    private static String[] splitPath(String str)
    {
        ArrayList<String> list = new ArrayList<String>();
        int index = 0;
        int foregoingIndex = 0;
        while (true)
        {
            foregoingIndex = index;
            index = str.indexOf('.', index);
            if (index < 0)
            {
                int sp = foregoingIndex - 1;
                list.add(str.substring(sp >= 0 ? sp + 1 : 0, str.length()));
                break;
            }
            int sp = foregoingIndex - 1;
            String subStr = str.substring(sp >= 0 ? sp + 1 : 0, index);
            if (subStr.length() > 0)
            {
                list.add(subStr);
            }
            index++;
        }

        String[] paths = new String[list.size()];
        paths = list.toArray(paths);
        return paths;
    }
}
