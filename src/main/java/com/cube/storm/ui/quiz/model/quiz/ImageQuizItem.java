package com.cube.storm.ui.quiz.model.quiz;

import com.cube.storm.ui.model.property.ImageProperty;
import com.cube.storm.ui.model.property.TextProperty;

import java.util.ArrayList;

import lombok.Getter;

/**
 * // TODO: Add class description
 *
 * @author Luke Reed
 * @project Storm
 */
public class ImageQuizItem extends ItemQuizItem
{
	@Getter protected ArrayList<TextProperty> options;
	@Getter protected ArrayList<ArrayList<ImageProperty>> images;
}
