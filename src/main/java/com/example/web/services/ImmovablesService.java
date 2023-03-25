package com.example.web.services;

import com.example.web.models.Image;
import com.example.web.models.Immovables;
import com.example.web.models.User;
import com.example.web.repo.ImmovablesRepository;
import com.example.web.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImmovablesService {
    private final ImmovablesRepository immovablesRepository;
    private final UserRepository userRepository;

    public List<Immovables> listImmovables(String title){
        if( title != null ) return immovablesRepository.findByTitle(title);
        return immovablesRepository.findAll();
    }

    public void saveImmovables(
            Principal principal,
            Immovables immovables,
            MultipartFile file1,
            MultipartFile file2,
            MultipartFile file3) throws IOException {
        immovables.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0){
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            immovables.addImageToImmovables(image1);
        }
        if (file2.getSize() != 0){
            image2 = toImageEntity(file2);
            immovables.addImageToImmovables(image2);
        }
        if (file3.getSize() != 0){
            image3 = toImageEntity(file3);
            immovables.addImageToImmovables(image3);
        }
        log.info("Saving new {}", immovables);
        Immovables immovablesFromDb = immovablesRepository.save(immovables);
        immovablesFromDb.setPreviewImageId(immovablesFromDb.getImages().get(0).getId());
        immovablesRepository.save(immovables);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return  new User();
        return userRepository.findByEmail(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;

    }

    public void deleteImmovables(Long id) {
        immovablesRepository.deleteById(id);
    }

    public Immovables getImmovablesById(Long id){
        return immovablesRepository.findById(id).orElse(null);
    }

}
