package org.charviakouski.atm.dao.implementation;

import org.charviakouski.atm.dao.BaseDao;
import org.charviakouski.atm.util.PathUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> implements BaseDao<T> {

    abstract String getDataFile();

    @Override
    public List<String> read() throws IOException {
        List<String> objectList = new ArrayList<>();
        String dataFile = getDataFile();
        if (PathUtil.checkFilePath(dataFile)) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(dataFile))) {
                while (reader.ready()) {
                    objectList.add(reader.readLine());
                }
            } catch (IOException e) {
                throw new IOException("The problem with reading the file", e);
            }
        } else {
            throw new IOException("File not found");
        }
        return objectList;
    }
}
