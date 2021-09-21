package com.example.knowledge_android.widget.builder;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.MapUtils;
import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;
import com.example.knowledge_android.comparator.ThemeUtil;
import com.example.knowledge_android.knowledge.GUtil;
import com.example.knowledge_android.widget.view.Badge;
import com.example.knowledge_android.widget.view.Banner;
import com.example.knowledge_android.widget.view.ZButton;
import com.example.knowledge_android.widget.viewgroup.CardLayout;
import com.example.knowledge_android.widget.viewgroup.ElasticGridLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import groovy.lang.Closure;
import groovy.lang.MissingMethodException;

/**
 * @author Alex
 * @since 2021-09-22
 */
public class GViewBuilder {

    //java里Stack的peek方法是返回栈顶的元素但不移除它。
    //但Stack的pop方法是会移除的。

    /**
     * For recording view container for Closure.
     */
    private Stack<ViewGroup> parents = new Stack<>();

    private Stack<Runnable> closures = new Stack<>();

    public Context getContext() {
        return OneApplication.getInstance().getMainActivity();
    }

    /**
     * Set closure's delegate to me, and make container to be the current parent View.
     */
    private Runnable bind(Runnable closure, ViewGroup container) {
        parents.push(container);
        closures.push(closure);
        return closure;
    }

    /**
     * Trash the latest parent View.
     */
    private void unbind(Runnable closure) {
        parents.pop();
        closures.pop();
    }

    public static int stringToPixels(String value) {
        try {
            int pixels;
            if (value.endsWith("dp")) {
                String dip = value.replace("dp", "");
                Float dip1 = new Float(dip);
                pixels = GUtil.dip2px(dip1);
            } else if (value.endsWith("sp")) {
                String sp = value.replace("sp", "");
                Float sp1 = new Float(sp);
                pixels = GUtil.sp2px(sp1);
            } else if (value.endsWith("px")) {
                String sp = value.replace("px", "");
                pixels = Integer.parseInt(sp);
            } else {
                pixels = Integer.parseInt(value);
            }
            return pixels;
        } catch (Exception ignored) {
            return 0;
        }
    }


    /**
     * Add view to a parent container, which is now either an ElasticGridLayout, CardLayout, or LinearLayout.
     */
    private void addToParent(View view, Map params) {
        try {
            ViewGroup container = parents.peek();

            if (params != null) {
                Object layoutParams = params.get("layoutParams");
                if (layoutParams != null) {
                    ViewGroup.LayoutParams layoutParams1 = (ViewGroup.LayoutParams) layoutParams;
                    container.addView(view, layoutParams1);
                } else {
                    container.addView(view);
                }
            }
        } catch (EmptyStackException ignored) {
        }
    }



    <T extends View> T build(Class<T> viewClass, Map properties) throws InstantiationException, IllegalAccessException {
        T t = viewClass.newInstance();
        View view = t;
        addToParent(view, properties);
        return (T) view;
    }



    private Map<String, Object> computeCombinedProperties(String type, Map properties) {
        Map givenProperties = properties == null ? Collections.EMPTY_MAP : properties;
        Map<String, Object> stringObjectMap = viewDefaultPropertiesMap.get(type);
        Map defaultProperties = stringObjectMap == null ? Collections.EMPTY_MAP : stringObjectMap;

        Map map = new HashMap();
        map.putAll(defaultProperties);
        map.putAll(givenProperties);
        return map;
    }



    /**
     * Other widget builder.
     * <pre>
     * widget(inflater.inflate(R.layout.dish01_image_view, null),
     *     [row: 2, column: 2])
     * </pre>
     */
    View widget(View widget, Map params) {
        addToParent(widget, params);
        return widget;
    }

    /**
     * Other widget builder.
     * <pre>
     * widget(inflater.inflate(R.layout.dish01_image_view, null))
     * </pre>
     */
    View widget(View widget) {
        addToParent(widget, new HashMap());
        return widget;
    }

    /**
     * Other widget builder.
     * <pre>
     * widget(row: 2, column: 2,
     *     view: inflater.inflate(R.layout.dish01_image_view, null))
     * </pre>
     */
    View widget(Map params) {
        Object view1 = params.get("view");
        if (view1 != null) {
            View view = (View) view1;
            widget(view,params);
            return view;
        }
        return null;
    }


