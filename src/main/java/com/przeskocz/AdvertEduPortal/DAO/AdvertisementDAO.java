package com.przeskocz.AdvertEduPortal.DAO;

import com.przeskocz.AdvertEduPortal.model.Advertisement;
import com.przeskocz.AdvertEduPortal.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementDAO extends CrudRepository<Advertisement, Long> {
    Optional<List<Advertisement>> findTop6ByExpirationDateGreaterThanEqualOrderByStartDateDesc(LocalDateTime date);
    List<Advertisement> findAllByCategory_IdAndTitleIgnoreCaseContainingAndExpirationDateGreaterThanEqualOrderByStartDateDesc(Long categoryId, String title, LocalDateTime date);
    List<Advertisement> findAllByCity_NameIgnoreCaseContaining(String name);

/*    @Query("SELECT p FROM Advertisement p WHERE upper(p.university.name) LIKE %?1%" +
            " OR upper(p.university.shortName) LIKE %?1% AND p.expirationDate >= ?2 ORDER BY p.startDate Desc")*/
    List<Advertisement> findAllByUniversity_NameIgnoreCaseContainingOrUniversity_ShortNameIgnoreCaseContainingAndExpirationDateGreaterThanEqualOrderByStartDateDesc(String name, String shortName, LocalDateTime date);
    List<Advertisement> findAllByTitleIgnoreCaseContainingAndExpirationDateGreaterThanEqualOrderByStartDateDesc(String title, LocalDateTime date);

    List<Advertisement> findAllByUser(User user);
}
