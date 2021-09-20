package com.example.knowledge_android.comparator.release;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {

    private static List<ResourceBundle> resources;

    public static void setLocale(Locale locale) {
        Locale.setDefault(locale);
    }

    public static void setLocale(Locale locale, List<String> resourceFiles) {
        Locale.setDefault(locale);
        resources = new ArrayList(resourceFiles.size());
        for (String resourceFile : resourceFiles) {
            resources.add(ResourceBundle.getBundle(resourceFile, locale));
        }
    }

    public static String getString(String key) {
        for (ResourceBundle resource : resources) {
            if (resource.containsKey(key)) {
                return resource.getString(key);
            }
        }
        return "";
    }

    public static String getString(String key, Object... args) {
        return MessageFormat.format(getString(key), args);
    }
}
