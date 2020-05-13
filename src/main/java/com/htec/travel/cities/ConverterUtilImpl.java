package com.htec.travel.cities;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConverterUtilImpl implements ConverterUtil {

    @Override
    public CityDto convertCityEntity(City city, int maxComments) {
        return CityDto.builder()
                .id(city.getId())
                .name(city.getName())
                .country(city.getCountry())
                .description(city.getDescription())
                .comments(convertComments(city.getComments(), maxComments))
                .build();
    }

    private List<CommentDto> convertComments(List<Comment> comments, int maxComments) {
        List<CommentDto> result = new ArrayList<>();
        if (comments != null) {
            for (int i = 0; i < maxComments && i < comments.size(); i++) {
                result.add(convertCommentEntity(comments.get(i)));
            }
        }
        return result;
    }

    @Override
    public CommentDto convertCommentEntity(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .created(comment.getCreated())
                .modified(comment.getModified())
                .description(comment.getDescription())
                .posterId(comment.getPoster().getId())
                .build();
    }
}
