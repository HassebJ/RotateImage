
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

    static int getXandYdiff(Scanner inFile, int firstWhitePixelX, int firstWhitePixelY){
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
        return (int)Math.toDegrees(Math.atan(y/x));
    }

    public static void main(String [] args) throws Exception{
        File file = new File("rotated.csv");
        Scanner inFile = new Scanner(file);

        String fields = inFile.nextLine();//.split(",");
        int firstWhitePixelX = 0;
        int firstWhitePixelY = 1;
        int angle =  getXandYdiff(inFile, firstWhitePixelX, firstWhitePixelY);
        System.out.println(angle);

    }
}
