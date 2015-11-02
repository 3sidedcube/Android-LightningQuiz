package com.cube.storm.ui.quiz.model.quiz;

import com.cube.storm.ui.model.property.ImageProperty;
import com.cube.storm.ui.model.property.TextProperty;
import com.cube.storm.ui.quiz.model.property.RangeProperty;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Model for SliderQuizItem class
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
@NoArgsConstructor @AllArgsConstructor(suppressConstructorProperties = true)
@Accessors(chain = true) @Data
public class SliderQuizItem extends QuizItem
{
	protected ArrayList<ImageProperty> image;
	protected RangeProperty range;
	protected TextProperty unit;
	protected int answer;
	protected int initialPosition;
}
