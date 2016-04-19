/**
 * Created by PraktikumCG on 19.04.2016.
 */

import utils.Vec2;
import utils.Vec3;

public class Camera extends SceneObject{
    private Vec3 cameraPosition;
    private Vec3 localAt;
    private Vec3 upVector;
    private float localLength;

    public Camera(Vec3 _pos, Vec3 _locA, Vec3 _up, float _localL) {
        this.cameraPosition = _pos;
        this.localAt = _locA;
        this.upVector = _up;
        this.localLength = _localL;


    }

    public Vec3 calculateDestination() {
        return null;
    }

    private Vec2 pNorm(Vec2 _v2) {
        float x = _v2.x;
        float y = _v2.y;
        float newX = pNormCalculate(x, (float) Main.IMAGE_WIDTH);
        float newY = pNormCalculate(y, (float) Main.IMAGE_HEIGHT);

        return new Vec2(newX, newY);
    }

    private float pNormCalculate(float _f, float _max) {
        return (2*((_f+0.5f)/_max)-1);
    }
}
