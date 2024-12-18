import java.util.Scanner;


class Sinn {
    public double sin1(double x, int k) {
       /* x = x % Math.PI;
        if (x > 180) {     //чтобы угол находился в пределах [-180, 180]
            x -= 360;
        } else if (x < -180) {
            x += 360;
        }*/
        x = x % (2 * Math.PI);
        if (x > Math.PI) {
            x -= 2 * Math.PI;
        } else if (x < -Math.PI) {
            x += 2 * Math.PI;
        }
       // double y = Math.toRadians(x );
        double res = x;

        double term = x;
        for (int i = 1; i <= k; i++) {
            term *= -x * x / (2 * i * (2 * i + 1));
            res += term;
        }
        return res;
    }


    public double sin2(double x) {
       /* x = x % Math.PI;
        if (x > 180) {
            x -= 360;
        } else if (x < -180) {
            x += 360;
        }*/
        x = x % (2 * Math.PI);
        if (x > Math.PI) {
            x -= 2 * Math.PI;
        } else if (x < -Math.PI) {
            x += 2 * Math.PI;
        }
       // double y = Math.toRadians(x);
        return Math.sin(x);

    }
}
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Write real number x: ");
        double x = in.nextDouble();
        System.out.print("Write integer k: ");
        int k = in.nextInt();

        Sinn sinn = new Sinn();


        System.out.printf("x: %." + k + "f\n", x);
        System.out.printf("k: %d \n", k);

        double res1 = sinn.sin1(x, k);
        System.out.printf("Res 1: %." + k + "f\n", res1);

        double res2 = sinn.sin2(x);
        System.out.printf("Res 2: %." + k + "f\n", res2);

        in.close();
    }
}
