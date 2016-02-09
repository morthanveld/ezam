package com.maze.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.awt.Shape;

public class MazeGame extends ApplicationAdapter implements GestureDetector.GestureListener {
	SpriteBatch batch;
	Texture img;

    private MazeCreator mazeCreator;
    private ShapeRenderer shapeRenderer;

    private GestureDetector gd;

    private int panDirection = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

        mazeCreator = new MazeCreator();
        shapeRenderer = new ShapeRenderer();

        gd = new GestureDetector(this);

        Gdx.input.setInputProcessor(gd);
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

        //System.out.println("size " + Gdx.app.getGraphics().getHeight() + "\t" + Gdx.app.getGraphics().getWidth());

        float xPart = Gdx.app.getGraphics().getWidth() / (mazeCreator.width);
        float yPart = Gdx.app.getGraphics().getHeight() / (mazeCreator.height);
        float xScale = 32.0f;//xPart;
        float yScale = 24.0f;//yPart;

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
            shapeRenderer.line(e.ax * xScale, e.ay * yScale, e.bx * xScale, e.by * yScale);
            //shapeRenderer.rect(e.ax * 10 + 10, e.ay * 10 + 10, 5.0f, 5.0f);
            //shapeRenderer.rect(e.bx * 10 + 10, e.by * 10 + 10, 5.0f, 5.0f);

        }

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.circle(mazeCreator.playerX * xScale, mazeCreator.playerY * yScale, 5.0f);
        shapeRenderer.end();


		batch.end();
	}

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (panDirection != 0)
        {
            return false;
        }

        if (Math.abs(deltaX) > Math.abs(deltaY))
        {
            if (deltaX > 0)
                //mazeCreator.moveEast();
                panDirection = 1;
            else
                //mazeCreator.moveWest();
                panDirection = 2;
        }
        else
        {
            if (deltaY > 0)
                //mazeCreator.moveNorth();
                panDirection = 3;
            else
                //mazeCreator.moveSouth();
                panDirection = 4;

        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        switch (panDirection)
        {
            case 1:
            {
                mazeCreator.moveWest();
                break;
            }
            case 2:
            {
                mazeCreator.moveEast();
                break;
            }
            case 3:
            {
                mazeCreator.moveNorth();
                break;
            }
            case 4:
            {
                mazeCreator.moveSouth();
                break;
            }
        }

        panDirection = 0;
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
