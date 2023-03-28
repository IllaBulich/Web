package com.example.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Immovables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title, city, street, address;
    @Column(columnDefinition = "text")
    private String description;
    private int price;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    mappedBy = "immovables")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDateTime dateOfCreated;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @OneToOne(mappedBy = "immovables", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Details details;


    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToImmovables(Image image){
        image.setImmovables(this);
        images.add(image);
    }

    @Override
    public String toString() {
        return "Immovables{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", images=" + images +
                ", previewImageId=" + previewImageId +
                ", dateOfCreated=" + dateOfCreated +
                ", user=" + user.getEmail()+
                ", details=" + details +
                '}';
    }
}
