package raytracer;

import light.Light;
import light.PointLight;
import material.Lambert;
import material.Phong;
import objects.Plane;
import objects.Shape;
import objects.Sphere;
import scene.Camera;
import ui.Window;
import utils.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;
    public static ArrayList<Light> lightList = new ArrayList<Light>();

    public Raytracer(Window renderWindow){
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
    }

    public void renderScene(){
        Log.print(this, "Start rendering");

        Camera myCam = new Camera(new Vec3(0 ,0, 5), new Vec3(0, 0, -1), new Vec3(0, 1, 0), 90f, new Vec3(0,0,0));
        Vec3 start = myCam.getPosition();
        Vec3 sphereStart1 = new Vec3(0, 0, -3);
        Sphere sphere1 = new Sphere(1, sphereStart1, new Phong(new RgbColor(1,0,0), 0.8f, 20f));
        Vec3 sphereStart2 = new Vec3(0, 1, -5);
        Sphere sphere2 = new Sphere(1, sphereStart2, new Phong(new RgbColor(0,1,0), 0.8f, 20f));

        Plane plane1 = new Plane(new Vec3(2,0,0), new Phong(new RgbColor(0,0,1), 1f, 20), new Vec3(0, 0, 0));
        Plane plane2 = new Plane(new Vec3(0,1,0.1f), new Phong(new RgbColor(1,0,0), 1f, 20), new Vec3(0, 1, 0));

        Shape[] shapeArray = new Shape[3];
        shapeArray[0] = plane1;
        shapeArray[1] = sphere2;
        shapeArray[2] = sphere1;

        createLight(0, new RgbColor(0.8f,0.8f,0.8f), new Vec3(7, 4, 10));

        for (int j = 0; j < mBufferedImage.getHeight(); j ++) {
            for (int i = 0; i < mBufferedImage.getWidth(); i++) {
                Vec3 dest = myCam.calculateDestination(i, j);
                Ray r = new Ray(start, dest.sub(start), 200);

                Intersection inters = shapeArray[0].intersect(r);
                float smallestDistance = -1;
                if (inters.hit) smallestDistance = inters.distance;

                for (int k = 1; k < shapeArray.length; k++) { //unfertig!!!!
                    Intersection tempInters = shapeArray[k].intersect(r);
                    float tempDis = tempInters.distance;


                    if(tempInters.hit && (tempDis < smallestDistance || smallestDistance < 0)) {
                        inters = tempInters;
                    }
                }

                mRenderWindow.setPixel(mBufferedImage, inters.rgb, new Vec2(i, j));
            }
        }
        IO.saveImageToPng(mBufferedImage, "RenderBilder\\raytracing"+System.currentTimeMillis()+".png");
    }

    private Light createLight(int _type, RgbColor _col, Vec3 _pos) {
        Light l = new PointLight(_col, _pos);
        lightList.add(l);
        return l;
    }
}


/**
 *
// Init vor den Schleifen
 scene.Camera myCam = new scene.Camera(new Vec3(0 ,0, 0), new Vec3(0, 0, -1), new Vec3(0, 1, 0), 1.0f, 90.0f);
 Vec3 start = myCam.getPosition();

 Vec3 dest = myCam.calculateDestination(mBufferedImage.getWidth(), mBufferedImage.getHeight());
 raytracer.Ray r = new raytracer.Ray(start, dest);
 Vec3 rEndP = r.getEndpoint();
 float rDist = r.getDistance();
 float vDiffMax = rDist-myCam.getFocalLength();
 Vec3 rRef = new Vec3(start.x+1, start.y, start.z).sub(start);

 // muss in die Schleifen rein


 dest = myCam.calculateDestination(i, j);
 r = new raytracer.Ray(start, dest);
 rEndP = r.getEndpoint();
 rDist = r.getDistance();
 float vDiff = rDist-myCam.getFocalLength();
 vDiff = vDiff/vDiffMax;

 Vec3 rV = r.rayVector();
 Vec3 rP = new Vec3 (rV.x, rV.y, start.z);

 double angle = rP.angle(rRef)*(360/(2*Math.PI));

 if (rP.y < rRef.y) {
 angle = 360-angle;
 }

 //Log.print(this, "--   "+rP.x+", "+rP.y+", "+rP.z+", AA:"+angle);

 float[] rgb = utils.HSV.hsvToRgb((float) angle, vDiff*100, 100f);
 float red = rgb[0];
 float blue = rgb[1];
 float green = rgb[2];

 mRenderWindow.setPixel(mBufferedImage, new RgbColor(red, green, blue), new Vec2(i, j));
 **/