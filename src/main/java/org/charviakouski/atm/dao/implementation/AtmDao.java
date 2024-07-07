package org.charviakouski.atm.dao.implementation;

import org.charviakouski.atm.entity.Atm;
import org.charviakouski.atm.util.PathUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AtmDao extends AbstractDao<Atm> {

    private static final String ATM_DATA_FILE = "./src/main/resources/data/atm.txt";

    @Override
    String getDataFile() {
        return ATM_DATA_FILE;
    }

    @Override
    public void save(Atm atm) throws IOException {
        if (PathUtil.checkFilePath(ATM_DATA_FILE)) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ATM_DATA_FILE))) {
                writer.write(atm.toString());
            } catch (IOException e) {
                throw new IOException("The problem with writing the file", e);
            }
        } else {
            throw new IOException("File not found");
        }
    }
}
