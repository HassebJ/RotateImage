import java.io.File;
import java.util.Scanner;

public class AngleCorrection {

    static int imageHeightRead = 0;

    static int getXCordinate(String line){
        int temp = line.indexOf("1");
        if(temp < 0){
            return temp;
        }
        return temp/2;
    }

    static double [] getXandYdiff(Scanner inFile, int firstWhitePixelX, int firstWhitePixelY){
        while(inFile.hasNext()){
            String line = inFile.nextLine();
            int temp = getXCordinate(line);
            if(temp > -1){
                firstWhitePixelX = temp;
                break;
            }
            firstWhitePixelY++;
        }

        int lastWhitePixelX = firstWhitePixelX;
        int lastWhitePixelY = firstWhitePixelY;
        boolean isClockwise = false;
        while(inFile.hasNext()) {
            String nextLine = inFile.nextLine();
            int currentWhitePixelX = getXCordinate(nextLine);
            lastWhitePixelY++;
            if (currentWhitePixelX  != firstWhitePixelX) {
                isClockwise = currentWhitePixelX < firstWhitePixelX;
                lastWhitePixelX = currentWhitePixelX;
                break;
            }
        }




        while(inFile.hasNext()){
            String line = inFile.nextLine();
            int temp = getXCordinate(line);
            if(isClockwise){
                if(temp > lastWhitePixelX){
                    break;
                }
            }else{
                if(temp < lastWhitePixelX){
                    break;
                }
            }

            lastWhitePixelX = temp;
            lastWhitePixelY++;
        }
        int y = lastWhitePixelY-firstWhitePixelY;
        double x = lastWhitePixelX-firstWhitePixelX;
        imageHeightRead += lastWhitePixelY;
        double rotAngle = Math.toDegrees(Math.atan(y/x));
        double rotAngle2 = Math.toDegrees(Math.atan2(y,x));
        System.out.println(rotAngle);
        System.out.println(rotAngle2);
        return new double[]{x,y};


    }

    static boolean isRotationValid(double [] XYdiff){
        int rectangleSide = (int)Math.sqrt(Math.pow( XYdiff[0], 2) +  Math.pow( XYdiff[1], 2));
        return imageHeightRead >= rectangleSide;
    }

    public static void main(String [] args) throws Exception{
        File file = new File("rotated.csv");
        Scanner inFile = new Scanner(file);

        String fields = inFile.nextLine();//.split(",");

        int firstWhitePixelX = 0;
        int firstWhitePixelY = 1;
        double[] XYdiff;
        do {
            XYdiff = getXandYdiff(inFile, firstWhitePixelX, firstWhitePixelY);
        }
        while (!isRotationValid(XYdiff));

        double y = XYdiff[1];
        double x = XYdiff[0];
        double rotAngle = Math.toDegrees(Math.atan(y/x));
        double rotAngle2 = Math.toDegrees(Math.atan2(y,x));
        System.out.println(rotAngle);
        System.out.println(rotAngle2);
    }
}
