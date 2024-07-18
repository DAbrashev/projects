package com.paintingscollectors.service;

import com.paintingscollectors.model.dto.AddPaintingDto;
import com.paintingscollectors.model.dto.UserPaintingsInfoDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.StyleRepository;
import com.paintingscollectors.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaintingService {

    private final ModelMapper modelMapper;
    private final PaintingRepository paintingRepository;

    private final StyleRepository styleRepository;

    private final UserRepository userRepository;



    private final CurrentUser currentUser;

    public PaintingService(ModelMapper modelMapper, PaintingRepository paintingRepository, StyleRepository styleRepository, UserRepository userRepository, CurrentUser currentUser) {
        this.modelMapper = modelMapper;
        this.paintingRepository = paintingRepository;
        this.styleRepository = styleRepository;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }

    public void addPainting(AddPaintingDto addPaintingDto) {

        Painting map = modelMapper.map(addPaintingDto, Painting.class);
        map.setOwner(userRepository.getById(currentUser.getUser().getId()));
        map.setStyle(styleRepository.getByName(StyleName.valueOf(addPaintingDto.getStyle())));

        paintingRepository.save(map);

    }

    @Transactional
    public void addToFavourite(Long paintingId) {
        User user = userRepository.getById(currentUser.getUser().getId());
        Painting painting = paintingRepository.getById(paintingId);
        if (!user.getFavoritePaintings().contains(painting)){
            user.getFavoritePaintings().add(painting);
            painting.setFavourite(true);
        }
    }
    @Transactional
    public List<UserPaintingsInfoDTO> getOtherPaintings(long id) {
        return paintingRepository.findAll()
                .stream()
                .filter(p -> p.getOwner().getId() != id)
                .map(p -> modelMapper.map(p,UserPaintingsInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void vote(Long paintingId) {
        User user = userRepository.getById(currentUser.getUser().getId());
        Painting painting = paintingRepository.getById(paintingId);
        if (!user.getRatedPaintings().contains(painting)){
            user.getRatedPaintings().add(painting);
            painting.setVotes(painting.getVotes() + 1);
        }
    }
    @Transactional
    public void remove(Long paintingId) {
        Painting painting = paintingRepository.getById(paintingId);
        painting.getToFavourite()
                        .forEach(user -> user.getFavoritePaintings().remove(painting));
        paintingRepository.save(painting);
        paintingRepository.deleteById(paintingId);
    }
    @Transactional
    public void removeFromFavorites(Long paintingId) {
        User user = userRepository.getById(currentUser.getUser().getId());
        Painting painting = paintingRepository.getById(paintingId);
        user.getFavoritePaintings().remove(painting);
        painting.setFavourite(painting.getToFavourite().size() - 1 > 0);
    }
    @Transactional
    public List<UserPaintingsInfoDTO> getTwoMostRatedPaintings() {
        return paintingRepository.findAllByOrderByVotesDesc()
                .stream()
                .limit(2)
                .map(p -> modelMapper.map(p, UserPaintingsInfoDTO.class))
                .collect(Collectors.toList());
    }
}
