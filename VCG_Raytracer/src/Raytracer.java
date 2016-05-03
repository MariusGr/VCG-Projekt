import ui.Window;
import utils.*;

import java.awt.image.BufferedImage;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;

    public Raytracer(Window renderWindow){
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
    }

    public void renderScene(){
        Log.print(this, "Start rendering");

        Camera myCam = new Camera(new Vec3(0 ,0, 5), new Vec3(0, 0, -1), new Vec3(0, 1, 0), 1.0f, 90.0f);
        Vec3 start = myCam.getPosition();
        Vec3 sphereStart = new Vec3(0, 0, 0);
        Sphere sphere1 = new Sphere(1, sphereStart);

        for (int j = 0; j < mBufferedImage.getHeight(); j ++) {
            for (int i = 0; i < mBufferedImage.getWidth(); i++) {
                Vec3 dest = myCam.calculateDestination(i, j);
                Ray r = new Ray(start, dest.sub(start), 200);

                float d = sphere1.intersect(r);

                float red = 1;
                float blue = 1;
                float green = 1;

                if (d < 0) {
                    red = 0;
                    blue = 0;
                    green = 0;
                }

                mRenderWindow.setPixel(mBufferedImage, new RgbColor(red, green, blue), new Vec2(i, j));
            }
        }
        IO.saveImageToPng(mBufferedImage, "raytracing"+System.currentTimeMillis()+".png");
    }
}


/**
 *
// Init vor den Schleifen
 Camera myCam = new Camera(new Vec3(0 ,0, 0), new Vec3(0, 0, -1), new Vec3(0, 1, 0), 1.0f, 90.0f);
 Vec3 start = myCam.getPosition();

 Vec3 dest = myCam.calculateDestination(mBufferedImage.getWidth(), mBufferedImage.getHeight());
 Ray r = new Ray(start, dest);
 Vec3 rEndP = r.getEndpoint();
 float rDist = r.getDistance();
 float vDiffMax = rDist-myCam.getFocalLength();
 Vec3 rRef = new Vec3(start.x+1, start.y, start.z).sub(start);

 // muss in die Schleifen rein


 dest = myCam.calculateDestination(i, j);
 r = new Ray(start, dest);
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

 float[] rgb = HSV.hsvToRgb((float) angle, vDiff*100, 100f);
 float red = rgb[0];
 float blue = rgb[1];
 float green = rgb[2];

 mRenderWindow.setPixel(mBufferedImage, new RgbColor(red, green, blue), new Vec2(i, j));
 **/