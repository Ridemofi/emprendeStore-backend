package com.emprendeStore.application.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    /**
     * Sube una imagen genérica a Cloudinary (carpeta Home-EmprendeStore) y devuelve la URL segura.
     */
    String uploadImage(MultipartFile file);

    String uploadProductoImage(MultipartFile file);
    String uploadClienteImage(MultipartFile file);
    String uploadEmprendedorImage(MultipartFile file);

    /**
     * Elimina una imagen de Cloudinary dada su URL.
     */
    void deleteImage(String imageUrl);
}
