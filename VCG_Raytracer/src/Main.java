// ************************************************************ //
//                      Hochschule Duesseldorf                  //
//                                                              //
//                     Vertiefung Computergrafik                //
// ************************************************************ //


/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    1. Documentation:    Did you comment your code shortly but clearly?
    2. Structure:        Did you clean up your code and put everything into the right bucket?
    3. Performance:      Are all loops and everything inside really necessary?
    4. Theory:           Are you going the right way?

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 TecMegz

     Master of Documentation: Christopher
     Master of Structure: Till
     Master of Performance: Marius
     Master of Theory: Jannis

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

import ui.Window;

// Main application class. This is the routine called by the JVM to run the program.
public class Main {

    static int IMAGE_WIDTH = 800;
    static int IMAGE_HEIGHT = 600;
    private int maxRecursions;

    // Initial method. This is where the show begins.
    public static void main(String[] args){
        long tStart = System.currentTimeMillis();

        Window renderWindow = new Window(IMAGE_WIDTH, IMAGE_HEIGHT);


        draw(renderWindow);

        renderWindow.setTimeToLabel(String.valueOf(stopTime(tStart)));
    }

    private static void draw(Window renderWindow){
        raytraceScene(renderWindow);
    }

    private void setupScene()
    {

    }

    private void setupLights()
    {

    }
    private void setupCameras()
    {

    }

    private void setupCornellBox()
    {

    }

    private static void raytraceScene(Window renderWindow){
        Raytracer raytracer = new Raytracer(renderWindow);

        raytracer.renderScene();
    }

    private static double stopTime(long tStart){
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }
}