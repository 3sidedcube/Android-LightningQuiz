package com.cube.storm.ui.quiz.model.quiz;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;

/**
 * // TODO: Add class description
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public abstract class ItemQuizItem extends QuizItem
{
	@Getter protected int limit;
	@Getter protected Collection<Integer> answer;

	@Getter protected ArrayList<Integer> selectHistory = new ArrayList<Integer>();
}
