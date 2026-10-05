import currency.*;
import distance.*;
import time.*;
import java.util.Scanner;

public class Convertor
{
    public static void main(String[] args)
    {
        int code, currency_code, distance_code, time_code;

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the code 1:Currency\n2:Distance\n3:Time");
        code = sc.nextInt();

        if(code == 1)
        {
            CurrencyConvertor c = new CurrencyConvertor();

            System.out.println("Enter the Currency code 1:Euro\n2:Dollar\n3:Yen");
            currency_code = sc.nextInt();

            if(currency_code == 1)
            {
                c.convertInrToEuro();
                c.convertEuroToInr();
            }
            else if(currency_code == 2)
            {
                c.convertInrToDollar();
                c.convertDollarToInr();
            }
            else if(currency_code == 3)
            {
                c.convertInrToYen();
                c.convertYenToInr();
            }
            else
            {
                System.out.println("Invalid Code");
            }
        }

        else if(code == 2)
        {
            DistanceConvertor d = new DistanceConvertor();

            System.out.println("Enter the Distance code 1:Meter\n2:Miles");
            distance_code = sc.nextInt();

            if(distance_code == 1)
            {
                d.convertMeterToKm();
                d.convertKmToMeter();
            }
            else if(distance_code == 2)
            {
                d.convertMilesToKm();
                d.convertKmToMiles();
            }
            else
            {
                System.out.println("Invalid Code");
            }
        }

        else if(code == 3)
        {
            TimeConvertor t = new TimeConvertor();

            System.out.println("Enter the Time code 1:Minutes\n2:Seconds");
            time_code = sc.nextInt();

            if(time_code == 1)
            {
                t.convertHourToMinute();
                t.convertMinuteToHour();
            }
            else if(time_code == 2)
            {
                t.convertHourToSeconds();
                t.convertSecondsToHour();
            }
            else
            {
                System.out.println("Invalid Code");
            }
        }

        else
        {
            System.out.println("Invalid Code");
        }
    }
}