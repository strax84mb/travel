package com.htec.travel.cities;

public interface ConverterUtil {

    CityDto convertCityEntity(City city, int maxComments);

    CommentDto convertCommentEntity(Comment comment);
}
