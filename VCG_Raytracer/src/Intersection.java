import com.sun.org.apache.xpath.internal.operations.Bool;
import utils.Vec3;
/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Intersection {
    private Vec3 interSectionPoint;
    private Vec3 normal;
    private Ray inRay;
    private Ray outRay;
    private Shape shape;
    private float distance;
    private Boolean incoming;
    private Boolean hit;


    public Ray calculateReflecionRay() {
    }

    public Ray calculateRefractionRay() {
    }

    public Boolean isOutOfDistance() {
    }
}

