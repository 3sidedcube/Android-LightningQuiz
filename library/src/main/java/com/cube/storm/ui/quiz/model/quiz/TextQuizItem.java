package com.cube.storm.ui.quiz.model.quiz;

import com.cube.storm.ui.model.property.TextProperty;

import java.util.Collection;

import lombok.Getter;

/**
 * // TODO: Add class description
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class TextQuizItem extends ItemQuizItem
{
	@Getter protected Collection<TextProperty> options;
}
