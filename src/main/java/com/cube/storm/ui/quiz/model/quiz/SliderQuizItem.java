package com.cube.storm.ui.quiz.model.quiz;

import com.cube.storm.ui.model.property.ImageProperty;
import com.cube.storm.ui.model.property.TextProperty;
import com.cube.storm.ui.quiz.model.property.RangeProperty;

import lombok.Getter;

/**
 * Model for SliderQuizItem class
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class SliderQuizItem extends QuizItem
{
	@Getter protected ImageProperty image;
	@Getter protected RangeProperty range;
	@Getter protected TextProperty unit;
	@Getter protected int answer;
	@Getter protected int initialPosition;
}
