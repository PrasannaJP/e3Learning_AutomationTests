package com.webuiframework.oua.uitests.utils;

import java.util.*;

import com.webuiframework.oua.uitests.utils.linqInterfaces.ActionT;
import com.webuiframework.oua.uitests.utils.linqInterfaces.FuncTT;

import static java.util.Arrays.asList;

public class LinqUtils {
    public static <T, T1> Collection<T1> select(Iterable<T> list, FuncTT<T, T1> func) {
        try {
            List<T1> result = new ArrayList<>();
            for (T el : list)
                result.add(func.invoke(el));
            return result;
        } catch (Exception ignore) { return null; }
    }
    public static <T, T1> Collection<T1> select(T[] list, FuncTT<T, T1> func){
        return select(asList(list), func);
    }
    public static <T, T1, T2> Collection<T2> selectMap(Map<T, T1> map, FuncTT<Map.Entry<T, T1>, T2> func) {
        try {
            List<T2> result = new ArrayList<>();
            for(Map.Entry<T, T1> el : map.entrySet())
                result.add(func.invoke(el));
            return result;
        } catch (Exception ignore) { return null; }
    }
    public static <T, T1, T2> Map<T, T2> select(Map<T, T1> map, FuncTT<T1, T2> func) {
        try {
            Map<T, T2> result = new HashMap<>();
            for(Map.Entry<T, T1> el : map.entrySet())
                result.put(el.getKey(), func.invoke(el.getValue()));
            return result;
        } catch (Exception ignore) { return null; }
    }

    public static <T> Collection<T> where(Iterable<T> list, FuncTT<T, Boolean> func) {
        try {
            List<T> result = new ArrayList<>();
            for(T el : list)
                if (func.invoke(el))
                result.add(el);
            return result;
        } catch (Exception ignore) { return null; }
    }
    public static <T> Collection<T> where(T[] list, FuncTT<T, Boolean> func) {
        return where(asList(list), func);
    }
    public static <T, T1> Map<T, T1> where(Map<T, T1> map, FuncTT<Map.Entry<T, T1>, Boolean> func) {
        try {
            Map<T, T1> result = new HashMap<>();
            for(Map.Entry<T, T1> el : map.entrySet())
                if (func.invoke(el))
                    result.put(el.getKey(), el.getValue());
            return result;
        } catch (Exception ignore) { return null; }
    }

    public static <T> void foreach(Iterable<T> list, ActionT<T> action) {
        try {
            for(T el : list)
                action.invoke(el);
        } catch (Exception ignore) { }
    }
    public static <T> void foreach(T[] list, ActionT<T> action) {
        foreach(asList(list), action);
    }
    public static <T, T1> void foreach(Map<T, T1> map, ActionT<Map.Entry<T, T1>> action) {
        try {
            for (Map.Entry<T, T1> entry : map.entrySet())
                action.invoke(entry);
        } catch (Exception ignore) { }
    }
    public static <T> T first(Iterable<T> list) {
        for(T el : list)
            return el;
        return null;
    }
    public static <T> T first(T[] list) {
        return first(asList(list));
    }
    public static <T, T1> T1 first(Map<T, T1> map) {
        for (Map.Entry<T, T1> el : map.entrySet())
            return el.getValue();
        return null;
    }
    public static <T> T first(Iterable<T> list, FuncTT<T, Boolean> func) {
        try {
            for(T el : list)
                if (func.invoke(el))
                    return el;
        } catch (Exception ignore) { }
        return null;
    }
    public static <T> int firstIndex(List<T> list, FuncTT<T, Boolean> func)  {
        try {
            for(int i = 0; i< list.size(); i++)
                if (func.invoke(list.get(i)))
                    return i;
        } catch (Exception ignore) { }
        return -1;
    }
    public static <T> int firstIndex(T[] array, FuncTT<T, Boolean> func) {
        try {
            for(int i = 0; i< array.length; i++)
                if (func.invoke(array[i]))
                    return i;
        } catch (Exception ignore) { }
        return -1;
    }
    public static <T> T first(T[] list, FuncTT<T, Boolean> func) {
        return first(asList(list), func);
    }
    public static <T, T1> T1 first(Map<T, T1> map, FuncTT<T, Boolean> func) {
        try {
            for (Map.Entry<T, T1> el : map.entrySet())
                if (func.invoke(el.getKey()))
                    return el.getValue();
        } catch (Exception ignore) { }
        return null;
    }

    public static <T> T last(Iterable<T> list)
    {
        T result = null;
        for(T el : list)
            result = el;
        return result;
    }
    public static <T> T last(T[] list) {
        return last(asList(list));
    }
    public static <T> T last(Iterable<T> list, FuncTT<T, Boolean> func) {
        T result = null;
        try {
            for(T el : list)
                if (func.invoke(el))
                    result = el;
        } catch (Exception ignore) { }
        return result;
    }
    public static <T> T last(T[] list, FuncTT<T, Boolean> func) {
        return last(asList(list), func);
    }

}