    //组件对应关系
    public static Map<String, Class<? extends View>> viewClassMap = new HashMap<>();
    static {
        viewClassMap.put("badge", Badge.class);
        viewClassMap.put("banner", Banner.class);
        viewClassMap.put("zButton", ZButton.class);
    }


    //组件默认属性配置
    public static Map<String,Map<String,Object>> viewDefaultPropertiesMap = new HashMap<>();
    static {
        Map map = new HashMap<>();
        map.put("textAppearance", R.style.TextAppearance_AppCompat);
        map.put("textColor", ThemeUtil.defaultTextColor);
        map.put("textSize", ThemeUtil.defaultTextSize);
        map.put("gravity",Gravity.LEFT | Gravity.CENTER_VERTICAL);
        viewDefaultPropertiesMap.put("text",map);

        map.clear();
        map.put("textAppearance", R.style.TextAppearance_AppCompat);
        map.put("textColor", ThemeUtil.defaultTextColor);
        map.put("textSize", ThemeUtil.defaultTextSize);
        map.put("gravity",Gravity.LEFT | Gravity.CENTER_VERTICAL);
        viewDefaultPropertiesMap.put("banner",map);
    }

    /** CardLayout builder method. */
    CardLayout cardLayout(Runnable subviewBuildingClosure) {
        CardLayout cardLayout = new CardLayout(getContext());

        bind(subviewBuildingClosure, cardLayout);
        subviewBuildingClosure.run();
        unbind(subviewBuildingClosure);
        return cardLayout;
    }

    /** CardLayout builder method. */
    @RequiresApi(api = Build.VERSION_CODES.N)
    CardLayout cardLayout(Map properties, Runnable subviewBuildingClosure) {
        Map<String,Object> givenProperties = null;
        if (MapUtils.isEmpty(properties)) {
            givenProperties = Collections.EMPTY_MAP;
        }

        CardLayout cardLayout = new CardLayout(getContext());
        setPropertiesAndAddToParent(cardLayout, givenProperties);

        bind (subviewBuildingClosure, cardLayout);
        subviewBuildingClosure.run();
        unbind(subviewBuildingClosure);
        return cardLayout;
    }


    /** ElasticGridLayout builder method. */
    @RequiresApi(api = Build.VERSION_CODES.N)
    ElasticGridLayout gridLayout(Map properties, Runnable subviewBuildingClosure) {
        Map<String,Object> givenProperties = null;
        if (MapUtils.isEmpty(properties)) {
            givenProperties = Collections.EMPTY_MAP;
        }
        ElasticGridLayout gridLayout = new ElasticGridLayout(getContext());
        setPropertiesAndAddToParent(gridLayout,givenProperties);
        bind(subviewBuildingClosure,gridLayout);
        subviewBuildingClosure.run();
        unbind(subviewBuildingClosure);
        return gridLayout;
    }


    /** Set properties for view from a Map object, and then add it to its parent view. */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private View setPropertiesAndAddToParent(View view, Map<String, Object> properties) {
        properties.forEach((name,value)-> {
            List<String> xx = new ArrayList<>();
            xx.add("row");
            xx.add("column");
            xx.add("rowSpan");
            xx.add("colSpan");
            if (xx.contains(name)) {
                return;
            }
            if (name.equals("padding")) {
                if (value instanceof String) {
                    int paddingPixels = stringToPixels((String) value);
                    view.setPadding(paddingPixels, paddingPixels, paddingPixels, paddingPixels);
                } else if (value instanceof List) {
                    List list = (List) value;
                    int paddingLeft = stringToPixels((String) list.get(0));
                    int paddingTop = stringToPixels((String) list.get(1));
                    int paddingRight = stringToPixels((String) list.get(2));
                    int paddingBottom = stringToPixels((String) list.get(3));
                    view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                }
            } else if (name == "minimumHeight" && value instanceof String) {
                int minimumHeightPixels = stringToPixels((String) value);
                view.setMinimumHeight(minimumHeightPixels);
            }
        });
        addToParent(view, properties);
        return view;
    }

    
}
