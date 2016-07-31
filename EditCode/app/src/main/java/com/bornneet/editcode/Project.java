package com.bornneet.editcode;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by tnantoka on 7/31/16.
 */
public class Project {
    static File parentDir;

    String name;

    String html;
    String css;
    String js;

    private static final String HTML_FILE = "index.html";
    private static final String CSS_FILE = "style.css";
    private static final String JS_FILE = "javascript.js";

    public Project(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void create() {
        dir().mkdir();
        html = "html";
        css = "css";
        js = "js";
        save();
    }

    public void load() {
        html = loadFile(HTML_FILE);
        css = loadFile(CSS_FILE);
        js = loadFile(JS_FILE);
    }

    public void save() {
        saveFile(HTML_FILE, html);
        saveFile(CSS_FILE, css);
        saveFile(JS_FILE, js);
    }

    public void destroy() {
        destroyFile(HTML_FILE);
        destroyFile(CSS_FILE);
        destroyFile(JS_FILE);
        dir().delete();
    }

    private void destroyFile(String name) {
        File file = new File(dir(), name);
        file.delete();
    }

    private String loadFile(String name) {
        try {
            File file = new File(dir(), name);
            FileInputStream stream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void saveFile(String name, String content) {
        try {
            File file = new File(dir(), name);
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File dir() {
        return new File(parentDir, name);
    }
}
