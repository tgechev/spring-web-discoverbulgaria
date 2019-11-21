package com.gechev.discoverbulgaria.util.impl;

import com.gechev.discoverbulgaria.util.FileUtil;

import java.io.*;

public class FileUtilImpl implements FileUtil {
    @Override
    public String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();

        File file = new File(path);
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        String line;

        while ((line = bf.readLine()) != null){
            sb.append(line).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
