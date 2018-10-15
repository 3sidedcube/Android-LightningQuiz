package com.cube.storm.ui.quiz.model.quiz;

import com.cube.storm.ui.model.property.ImageProperty;
import com.cube.storm.ui.model.property.TextProperty;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * // TODO: Add class description
 *
 * @author Luke Reed
 * @project Storm
 */
@NoArgsConstructor @AllArgsConstructor
@Accessors(chain = true) @Data
public class ImageQuizItem extends ItemQuizItem
{
	protected ArrayList<TextProperty> options;
	protected ArrayList<ArrayList<ImageProperty>> images;
}
