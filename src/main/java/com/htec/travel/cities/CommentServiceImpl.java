package com.htec.travel.cities;

import com.htec.travel.users.User;
import com.htec.travel.users.UserNotExistException;
import com.htec.travel.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDate;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConverterUtil converterUtil;

    @Override
    @Transactional
    public CommentDto comment(BigInteger cityId, String description, String username) {
        var user = loadUser(username);
        var city = cityRepository.findById(cityId);
        var comment = Comment.builder()
                .description(description)
                .modified(LocalDate.now())
                .created(LocalDate.now())
                .poster(user)
                .city(city.get())
                .build();
        comment = commentRepository.save(comment);
        city.get().getComments().add(comment);
        cityRepository.save(city.get());
        return converterUtil.convertCommentEntity(
                commentRepository.save(comment));
    }

    private User loadUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotExistException());
    }

    @Override
    public CommentDto updateComment(BigInteger id, String description, String username) {
        var comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotExistException());
        if (!comment.getPoster().getUsername().equals(username)) {
            throw new UnauthorizedCommentActionException();
        }
        comment.setDescription(description);
        comment.setModified(LocalDate.now());
        return converterUtil.convertCommentEntity(
                commentRepository.save(comment));
    }

    @Override
    @Transactional
    public boolean deleteComment(BigInteger id, String username) {
        var comment = commentRepository.findById(id).get();
        if (!comment.getPoster().getUsername().equals(username)) {
            throw new UnauthorizedCommentActionException();
        }
        var city = comment.getCity();
        city.getComments().remove(comment);
        comment.setCity(null);
        cityRepository.save(city);
        commentRepository.deleteById(comment.getId());
        return true;
    }

    @Override
    @Transactional
    public void adminDeleteComment(BigInteger id) {
        var comment = commentRepository.findById(id).get();
        var city = comment.getCity();
        city.getComments().remove(comment);
        comment.setCity(null);
        cityRepository.save(city);
        commentRepository.deleteById(comment.getId());
    }
}
