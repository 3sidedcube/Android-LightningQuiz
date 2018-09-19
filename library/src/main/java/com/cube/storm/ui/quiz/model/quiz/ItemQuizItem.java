package com.cube.storm.ui.quiz.model.quiz;

import java.util.ArrayList;
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
public abstract class ItemQuizItem extends QuizItem
{
	protected int limit;
	protected Collection<Integer> answer;
	protected ArrayList<Integer> selectHistory = new ArrayList<Integer>();
}
