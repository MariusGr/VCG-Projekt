package raytracer;

import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */

public class Ray {
    private Vec3 startPoint;
    private Vec3 endPoint;
    private Vec3 direction;
    private float distance;

    public Ray(Vec3 _s, Vec3 _dir, float _dis) {
        this.startPoint = _s;
        this.direction = _dir.normalize();
        this.distance = _dis;

        this.endPoint = direction.multScalar(distance).add(startPoint);
    }

    public Vec3 getEndpoint() {
        return endPoint;
    }

    public Vec3 rayVector() {
        return new Vec3(endPoint).sub(new Vec3 (startPoint));
    }

    public float getDistance() {
        return this.distance;
    }

    public Vec3 getStartPoint() {
        return startPoint;
    }

    public Vec3 getDirection() {
        return direction;
    }
}
