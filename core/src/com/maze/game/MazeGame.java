package com.maze.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Shape;

public class MazeGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

    private MazeCreator mazeCreator;
    private ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

        mazeCreator = new MazeCreator();
        shapeRenderer = new ShapeRenderer();
    }

	@Override
	public void render () {
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            if (mazeCreator.moveNorth())
            {
                System.out.println("north");
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            if (mazeCreator.moveSouth())
            {
                System.out.println("south");
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            if (mazeCreator.moveWest())
            {
                System.out.println("west");
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            if (mazeCreator.moveEast())
            {
                System.out.println("east");
            }
        }

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img, 0, 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);

        /*
        for (int i = 0; i < mazeCreator.copyEdges.size(); i++) {
            MazeCreator.Edge e = (MazeCreator.Edge)mazeCreator.copyEdges.get(i);
            shapeRenderer.setColor(e.w / 1000.0f, e.w / 1000.0f, e.w / 1000.0f, 1);
            shapeRenderer.line(e.ax * 10 + 10, e.ay * 10 + 10, e.bx * 10 + 10, e.by * 10 + 10);

        }
        */

        //Gdx.gl.glLineWidth(5);
        for (int i = 0; i < mazeCreator.visitedEdges.size(); i++) {
            MazeCreator.Edge e = (MazeCreator.Edge)mazeCreator.visitedEdges.get(i);
            //shapeRenderer.setColor(e.w / 1000.0f, e.w / 1000.0f, e.w / 1000.0f, 1);
            shapeRenderer.line(e.ax * 10 + 10, e.ay * 10 + 10, e.bx * 10 + 10, e.by * 10 + 10);
            //shapeRenderer.rect(e.ax * 10 + 10, e.ay * 10 + 10, 5.0f, 5.0f);
            //shapeRenderer.rect(e.bx * 10 + 10, e.by * 10 + 10, 5.0f, 5.0f);

        }

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.circle(mazeCreator.playerX * 10.0f + 10.0f, mazeCreator.playerY * 10.0f + 10.0f, 5.0f);
        shapeRenderer.end();


		batch.end();
	}
}
