package com.cube.storm.ui.quiz.model.quiz;

import com.cube.storm.ui.model.property.TextProperty;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * // TODO: Add class description
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
@NoArgsConstructor @AllArgsConstructor
@Accessors(chain = true) @Data
public class TextQuizItem extends ItemQuizItem
{
	protected Collection<TextProperty> options;
}
