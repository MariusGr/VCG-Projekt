import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Sphere extends Shape {
    private float radius;

    public Sphere(float _radius, Vec3 _pos) {
        this.radius = _radius;
        this.position = _pos;
    }

    public float  intersect(Ray _ray) {
        Vec3 start = _ray.getStartPoint();
        Vec3 dir = _ray.getDirection();
        float b = 2*(start.x*dir.x+start.y*dir.y+start.z*dir.z);
        float c = start.x*start.x+start.y*start.y+start.z*start.z-this.radius*this.radius;

        float d = b*b-4*c;

        return d;
    }
}
