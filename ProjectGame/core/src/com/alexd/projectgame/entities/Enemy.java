package com.alexd.projectgame.entities;

import com.alexd.projectgame.enums.EntityType;
import com.alexd.projectgame.utils.GameManager;
import com.alexd.projectgame.utils.PhysicsFactory;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alex on 2015-04-13.
 */
public class Enemy extends Entity {

    private boolean _isFlipped;

    public Enemy(World world, float x, float y, float width, float height){
        super(world, x, y, width, height);
        initiate();
    }

    @Override
    protected void initiate() {
        _entityType = EntityType.ENEMY;
        _body = PhysicsFactory.createEnemy(_world, this);
        _isFlipped = false;

    }

    public void flip(){
        if (!_isFlipped){
            _isFlipped = true;
            _body.setLinearVelocity(new Vector2(getNewVelocityX(), 0));
        }
    }

    private float getNewVelocityX(){
        float platformVelocityX = +GameManager.getInstance().getStaticObjectSpeed();
        float bodyVelocityX = +_body.getLinearVelocity().x;

        return platformVelocityX - (bodyVelocityX - platformVelocityX);
    }

    public boolean getIsFlipped(){
        return _isFlipped;
    }
}
