package com.thuyhn.test;


import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Util {
    public static Set<String> getFilesInPath(String path) {
        File files = Paths.get(path).toFile();
        Set<String> set = new HashSet<String>();
        if(files.exists() && files.isDirectory()) {
        	set = Arrays.asList(files.list()).parallelStream().sorted().collect(Collectors.toSet());
        }
        return set;
    }

    public static File[] getAllFilesInPath(String path) {
        File[] files = Paths.get(path).toFile().listFiles();
        return files;
    }

    public static Map<String, Object> getAllFilesInPath(File[] files) {
        Map<String, Object> map = new HashMap<>();
        if(files != null) {
            for(File f: files) {
                String name = f.getName();
                Set<String> set = Util.getFilesInPath(f.toString());
                map.put(name, set);
            }
        }
        return map;
    }
}
