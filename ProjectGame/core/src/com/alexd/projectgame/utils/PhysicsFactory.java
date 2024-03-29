package com.alexd.projectgame.utils;

import com.alexd.projectgame.entities.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


/**
 * Class for creating the bodies for box2d
 */
public class PhysicsFactory {


    public static Body createEnemy(World world, Enemy enemy){
        BodyDef bodyDef = getBodyDef(enemy.getX(), enemy.getY(), BodyType.DynamicBody);
        bodyDef.fixedRotation = true;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(enemy.getWidth() / 2, enemy.getHeight() / 2);
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(false, shape, Box2DConstants.ENEMY_DENSITY);
        fixtureDef.filter.categoryBits = Box2DConstants.ENEMY_BIT;
        fixtureDef.filter.maskBits = Box2DConstants.PLATFORM_BIT | Box2DConstants.RUNNER_BIT |
                Box2DConstants.PLATFORM_SENSOR_BIT;

        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(enemy.getWidth() / 2, 0.025f, new Vector2(0, enemy.getHeight() / 2 + 0.025f), 0f);
        FixtureDef sensorDef = getFixtureDef(true, sensorShape, 0);
        sensorDef.filter.categoryBits = Box2DConstants.ENEMY_SENSOR_BIT;
        sensorDef.filter.maskBits = Box2DConstants.RUNNER_BIT;

        body.createFixture(sensorDef);
        sensorShape.dispose();

        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(enemy);
        shape.dispose();
        body.setGravityScale(1.5f);

        body.setLinearVelocity(new Vector2(GameManager.getInstance().getEnemySpeed(), 0));

        return body;
    }

    public static Body createLife(World world, Life life){

        BodyDef bodyDef = getBodyDef(life.getX(), life.getY(), BodyType.KinematicBody);
        PolygonShape shape = getBox(life.getWidth(), life.getHeight());
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(true, shape, 1f);
        fixtureDef.filter.categoryBits = Box2DConstants.LIFE_BIT;
        fixtureDef.filter.maskBits = Box2DConstants.RUNNER_BIT;

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(life);

        body.setLinearVelocity(new Vector2(GameManager.getInstance().getStaticObjectSpeed(), 0));

        return body;

    }


    public static Body createObstacle(World world, Obstacle obstacle){
        BodyDef bodyDef = getBodyDef(obstacle.getX(), obstacle.getY(), BodyType.KinematicBody);
        PolygonShape shape = getBox(obstacle.getWidth(), obstacle.getHeight());
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(true, shape, Box2DConstants.OBSTACLE_DENSITY);
        fixtureDef.filter.categoryBits = Box2DConstants.ENEMY_BIT;
        fixtureDef.filter.maskBits = Box2DConstants.PLATFORM_BIT | Box2DConstants.RUNNER_BIT;
        body.setLinearVelocity(GameManager.getInstance().getStaticObjectSpeed(), 0);
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(obstacle);
        shape.dispose();

        return body;
    }


    public static Body createPlatform(World world, Platform platform ){
        BodyDef bodyDef = getBodyDef(platform.getX(), platform.getY(), BodyType.KinematicBody);
        PolygonShape shape = getBox(platform.getWidth(), platform.getHeight());
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = getFixtureDef(false, shape, Box2DConstants.PLATFORM_DENSITY);
        fixtureDef.friction = 0f;
        fixtureDef.filter.categoryBits = Box2DConstants.PLATFORM_BIT;
        fixtureDef.filter.maskBits = Box2DConstants.RUNNER_BIT | Box2DConstants.ENEMY_BIT;

        body.setLinearVelocity(new Vector2(GameManager.getInstance().getStaticObjectSpeed(), 0));
        body.createFixture(fixtureDef);
        body.resetMassData();
        body.setUserData(platform);
        shape.dispose();

        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(-1, 3);
        sensorShape.setAsBox( 0.1f, 0.1f, new Vector2(-10f ,3), 0f);

        FixtureDef sensorDef = getFixtureDef(true, sensorShape, 0);
        sensorDef.filter.categoryBits = Box2DConstants.PLATFORM_SENSOR_BIT;
        sensorDef.filter.maskBits = Box2DConstants.ENEMY_BIT;

        body.createFixture(sensorDef);
        sensorShape.dispose();

        return body;
    }


    public static Body createRunner(World world, Runner runner){
        BodyDef bodyDef = getBodyDef(runner.getX(), runner.getY(), BodyType.DynamicBody);
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(runner.getWidth() / 2, runner.getHeight() / 2);
        FixtureDef fixtureDef = getFixtureDef(false, shape, Box2DConstants.RUNNER_DENSITY);
        fixtureDef.filter.categoryBits = Box2DConstants.RUNNER_BIT;
        fixtureDef.filter.maskBits = Box2DConstants.PLATFORM_BIT | Box2DConstants.ENEMY_BIT | Box2DConstants.ENEMY_SENSOR_BIT |
                Box2DConstants.LIFE_BIT;
        body.createFixture(fixtureDef);

        body.setGravityScale(Box2DConstants.RUNNER_GRAVITY_SCALE);
        body.resetMassData();
        body.setUserData(runner);
        shape.dispose();

        return body;
    }



    private static FixtureDef getFixtureDef(boolean isSensor, Shape shape, float density ){
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.isSensor = isSensor;
        fixtureDef.shape = shape;
        fixtureDef.density = density;

        return fixtureDef;
    }


    private static PolygonShape getBox(float width, float height){
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(width / 2, height / 2);

        return shape;
    }


    private static BodyDef getBodyDef(float x, float y, BodyType type){
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = type;
        bodyDef.position.set(new Vector2(x, y));

        return bodyDef;
    }
}
