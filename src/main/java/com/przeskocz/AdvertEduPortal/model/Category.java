package com.przeskocz.AdvertEduPortal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"advertisements"})
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Advertisement> advertisements;

    public Category(String name) {
        this(-1L, name);
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public enum CategoryEnum {
        DEFAULT(1L), SPRZEDAM(2L), KUPIE(3L), ODDAM(4L), WYKONAM(5L),
        ZLECE(6L), KOREPETYCJE(7L), MIASTO(8L), UCZELNIA(9L);

        private final Long value;

        CategoryEnum(Long value) {
            this.value = value;
        }

        public Long getValue() {
            return this.value;
        }

        public CategoryEnum getEnum(Long value) {
            for (CategoryEnum tmp : CategoryEnum.values()) {
                if (tmp.getValue().equals(value))
                    return tmp;
            }
            return DEFAULT;
        }
    }
}
