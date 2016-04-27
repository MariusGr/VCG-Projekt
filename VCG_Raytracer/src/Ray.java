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
        this.direction = _dir;
        this.distance = _dis;
    }


}
