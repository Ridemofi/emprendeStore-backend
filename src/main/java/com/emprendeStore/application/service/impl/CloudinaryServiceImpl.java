package com.emprendeStore.application.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    private String uploadToFolder(MultipartFile file, String folder) {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", folder)
            );
            Object url = uploadResult.get("secure_url");
            if (url == null) {
                throw new ErrorNegocio("Cloudinary no devolvió una URL segura para la imagen subida");
            }
            return url.toString();
        } catch (IOException e) {
            throw new ErrorNegocio("Error al subir imagen a Cloudinary");
        }
    }

    @Override
    public String uploadImage(MultipartFile file) {
        // Antes: "Home/EmprendeStore"
        // Ahora: Directamente la carpeta del proyecto
        return uploadToFolder(file, "EmprendeStore");
    }

    @Override
    public String uploadProductoImage(MultipartFile file) {
        return uploadToFolder(file, "EmprendeStore/Productos");
    }

    @Override
    public String uploadClienteImage(MultipartFile file) {
        return uploadToFolder(file, "EmprendeStore/Clientes");
    }

    @Override
    public String uploadEmprendedorImage(MultipartFile file) {
        return uploadToFolder(file, "EmprendeStore/Emprendedores");
    }

    @Override
    public void deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }
        try {
            // Extraer el public_id de la URL
            // Ejemplo URL: https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg
            // public_id: sample
            String publicId = extractPublicId(imageUrl);
            if (publicId != null) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
        } catch (IOException e) {
            // Loguear el error pero no detener el flujo si falla el borrado
            System.err.println("Error al eliminar imagen de Cloudinary: " + e.getMessage());
        }
    }

    private String extractPublicId(String imageUrl) {
        try {
            // 1. Validar input
            if (imageUrl == null || imageUrl.isEmpty()) return null;

            // 2. Dividir por "/upload/" que es el marcador estándar de Cloudinary
            String[] parts = imageUrl.split("/upload/");

            if (parts.length > 1) {
                String pathWithVersion = parts[1];

                // 3. Decodificar URL (Vital para espacios: %20 -> " ")
                pathWithVersion = URLDecoder.decode(pathWithVersion, StandardCharsets.UTF_8.toString());

                // 4. Quitar versión (ej: v17354823/)
                // El regex busca 'v' seguido de números y una barra al inicio
                String path = pathWithVersion.replaceFirst("^v\\d+/", "");

                // 5. Quitar extensión (ej: .jpg, .png)
                int lastDot = path.lastIndexOf(".");
                if (lastDot > 0) {
                    return path.substring(0, lastDot);
                }
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace(); // O usar un logger
            return null;
        }
        return null;
    }
}
