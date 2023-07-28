package com.epam.mjc.nio;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class FileReader {

    public Profile getDataFromFile(File file) {
        Profile profile = null;
        String name = null;
        Integer age = null;
        String email = null;
        Long phone = null;
        StringBuilder stringBuilder = new StringBuilder();
        Path path = file.toPath();
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer) != -1) {
                buffer.flip();
                stringBuilder.append(StandardCharsets.UTF_8.decode(buffer));
                buffer.clear();
            }
            String[] splitText = stringBuilder.toString().split("\n");
            for (int i = 0; i < splitText.length; i++) {
                if (splitText[i].contains("Name")) {
                    name = splitText[i].replace("Name: ", "");
                } else if (splitText[i].contains("Age")) {
                    age = Integer.valueOf(splitText[i].replace("Age: ", ""));
                } else if (splitText[i].contains("Email")) {
                    email = splitText[i].replace("Email: ", "");
                } else if (splitText[i].contains("Phone")) {
                    phone = Long.valueOf(splitText[i].replace("Phone: ", ""));
                }
            }
            profile = new Profile(name, age, email, phone);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return profile;
    }
}
