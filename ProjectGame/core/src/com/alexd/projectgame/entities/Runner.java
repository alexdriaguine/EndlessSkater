package com.alexd.projectgame.entities;

import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.utils.Constants;
import com.alexd.projectgame.utils.PhysicsFactory;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 2015-04-11.
 */
public class Runner extends Entity {
    /* CONSTANTS */

    public static final int MAX_HEALTH = 5;

    private int _health;
    private boolean _isJumping;
    private boolean _isOnGround;

    public int getHealth(){ return _health; }

    public void setHealth(int value){ _health = value; }

    public boolean getIsJumping(){ return _isJumping; }

    public void setIsOnGround(boolean value){
        _isOnGround = value;
    }


    /* Constructor */
    public Runner(World world, float x, float y, float width, float height){
        super(world, x, y, width, height);
        initiate();

    }

    public Runner(){

    }

    @Override
    protected void initiate() {
        _gameObjectType = GameObjectType.RUNNER;
        _body = PhysicsFactory.createRunner(_world, this);
        _health = MAX_HEALTH;
        landed();
    }


    public void jump(){
        // Jumping code translated from http://www.iforce2d.net/b2dtut/constant-speed
        if(!_isJumping && _isOnGround){

            _isJumping = true;
            _isOnGround = false;
            _body.applyLinearImpulse(Constants.RUNNER_JUMPING_IMPULSE, _body.getWorldCenter(), true);
        }
    }

    public void bumpOffEnemy(){
        _body.applyLinearImpulse(Constants.RUNNER_BUMP_IMPULSE, _body.getWorldCenter(), true);
    }

    public void landed(){
        _isJumping = false;
        _isOnGround = true;
    }


    public void removeHealth(){
        if (_health != 0){
            _health--;
        }
    }

    public void addHealth(){
        if (_health < 3){
            _health++;
        }
    }


    public boolean isFalling() {
       return _body.getLinearVelocity().y < 0;
    }




}