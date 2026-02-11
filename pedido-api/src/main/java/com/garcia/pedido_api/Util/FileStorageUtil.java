package com.garcia.pedido_api.Util;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileStorageUtil {

    private static final String UPLOAD_DIR = "cargar/productos/";

    public static String saveImage(MultipartFile file) throws IOException {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File destination = new File(dir, fileName);

        file.transferTo(destination);

        return UPLOAD_DIR + fileName; // lo que se guarda en BD
    }
    
}
