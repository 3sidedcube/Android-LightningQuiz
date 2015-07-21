package com.cube.storm.ui.quiz.model.quiz;

import com.cube.storm.ui.model.property.ImageProperty;
import com.cube.storm.ui.quiz.model.property.ZoneProperty;

import lombok.Getter;

/**
 * Quiz item for selecting an area on an image
 *
 * @author Matt Allen
 * @project Hazards
 */
public class AreaQuizItem extends QuizItem
{
	@Getter public ImageProperty image;
	@Getter public ZoneProperty[] answer;
}
