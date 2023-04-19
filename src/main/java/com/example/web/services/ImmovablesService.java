package com.example.web.services;

import com.example.web.models.Details;
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
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImmovablesService {
    private final ImmovablesRepository immovablesRepository;
    private final UserRepository userRepository;

    public List<Immovables> listImmovables(String title){
        if( title != null ) return immovablesRepository.findByTitleContaining(title);
        return immovablesRepository.findAll();
    }

    public List<Immovables> listImmovablesCost(String title, Integer min, Integer max){
        log.info(String.valueOf(min));
        log.info(String.valueOf(max));
        if( max != null &&  min != null ) return immovablesRepository.findByTitleContainingAndPriceBetween(title,min,max);
        if( min != null ) return immovablesRepository.findByTitleContainingAndPriceAfter(title,min);
        if( max != null ) return immovablesRepository.findByTitleContainingAndPriceBefore(title,max);
        if( title != null ) return immovablesRepository.findByTitleContaining(title);
        return immovablesRepository.findAll();
    }

    public void saveImmovables(
            Principal principal,
            Details details,
            Immovables immovables,
            MultipartFile[] files) throws IOException {
        immovables.setUser(getUserByPrincipal(principal));

        if (files != null) {
            for (MultipartFile file : files) {
                if (file.getSize() != 0) {
                    Image image = toImageEntity(file);
                    immovables.addImageToImmovables(image);
                }
            }
        }
        Immovables immovablesFromDb = immovablesRepository.save(immovables);
        immovablesFromDb.setPreviewImageId(immovablesFromDb.getImages().get(0).getId());
        details.setImmovables(immovablesFromDb);
        immovablesFromDb.setDetails(details);
        immovablesRepository.save(immovablesFromDb);
    }


    public void editImmovables(Long id,
            Principal principal,
            Details detailsNow,
            Immovables immovablesNow,
            MultipartFile[] files) throws IOException {
        Immovables immovables = immovablesRepository.findById(id).orElseThrow();
        if (immovables.getUser() == getUserByPrincipal(principal)){

            immovables.setTitle(immovablesNow.getTitle());
            immovables.setCity(immovablesNow.getCity());
            immovables.setStreet(immovablesNow.getStreet());
            immovables.setAddress(immovablesNow.getAddress());
            immovables.setPrice(immovablesNow.getPrice());
            immovables.setDescription(immovablesNow.getDescription());

            immovables.getDetails().setRealtySupType(detailsNow.getRealtySupType());
            immovables.getDetails().setRooms(detailsNow.getRooms());
            immovables.getDetails().setFloor(detailsNow.getFloor());
            immovables.getDetails().setSquare(detailsNow.getSquare());
            immovables.getDetails().setLivingSpace(detailsNow.getLivingSpace());
            immovables.getDetails().setKitchenSpace(detailsNow.getKitchenSpace());
            immovables.getDetails().setBalcony(detailsNow.getBalcony());
            immovables.getDetails().setBathroom(detailsNow.getBathroom());


            if (files != null) {
                for (MultipartFile file : files) {
                    if (file.getSize() != 0) {
                        Image image = toImageEntity(file);
                        immovables.addImageToImmovables(image);
                    }
                }
            }
            Immovables immovablesFromDb = immovablesRepository.save(immovables);
            immovablesFromDb.setPreviewImageId(immovablesFromDb.getImages().get(0).getId());
            immovablesRepository.save(immovablesFromDb);
        }
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

    public void purchaseImmovables(long id, Principal principal) {
        Immovables immovables = immovablesRepository.findById(id).orElseThrow();
        immovables.setUser(getUserByPrincipal(principal));
        immovablesRepository.save(immovables);
    }
}
